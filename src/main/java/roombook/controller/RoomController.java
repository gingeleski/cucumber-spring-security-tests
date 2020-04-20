package roombook.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import roombook.model.appointment.Appointment;
import roombook.model.room.Room;
import roombook.service.AppointmentService;
import roombook.service.RoomService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController
{
    private final AppointmentService appointmentService;
    private final RoomService roomService;

    public RoomController(AppointmentService appointmentService, RoomService roomService)
    {
        this.appointmentService = appointmentService;
        this.roomService = roomService;
    }

    /**
     * Ad hoc input validation for the room name path parameter. Checks that string is not empty, does not exceed
     * the 50-character limit on room names in the database, and that characters are only alphanumeric, underscores,
     * or hyphens.
     *
     * @param roomName string to validate
     * @return boolean validation outcome
     */
    private boolean validateRoomName(String roomName)
    {
        return (false == roomName.isEmpty()
                        && roomName.matches("[a-zA-Z0-9_-]*")
                        && roomName.length() <= 50);
    }

    /**
     * Ad hoc input validation for Unix timestamp-format query parameters. Checks that the string is not empty, is only
     * digits, and does not exceed 10 characters.
     *
     * @param unixTimestampString string to validate
     * @return boolean validation outcome
     */
    private boolean validateUnixTimestampString(String unixTimestampString)
    {
        return (false == unixTimestampString.isEmpty()
                        && unixTimestampString.matches("[0-9]*")
                        && unixTimestampString.length() <= 10);
    }

    // GET /api/rooms
    @GetMapping("/rooms")
    public ResponseEntity getRooms(HttpServletResponse res)
    {
        return ResponseEntity.status(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON).body(this.roomService.getRooms());
    }

    // GET /api/rooms/{roomName}
    @GetMapping("/rooms/{roomName}")
    public ResponseEntity getRoomByName(@PathVariable String roomName, HttpServletResponse res) throws Throwable
    {
        if (false == validateRoomName(roomName))
        {
            // Failed validation on the passed name, so return a 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }

        // Look for room by the unique name
        Room r = this.roomService.getRoomByName(roomName);

        if (r != null)
        {
            // Found that room - return a 200 OK
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(r);
        }

        // Result was null for this name so return a 404 Not Found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(null);
    }

    // GET /api/rooms/{roomName}/availability
    @GetMapping("/rooms/{roomName}/availability")
    public ResponseEntity getRoomAvailabilityByName(@PathVariable String roomName,
                                                    @RequestParam("start") String startTimeString,
                                                    @RequestParam("end") String endTimeString, HttpServletResponse res)
    {
        if (false == validateRoomName(roomName))
        {
            // Failed validation on the passed name, so return a 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }

        // Get some java.sql.Timestamp objects ready if we got passed the time filter params
        Timestamp startTime = null;
        Timestamp endTime = null;

        // Validate 'start' time string if it's present - should be Unix timestamp format - and set it up for usage
        if (null != startTimeString)
        {
            if (false == validateUnixTimestampString(startTimeString))
            {
                // Failed validation on the passed time string, so return a 400 Bad Request
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
            }

            // We're going to convert from Unix timestamp string -> java.util.Date -> java.sql.Timestamp

            // First step : Unix timestamp string -> java.util.Date
            Date startTimeIntermediary = new Date(Long.parseLong(startTimeString) * 1000);

            // Second step : java.util.Date -> java.sql.Timestamp
            startTime = new Timestamp(startTimeIntermediary.getTime());
        }

        // Validate 'end' time string if it's present - should be Unix timestamp format - and set it up for usage
        if (null != endTimeString)
        {
            if (false == validateUnixTimestampString(endTimeString))
            {
                // Failed validation on the passed time string, so return a 400 Bad Request
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
            }

            // We're going to convert from Unix timestamp string -> java.util.Date -> java.sql.Timestamp

            // First step : Unix timestamp string -> java.util.Date
            Date endTimeIntermediary = new Date(Long.parseLong(endTimeString) * 1000);

            // Second step : java.util.Date -> java.sql.Timestamp
            endTime = new Timestamp(endTimeIntermediary.getTime());
        }

        // We need to look up this room to make sure it exists, before looking for possible appointments

        Room r = this.roomService.getRoomByName(roomName);

        if (r == null)
        {
            // Could not find that room so return a 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(null);
        }

        // TODO maybe refactor to return some sort of availability DTO instead of Appointment objects ?

        List<Appointment> appointments = this.appointmentService.findByRoomName(roomName);

        // If no filter was passed for start time *or* end time then we can just return the whole list as-is
        if (null == startTime && null == endTime)
        {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(appointments);
        }

        // We need to filter our overall Appointment results based on time, so make a place to do that
        List<Appointment> filteredAppointments = new ArrayList<Appointment>();

        // Logical loop for filtering + population of filteredAppointments
        for (Appointment appointment : appointments)
        {
            boolean startOk = true;
            boolean endOk = true;

            // If we have a start time filter, make sure this Appointment is okay
            if (null != startTime)
            {
                if (appointment.getStart().before(startTime))
                {
                    startOk = false;
                }
            }

            // If we have an end time filter, make sure this Appointment is okay
            if (null != endTime)
            {
                if (appointment.getEnd().after(endTime))
                {
                    endOk = false;
                }
            }

            if (startOk && endOk) filteredAppointments.add(appointment);
        }

        // Return the *filtered* list of Appointment results
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(filteredAppointments);
    }
}

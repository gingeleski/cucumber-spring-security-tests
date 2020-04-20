package roombook.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import roombook.model.appointment.Appointment;
import roombook.model.room.Room;
import roombook.service.AppointmentService;
import roombook.service.RoomService;

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
        return (!roomName.isEmpty() && roomName.matches("[a-zA-Z0-9_-]*") && roomName.length() <= 50);
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
    public ResponseEntity getRoomAvailabilityByName(@PathVariable String roomName, HttpServletResponse res)
    {
        // TODO get optional start and end time query parameters on this endpoint

        if (false == validateRoomName(roomName))
        {
            // Failed validation on the passed name, so return a 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
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

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(appointments);
    }
}

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

    private boolean validateRoomName(String roomName)
    {
        return (!roomName.isEmpty() && roomName.matches("[a-zA-Z0-9_-]*"));
    }

    // GET /api/rooms
    @GetMapping("/rooms")
    public ResponseEntity getRooms(HttpServletResponse res)
    {
        //return ResponseEntity.status(HttpStatus.OK).body(this.roomService.getRooms());
        return ResponseEntity.ok("");
    }

    // GET /api/rooms/{roomName}
    @GetMapping("/rooms/{roomName}")
    public ResponseEntity getRoomByName(@PathVariable String roomName, HttpServletResponse res) throws Throwable
    {
        /*
        if (false == validateRoomName(roomName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Room r = this.roomService.getRoomByName(roomName);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(r == null ? null : r.toJSONString()); */
        return ResponseEntity.ok("");
    }

    // GET /api/rooms/{roomName}/availability
    @GetMapping("/rooms/{roomName}/availability")
    public ResponseEntity getRoomAvailabilityByName(@PathVariable String roomName, HttpServletResponse res)
    {
        if (false == validateRoomName(roomName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // TODO maybe refactor to return some sort of availability DTO instead of Appointment objects ?

        // TODO get optional start and end time parameters
/*
        JSONArray roomBookings = new JSONArray();

        for (Appointment a : this.appointmentService.findByRoomName(roomName, null, null))
        {
            roomBookings.add(a);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(roomBookings.toJSONString());   */
        return ResponseEntity.ok("");
    }
}

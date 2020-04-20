package roombook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roombook.service.AppointmentService;
import roombook.service.RoomService;

@RestController
@RequestMapping("/api")
public class AdminController
{
    private final AppointmentService appointmentService;
    private final RoomService roomService;

    public AdminController(AppointmentService appointmentService, RoomService roomService)
    {
        this.appointmentService = appointmentService;
        this.roomService = roomService;
    }

    // POST /api/admin/clearAllRooms
    @PostMapping("/admin/clearAllRooms")
    public ResponseEntity adminDeleteRooms()
    {
        roomService.deleteAll();

        // Return a 204 No Content - successful action, empty response body
        return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).body(null);
    }

    // POST /api/admin/clearAllReservations
    @PostMapping("/admin/clearAllReservations")
    public ResponseEntity adminDeleteAppointments()
    {
        appointmentService.deleteAll();

        // Return a 204 No Content - successful action, empty response body
        return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).body(null);
    }
}

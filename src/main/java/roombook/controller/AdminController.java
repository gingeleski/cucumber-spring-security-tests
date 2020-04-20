package roombook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminController
{
    // POST /api/admin/clearAllRooms
    @PostMapping("/admin/clearAllRooms")
    public ResponseEntity adminDeleteRooms()
    {
        // TODO implement this function

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.APPLICATION_JSON).body(null);
    }

    // POST /api/admin/clearAllReservations
    @PostMapping("/admin/clearAllReservations")
    public ResponseEntity adminDeleteAppointments()
    {
        // TODO implement this function

        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).contentType(MediaType.APPLICATION_JSON).body(null);
    }
}

package roombook.appointment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private List<Appointment> appointments;

    public AppointmentController() {
        this.appointments = new ArrayList<>();
    }
}

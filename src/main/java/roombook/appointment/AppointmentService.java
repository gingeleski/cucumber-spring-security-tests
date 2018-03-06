package roombook.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Bean
    AppointmentRepository appointmentRepository() {
        return new AppointmentRepository();
    }

    public List<Appointment> findByRoomName(String roomName, Long start, Long end) {
        Date startDate = null;
        Date endDate = null;

        if (start != null) {
            startDate = new Date(start);
        }

        if (end != null) {
            endDate = new Date(end);
        }

        return this.appointmentRepository.findByRoomName(roomName, startDate, endDate);
    }

    public void save(Appointment appointment) {
        this.appointmentRepository.save(appointment);
    }
}

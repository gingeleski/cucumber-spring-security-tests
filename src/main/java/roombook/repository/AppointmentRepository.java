package roombook.repository;

//import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import roombook.model.appointment.Appointment;

//import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>
{
    // To be determined - methods we need to support API functionality
}

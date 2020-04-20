package roombook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import roombook.model.appointment.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>
{
    List<Appointment> findByRoomName(String roomName);
}

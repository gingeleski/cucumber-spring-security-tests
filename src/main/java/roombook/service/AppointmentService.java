package roombook.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roombook.security.SecurityUtils;
import roombook.model.appointment.Appointment;
import roombook.repository.AppointmentRepository;

import java.util.List;

@Service
@Transactional
public class AppointmentService
{
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository)
    {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> findByRoomName(String roomName)
    {
        return this.appointmentRepository.findByRoomName(roomName);
    }

    public void deleteAll()
    {
        this.appointmentRepository.deleteAll();
    }
}

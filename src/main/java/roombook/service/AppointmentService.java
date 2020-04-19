package roombook.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roombook.security.SecurityUtils;
import roombook.model.appointment.Appointment;
import roombook.repository.AppointmentRepository;

import java.util.Optional;

@Service
@Transactional
public class AppointmentService
{
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository)
    {
        this.appointmentRepository = appointmentRepository;
    }
/*
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
*/
}

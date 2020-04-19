package roombook.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roombook.model.appointment.Appointment;
import roombook.model.room.Room;

public class AppointmentRepository {

    private List<Appointment> appointments = new ArrayList<>();

    public List<Appointment> findByRoomName(String roomName) {
        return findByRoomName(roomName, null, null);
    }

    public List<Appointment> findByRoomName(String roomName, Date start, Date end) {
        if (start == null || start.before(new Date())) {
            start = new Date(); // Current time
        }
        if (end == null) {
            end = new Date(Long.MAX_VALUE);
        }
        List<Appointment> matches = new ArrayList<>();
        for (Appointment a : this.appointments) {
            if (a.getRoom().equalsIgnoreCase(roomName)) {
                if (a.getStart().compareTo(start) <= 0) {
                    if (a.getEnd().compareTo(end) >= 0) {
                        matches.add(a);
                    }
                }
            }
        }
        return matches;
    }

    public void save(Appointment appointment) {
        this.appointments.add(appointment);
    }
}

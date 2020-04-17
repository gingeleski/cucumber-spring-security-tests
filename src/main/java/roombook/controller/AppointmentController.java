package roombook.controller;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roombook.model.appointment.Appointment;
import roombook.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    public AppointmentController() {
        initialLoadFromJsonFile();
    }

    private Date getDateFromUnixTime(long unixTimestamp) {
        return new Date(unixTimestamp * 1000L);
    }

    private void initialLoadFromJsonFile() {

        try {
            String jsonFilePath
                    = AppointmentController.class.getClassLoader().getResource("appointments.json").getPath();

            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader(jsonFilePath));

            for (Object o : a)
            {
                Appointment appointment = new Appointment();
                JSONObject jsonAppointment = (JSONObject) o;

                appointment.setName((String) jsonAppointment.get("name"));
                appointment.setRoom((String) jsonAppointment.get("room"));
                appointment.setOwner((String) jsonAppointment.get("owner"));
                appointment.setCreator((String) jsonAppointment.get("creator"));

                long startTime = (long) jsonAppointment.get("start");
                appointment.setStart(getDateFromUnixTime(startTime));

                long endTime = (long) jsonAppointment.get("end");
                appointment.setEnd(getDateFromUnixTime(endTime));

                this.appointmentService.save(appointment);
            }
        }
        catch (Exception e) {
            return;
        }
    }
}

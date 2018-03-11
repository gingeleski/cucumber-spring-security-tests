package roombook.room;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import roombook.appointment.Appointment;
import roombook.appointment.AppointmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.util.List;

import static java.lang.Math.toIntExact;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private AppointmentService appointmentService;

    public RoomController() {
        initialLoadFromJsonFile();
    }

    private void initialLoadFromJsonFile() {

        try {
            String jsonFilePath = RoomController.class.getClassLoader().getResource("rooms.json").getPath();

            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader(jsonFilePath));

            for (Object o : a)
            {
                Room room;
                JSONObject jsonRoom = (JSONObject) o;

                String type = (String) jsonRoom.get("type");

                if (type.equals("CONFERENCE")) {
                    room = new ConferenceRoom();
                }
                else if (type.equals("FOCUS")) {
                    room = new FocusRoom();
                }
                else { // "SHARE"
                    room = new ShareRoom();
                }

                room.setName((String) jsonRoom.get("name"));
                room.setSeats(toIntExact((long) jsonRoom.get("seats")));

                this.roomService.save(room);
            }
        }
        catch (Exception e) {
            return;
        }
    }

    private boolean validateRoomName(String roomName)
    {
        return (!roomName.isEmpty() && roomName.matches("[a-zA-Z0-9_-]*"));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public List<Room> getRooms(HttpServletResponse res) {
        return this.roomService.getRooms();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/{roomName}")
    public ResponseEntity getRoomByName(@PathVariable String roomName, HttpServletResponse res) throws Throwable
    {
        if (false == validateRoomName(roomName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Room r = this.roomService.getRoomByName(roomName);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(r == null ? null : r.toJSONString());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/{roomName}/availability")
    public ResponseEntity getRoomAvailabilityByName(@PathVariable String roomName, HttpServletResponse res)
    {
        if (false == validateRoomName(roomName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // TODO refactor to return AvailabilityBlock instead of Appointment

        // TODO get start and end time parameters

        JSONArray roomBookings = new JSONArray();

        for (Appointment a : this.appointmentService.findByRoomName(roomName, null, null))
        {
            roomBookings.add(a);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(roomBookings.toJSONString());
    }
}

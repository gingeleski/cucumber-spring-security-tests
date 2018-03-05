package roombook.room;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import roombook.appointment.AvailabilityBlock;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private List<Room> rooms;

    private void initialLoadFromJsonFile() {
        this.rooms = new ArrayList<>();

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

                this.rooms.add(room);
            }
        }
        catch (Exception e) {
            return;
        }
    }

    public RoomController() {
        initialLoadFromJsonFile();
    }

    // TODO access control
    @RequestMapping(method = RequestMethod.GET)
    public List<Room> getRooms() {
        return this.rooms;
    }

    // TODO access control
    @RequestMapping(method = RequestMethod.GET, value = "/{roomName}")
    public Room getRoomByName(@PathVariable String roomName) {
        for (Room room : this.rooms) {
            if (room.getName().equalsIgnoreCase(roomName)) {
                return room;
            }
        }
        return null;
    }

    // TODO access control
    @RequestMapping(method = RequestMethod.GET, value = "/{roomName}/availability")
    public List<AvailabilityBlock> getRoomAvailabilityByName(@PathVariable String roomName) {
        return null; // TODO
    }
}

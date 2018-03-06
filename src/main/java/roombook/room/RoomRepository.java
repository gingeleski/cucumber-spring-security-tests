package roombook.room;

import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private List<Room> rooms = new ArrayList<>();

    public List<Room> getRooms() {
        return this.rooms;
    }

    public Room getRoomByName(String name) {
        for (Room room : this.rooms) {
            if (room.getName().equalsIgnoreCase(name)) {
                return room;
            }
        }
        return null;
    }

    public void save(Room room) {
        this.rooms.add(room);
    }
}

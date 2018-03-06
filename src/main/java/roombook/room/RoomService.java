package roombook.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Bean
    RoomRepository roomRepository() {
        return new RoomRepository();
    }

    public List<Room> getRooms() {
        return this.roomRepository.getRooms();
    }

    public Room getRoomByName(String name) {
        return this.roomRepository.getRoomByName(name);
    }

    public void save(Room room) {
        this.roomRepository.save(room);
    }
}

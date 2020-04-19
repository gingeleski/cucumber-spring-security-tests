package roombook.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roombook.security.SecurityUtils;
import roombook.model.room.Room;
import roombook.repository.RoomRepository;

import java.util.Optional;

@Service
@Transactional
public class RoomService
{
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository)
    {
        this.roomRepository = roomRepository;
    }
    /*
    public List<Room> getRooms()
    {
        return this.roomRepository.getRooms();
    }

    public Room getRoomByName(String name)
    {
        return this.roomRepository.getRoomByName(name);
    }

    public void save(Room room)
    {
        this.roomRepository.save(room);
    }
*/
}

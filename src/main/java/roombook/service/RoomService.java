package roombook.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roombook.security.SecurityUtils;
import roombook.model.room.Room;
import roombook.repository.RoomRepository;

import java.util.List;
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

    public List<Room> getRooms()
    {
        return this.roomRepository.findAll();
    }

    public Room getRoomByName(String name)
    {
        Optional<Room> roomResult = this.roomRepository.findByRoomName(name);

        // If the Optional wrapper returns true to isPresent() then we can actually get() and return
        if (roomResult.isPresent())
        {
            return roomResult.get();
        }

        // No result for that name
        return null;
    }

    public void deleteAll()
    {
        this.roomRepository.deleteAll();
    }
}

package roombook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import roombook.model.room.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long>
{
    Optional<Room> findByRoomName(String roomName);
}

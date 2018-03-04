package roombook.room;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private List<Room> rooms;

    public RoomController() {
        this.rooms = new ArrayList<>();
    }
}

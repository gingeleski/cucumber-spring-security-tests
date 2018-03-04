package roombook;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RoomBookController {

    @RequestMapping("/")
    public String index() {
        return "Hello Sample Spring Boot API!";
    }

}
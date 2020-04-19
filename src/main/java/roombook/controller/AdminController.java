package roombook.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminController
{
    @GetMapping("/admin")
    public ResponseEntity<HiddenMessage> getAdminProtectedGreeting()
    {
        return ResponseEntity.ok(new HiddenMessage("Secret content here"));
    }

    private static class HiddenMessage
    {
        private final String message;

        private HiddenMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

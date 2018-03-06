package roombook.user;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import roombook.security.SecurityUtils;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static roombook.security.SecurityUtils.HEADER_STRING;
import static roombook.security.SecurityUtils.TOKEN_PREFIX;

@RestController
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        initialLoadFromJsonFile();
    }

    private void initialLoadFromJsonFile() {
        try {
            String jsonFilePath = UserController.class.getClassLoader().getResource("users.json").getPath();

            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader(jsonFilePath));

            for (Object o : a)
            {
                User user;
                JSONObject jsonUser = (JSONObject) o;

                String type = (String) jsonUser.get("type");

                if (type.equals("ADMIN")) {
                    user = new AdminUser();
                }
                else if (type.equals("HR_MGR")) {
                    user = new HRManagerUser();
                }
                else if (type.equals("ASSISTANT")) {
                    user = new AssistantUser();
                }
                else { // "EMPLOYEE"
                    user = new EmployeeUser();
                }

                user.setUsername((String) jsonUser.get("username"));
                user.setPassword((String) jsonUser.get("password"));

                this.userRepository.save(user);
            }
        }
        catch (Exception e) {
            return;
        }
    }

    @PermitAll
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public void login(@RequestParam(value = "username", defaultValue = "") String username,
                         @RequestParam(value = "password", defaultValue = "") String password, HttpServletResponse res) {

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Thinks username or password is empty");
            res.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        User user = this.userRepository.findByUsername(username);

        if (user != null && password.equals(user.getPassword())) {
            res.addHeader(HEADER_STRING, TOKEN_PREFIX + SecurityUtils.generateToken(user.getUsername()));
            res.setStatus(HttpStatus.OK.value());
        }
        else {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public void logout(HttpServletResponse res) {
        res.setStatus(HttpStatus.OK.value());
    }
}


package roombook.user;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private List<User> users;

    private void initialLoadFromJsonFile() {
        this.users = new ArrayList<>();

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

                this.users.add(user);
            }
        }
        catch (Exception e) {
            return;
        }
    }

    public UserController() {
        initialLoadFromJsonFile();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public ResponseEntity getLogin(@CookieValue("JSESSIONID") String sessionCookie) {
        if (sessionCookie.isEmpty()) {
            // not logged in, needs to POST so this GET is wrong method
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        // user is logged in so give 200 OK
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


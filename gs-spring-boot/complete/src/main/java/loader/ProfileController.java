package loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import service.LoginService;
import service.TransactionManagerService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RestController
public class ProfileController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private TransactionManagerService txMgrService;

    @RequestMapping(value = "/loginPersons", method = RequestMethod.GET)
    public String index(@RequestParam String name, @RequestParam String password, HttpServletRequest request) throws IOException {
        if (loginService.isValidUser(name, password)==true){
            System.out.println("Successful login!!");
            redirection(request.getRemoteAddr(),password);
            return "Greetings"+name+" from Spring Boots!" + password +" you entered";
        }
        else 
            return "Invalid login attempt";
    }

    public void redirection(String name, @RequestParam String password) {
        try{
            txMgrService.insertIntoDB(name,password, (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    
}

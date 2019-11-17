package loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.LoginService;
import service.TransactionManagerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TransactionManagerService txMgrService;

    // inject via application.properties

    @Value("${welcome.message:test}")
    private String message = "Hello World";

    @RequestMapping("/")
    public String welcome(Map<String, Object> model,Model models, HttpSession session) {
        model.put("message", this.message);
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if (messages == null) {
			messages = new ArrayList<>();
		}
		session.setAttribute("MY_SESSION_MESSAGES",messages );
		models.addAttribute("sessionMessages", messages);
        return "welcome";
    }

    @RequestMapping(value = "/loginPersons", method = RequestMethod.POST)
    public String index(HttpServletRequest request) throws IOException {
    	String name = request.getParameter("name");
    	String password = request.getParameter("password");
    	List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (messages == null) {
			Model models = null;
			return welcome(new HashMap<String,Object>(),models,request.getSession());
		}
		messages.add(name+":"+password);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		
		  if (loginService.isValidUser(name, password) == true) {
		  System.out.println("Successful login!!");
		  redirection(request.getRemoteAddr(), password); return "dashboard"; } else
		  return "error";
    }
    
    @RequestMapping(value ="/destroy",method = RequestMethod.POST)
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "/";
	}

    public void redirection(String name, String password) {
        try {
            txMgrService.insertIntoDB(name, password, (long) Math.floor(Math.random() *
                    9_000_000_000L) + 1_000_000_000L);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}

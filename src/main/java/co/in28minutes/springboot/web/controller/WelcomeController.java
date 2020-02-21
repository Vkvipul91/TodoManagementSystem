package co.in28minutes.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class WelcomeController {


    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String showWelcomePage(ModelMap model){
		model.put("name", getLoggedInUserName(model));
		return "welcome";
    }

    /**
     * @return
     * Logged-in user in spring security is called "Principal"
     * "userDeatils" is a spring class
     */
    private String getLoggedInUserName(ModelMap model) {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();

        return principal.toString();
    }
}

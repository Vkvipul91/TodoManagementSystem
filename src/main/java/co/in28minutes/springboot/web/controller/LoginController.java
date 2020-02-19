package co.in28minutes.springboot.web.controller;

import co.in28minutes.springboot.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class LoginController {
    @Autowired
    LoginService loginService ;

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String showLoginPage(){
        return "login";
    }

	
	 @RequestMapping(value = "/login", method = RequestMethod.POST) 
	 public String handleLogin(ModelMap model, @RequestParam String name,@RequestParam String password) { 
		 boolean isValidUser = loginService.validateUser(name,password); 
		 if(!isValidUser) {
			 model.put("errorMsg", "Invalid Username or Password"); 
			 return "login"; }
	 model.put("name", name); 
	 return "welcome"; 
	 }
	
}

package co.in28minutes.springboot.web.controller;

import co.in28minutes.springboot.web.service.LoginService;
import co.in28minutes.springboot.web.service.TodoService;

import java.util.Date;

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
public class TodoController {
    @Autowired
    TodoService todoService ;

    @RequestMapping(value ="/list-todos", method = RequestMethod.GET)
    public String showTodoList(ModelMap model){
    	String username = (String)model.get("name");
    	model.put("todos",todoService.retrieveTodos(username));
        return "list-todos";
    }
    
    @RequestMapping(value ="/add-todo", method = RequestMethod.GET)
    public String addTodo(ModelMap model){
    	return "todo";
    }
    
    @RequestMapping(value ="/add-todo", method = RequestMethod.POST)
    public String addTodoDesc(ModelMap model, @RequestParam String desc){
    	todoService.addTodo((String)model.get("name"), desc, new Date(), false);
    	return "redirect:/list-todos";
    }

	
	
	
}

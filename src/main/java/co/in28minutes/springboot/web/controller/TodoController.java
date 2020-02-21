package co.in28minutes.springboot.web.controller;

import co.in28minutes.springboot.web.model.Todo;
import co.in28minutes.springboot.web.service.LoginService;
import co.in28minutes.springboot.web.service.TodoService;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("name")
public class TodoController {
    @Autowired
    TodoService todoService ;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

    @RequestMapping(value ="/list-todos", method = RequestMethod.GET)
    public String showTodoList(ModelMap model){
    	String username = (String)model.get("name");
    	model.put("todos",todoService.retrieveTodos(username));
        return "list-todos";
    }
    
	/*
	 * @RequestMapping(value ="/add-todo", method = RequestMethod.GET) public String
	 * addTodo(ModelMap model){ return "todo"; }
	 */
    
    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		model.addAttribute("todo", new Todo(0, (String) model.get("name"), "Default Desc",
				new Date(), false));
		return "todo";
	}
    
	/*
	 * @RequestMapping(value ="/add-todo", method = RequestMethod.POST) public
	 * String addTodoDesc(ModelMap model, @RequestParam String desc){
	 * todoService.addTodo((String)model.get("name"), desc, new Date(), false);
	 * return "redirect:/list-todos"; }
	 */
    
    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		
		if(result.hasErrors()){
			return "todo";
		}
		
		todoService.addTodo((String) model.get("name"), todo.getDesc(), todo.getTargetDate(),
				false);
		return "redirect:/list-todos";
	}

    @RequestMapping(value="/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id){
		todoService.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	
    @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Todo todo = todoService.retrieveTodo(id);
		model.put("todo", todo);
		return "todo";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		
		todo.setUser((String) model.get("name"));
		todoService.updateTodo(todo);
		return "redirect:/list-todos";
	}
}

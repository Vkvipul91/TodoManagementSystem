package co.in28minutes.springboot.web.controller;

import co.in28minutes.springboot.web.model.Todo;
import co.in28minutes.springboot.web.service.TodoRepository;
import co.in28minutes.springboot.web.service.TodoService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {
    
	
	/*
	 * @Autowired TodoService todoService ;
	 */
    
    @Autowired
    private TodoRepository todoRepo;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

    @RequestMapping(value ="/list-todos", method = RequestMethod.GET)
    public String showTodoList(ModelMap model){
    	String username = getLoggedInUserName(model);
    	model.put("todos",todoRepo.findByUser(username));
    	//model.put("todos",todoService.retrieveTodos(username));
        return "list-todos";
    }

	/**
	 * Refactoring session name
	 */
	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();

		return principal.toString();
	}


	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		model.addAttribute("todo", new Todo(0, getLoggedInUserName(model), "Default Desc",
				new Date(), false));
		return "todo";
	}
    

    
    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		
		if(result.hasErrors()){
			return "todo";
		}
		
		todo.setUser(getLoggedInUserName(model));
		todoRepo.save(todo);
		
		/*
		 * todoService.addTodo(getLoggedInUserName(model), todo.getDesc(),
		 * todo.getTargetDate(), false);
		 */
		return "redirect:/list-todos";
	}

    @RequestMapping(value="/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id){
		todoRepo.deleteById(id);
    	//todoService.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	
    @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
    	Todo todo = todoRepo.findById(id).get();
    	//Todo todo = todoService.retrieveTodo(id);
		model.put("todo", todo);
		return "todo";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		
		todo.setUser(getLoggedInUserName(model));
		todoRepo.save(todo);
		//todoService.updateTodo(todo);
		return "redirect:/list-todos";
	}
}

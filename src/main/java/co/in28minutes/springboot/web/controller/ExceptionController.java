package co.in28minutes.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("error")
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex){
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", ex.getLocalizedMessage());
        mv.addObject("url",request.getRequestURL());
        mv.setViewName("error");
        return mv;
    }
}

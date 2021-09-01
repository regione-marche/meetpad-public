package conferenza.chatbot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
@RequestMapping("/chatbot")
public class WebController {
	
    @GetMapping(value="/")
    public String homepage(){
        return "chatbot";
    }
}
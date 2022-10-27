package app.controller.administrativo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Principal {

    @GetMapping("/administrativo")
    public String acessarPrincipal() {
        return "administrativo/home";
    }

    @GetMapping("/home")
    public String acessarHome() {
        return "home";
    }

    @GetMapping("/administrativo/grupo")
    public ModelAndView grupo(){
        ModelAndView model = new ModelAndView("/administrativo/funcionarios/grupo");
        return model;
    }
}

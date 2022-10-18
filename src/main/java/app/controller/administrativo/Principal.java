package app.controller.administrativo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Principal {

    @GetMapping("/administrativo")
    public String acessarPrincipal(){
        return "administrativo/home";
    }

    @GetMapping("/home")
    public String acessarHome(){
        return "home";
    }
}

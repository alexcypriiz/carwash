package ru.aisa.carwash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.aisa.carwash.model.Client;
import ru.aisa.carwash.service.ClientService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final ClientService clientService;

    public RegistrationController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("clientForm", new Client());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("clientForm") @Valid Client clientForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!clientForm.getPassword().equals(clientForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!clientService.saveClient(clientForm)) {
            model.addAttribute("usernameError", "Пользователь с такой почтой уже существует");
            return "registration";
        }

        return "redirect:/login";
    }
}

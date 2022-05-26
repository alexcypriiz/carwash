package ru.aisa.carwash.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aisa.carwash.model.Client;
import ru.aisa.carwash.model.OrderEntity;
import ru.aisa.carwash.model.WashOption;
import ru.aisa.carwash.service.OrderEntityService;
import ru.aisa.carwash.service.WashOptionService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/carwash")
public class MainController {

    final
    WashOptionService washOptionService;

    final
    OrderEntityService orderEntityService;

    public MainController(OrderEntityService orderEntityService, WashOptionService washOptionService) {
        this.orderEntityService = orderEntityService;
        this.washOptionService = washOptionService;
    }

    @GetMapping()
    public String findAll(Model model) {
        List<OrderEntity> orders = orderEntityService.findAll();
        model.addAttribute("orders", orders);
        return "main/view-orders-car-wash";
    }

    @GetMapping("/create")
    public String addOrderForm(Model model) {
        List<WashOption> selectedWashOptions = washOptionService.findAll();
        model.addAttribute("order", new OrderEntity());
        model.addAttribute("selectedWashOptions", selectedWashOptions);
        return "main/create-orders-car-wash";
    }

    @PostMapping("/create")
    public String addOrder(@ModelAttribute("order") @Valid OrderEntity orderEntity, @AuthenticationPrincipal Client client, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doubleOrderError", "Убедитесь в меню забронированных мест, что время начало времени и конца не входят в интервал чужого времени." +
                    "В меню забронированных мест можно перейти с помощью нажатия кнопки в верхнем правом углу");
            return "main/create-orders-car-wash";
        }
        if (!orderEntityService.saveOrder(orderEntity, client)) {
            model.addAttribute("orderError", "Данный интервал времени занят. Убедитесь, что Ваше время не попадает в занятый интервал времени.");
            return "main/create-orders-car-wash";
        }

        return "redirect:/carwash";
}


}

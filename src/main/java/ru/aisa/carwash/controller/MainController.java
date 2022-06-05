package ru.aisa.carwash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@Api(description = "Операции связанные с записью клиентов в очередь. *Доступ только зарегистрированным пользователям")
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

    @ApiOperation("Получение списка всех записанных клиентов и их забронированного времени с учетом оставшего времени(если пользователь ожидает своей очереди)")
    @GetMapping()
    public String findAll(@AuthenticationPrincipal Client client, Model model) throws ParseException {
        List<OrderEntity> orders = orderEntityService.findAllOrderByStartTime();

        String countDown = orderEntityService.countDown(orders, client);
        if (countDown != null) {
            model.addAttribute("endDate", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(countDown));
        }
        model.addAttribute("orders", orders);
        return "main/view-orders-car-wash";
    }

    @ApiOperation("Получение формы для записи в очередь")
    @GetMapping("/create")
    public String addOrderForm(Model model) {
        List<WashOption> selectedWashOptions = washOptionService.findAll();
        model.addAttribute("order", new OrderEntity());
        model.addAttribute("selectedWashOptions", selectedWashOptions);
        return "main/create-orders-car-wash";
    }

    @ApiOperation("Запись статистики в список очереди на мойку")
    @PostMapping("/create")
    public String addOrder(@ModelAttribute("order") @Valid OrderEntity orderEntity, @AuthenticationPrincipal Client client, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doubleOrderError", "Убедитесь в меню забронированных мест," +
                    " что время начало времени и конца не входят в интервал чужого времени." +
                    "В меню забронированных мест можно перейти с помощью нажатия кнопки в верхнем правом углу");
            return "main/create-orders-car-wash";
        }
        try {
            if (!orderEntityService.saveOrder(orderEntity, client)) {
                model.addAttribute("orderError", "Данный интервал времени занят. Убедитесь," +
                        " что Ваше время не попадает в занятый интервал времени.");
                return "main/create-orders-car-wash";
            }
        } catch (Exception e) {
            model.addAttribute("orderError", "Без выбранных услуг нельзя записать. " +
                    "Пожалуйста, выберите услугу из списка ниже.");
            return "main/create-orders-car-wash";
        }

        return "redirect:/carwash";
    }


}


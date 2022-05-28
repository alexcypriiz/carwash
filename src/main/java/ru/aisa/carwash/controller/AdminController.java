package ru.aisa.carwash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aisa.carwash.model.WashOption;
import ru.aisa.carwash.service.WashOptionService;

import javax.validation.Valid;
import java.util.List;

@Controller
@Api(description = "Операции связанные с услугами для клиентов. *Доступ только с индификатором ADMIN_ROLE")
@RequestMapping("/admin")
public class AdminController {
    final
    WashOptionService washOptionService;

    public AdminController(WashOptionService washOptionService) {
        this.washOptionService = washOptionService;
    }

    @ApiOperation("Получение списка всех услуг")
    @GetMapping()
    public String findAll(Model model) {
        List<WashOption> washOptions = washOptionService.findAll();
        model.addAttribute("washOptions", washOptions);
        return "admin/view-wash-option";
    }

    @ApiOperation("Получение формы создания услуги")
    @GetMapping("/create")
    public String createWashOptionForm(Model model) {
        model.addAttribute("washOption", new WashOption());
        return "admin/create-wash-option";
    }

    @ApiOperation("Запись созданной новой услуги")
    @PostMapping("/create")
    public String addWashOption(@ModelAttribute("washOption") @Valid WashOption washOption, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/create-wash-option";
        }
        if (!washOptionService.save(washOption)) {
            model.addAttribute("nameError", "Услуга с таким именем уже существует");
            return "admin/create-wash-option";
        }

        return "redirect:/admin";
    }

    @ApiOperation("Получение формы редактирования имеющейся услуги. Принимает id услуги.")
    @GetMapping("/update/{id}")
    public String updateWashOptionForm(@PathVariable("id") Long id, Model model) {
        WashOption washOption = washOptionService.findId(id);
        model.addAttribute("washOption", washOption);
        return "admin/update-wash-option";
    }

    @ApiOperation("Запись отредактированой услуги")
    @PostMapping("/update")
    public String updateWashOption(@ModelAttribute("washOption") @Valid WashOption washOption, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/update-wash-option";
        }
        washOptionService.update(washOption);
        return "redirect:/admin";
    }

    @ApiOperation("Удаление услуги. Принимает id услуги.")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        washOptionService.delete(id);
        return "redirect:/admin";
    }
}

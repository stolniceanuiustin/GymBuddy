package com.gymbuddy.backend.controller;

import com.gymbuddy.backend.model.GymDay;
import com.gymbuddy.backend.service.GymDayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/gymdays")
public class GymDayController {
    private final GymDayService gymDayService;

    public GymDayController(GymDayService gymDayService) {
        this.gymDayService = gymDayService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/gymdays";
    }

    @GetMapping("")
    public String showGymDays(Model model) {
        List<GymDay> gymDays = gymDayService.getAllGymDays();
        model.addAttribute("gymDays", gymDays);
        return "gymdays/list-gymdays";
    }

    @PostMapping("/save")
    public String saveOwner(@ModelAttribute GymDay gymDay) {
        gymDayService.addGymDay(gymDay);
        return "redirect:/gymdays";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        try{
            GymDay gymDay = gymDayService.getGymDayById(id);
            model.addAttribute("gymDay", gymDay);
            return "gymdays/edit-gymday";
        } catch (NoSuchElementException e) {
            return "redirect:/gymdays";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteGymDay(@PathVariable Long id) {
        gymDayService.deleteGymDay(id);
        return "redirect:/gymdays";
    }
}

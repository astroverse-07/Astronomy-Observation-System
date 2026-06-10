package org.astronomy.controller;

import org.astronomy.exception.InvalidDataException;
import org.astronomy.model.Observer;
import org.astronomy.service.AstronomySystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ObserverController {

    private final AstronomySystem system;

    public ObserverController(AstronomySystem system) {
        this.system = system;
    }

    @GetMapping("/observers")
    public String observers(Model model) {
        model.addAttribute("observers", system.getObservers());
        return "observers";
    }

    @PostMapping("/observers/add")
    public String addObserver(@RequestParam("observerID") int id,
                              @RequestParam("observerName") String name,
                              @RequestParam("experienceLevel") String experienceLevel,
                              @RequestParam("location") String location,
                              Model model) {
        try {
            Observer newObserver = new Observer(id, name, experienceLevel, location);

            system.addObserver(newObserver);

            return "redirect:/observers";
        } catch (InvalidDataException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("observers", system.getObservers());
            return "observers";
        }
    }
}
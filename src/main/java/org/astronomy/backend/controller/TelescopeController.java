package org.astronomy.backend.controller;

import org.astronomy.backend.exception.InvalidDataException;
import org.astronomy.backend.model.Telescope;
import org.astronomy.backend.service.AstronomySystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TelescopeController {

    private final AstronomySystem system;

    public TelescopeController(AstronomySystem system) {
        this.system = system;
    }

    @GetMapping("/telescopes")
    public String telescopes(Model model) {
        model.addAttribute("telescopes", system.getTelescopes());
        return "telescopes";
    }

    @PostMapping("/telescopes/add")
    public String addTelescope(@RequestParam("telescopeID") int id,
                               @RequestParam("modelName") String modelName,
                               @RequestParam("maxMagnification") int maxMagnification,
                               @RequestParam("apertureSize") double apertureSize,
                               @RequestParam(value = "motorized", defaultValue = "false") boolean motorized,
                               Model model) {
        try {
            Telescope newTelescope = new Telescope(id, modelName, maxMagnification, apertureSize, motorized);
            system.addTelescope(newTelescope);
            return "redirect:/telescopes";
        } catch (InvalidDataException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("telescopes", system.getTelescopes());
            return "telescopes";
        }
    }
}
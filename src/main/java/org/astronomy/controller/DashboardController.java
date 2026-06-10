package org.astronomy.controller;

import org.astronomy.service.AstronomySystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final AstronomySystem system;

    public DashboardController(AstronomySystem system) {
        this.system = system;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalObjects", system.getCelestialObjects().size());
        model.addAttribute("totalObservers", system.getObservers().size());
        model.addAttribute("totalTelescopes", system.getTelescopes().size());
        model.addAttribute("totalSessions", system.getSessions().size());
        model.addAttribute("celestialObjects", system.getCelestialObjects());
        model.addAttribute("observers", system.getObservers());
        return "dashboard";
    }
}
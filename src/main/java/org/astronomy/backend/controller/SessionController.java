package org.astronomy.backend.controller;

import org.astronomy.backend.model.CelestialObject;
import org.astronomy.backend.model.Observer;
import org.astronomy.backend.model.Telescope;
import org.astronomy.backend.service.AstronomySystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {

    private final AstronomySystem system;

    public SessionController(AstronomySystem system) {
        this.system = system;
    }

    @GetMapping("/sessions")
    public String sessions(Model model) {
        model.addAttribute("sessions", system.getSessions());
        model.addAttribute("observers", system.getObservers());
        model.addAttribute("telescopes", system.getTelescopes());
        model.addAttribute("celestialObjects", system.getCelestialObjects());
        return "sessions";
    }

    @PostMapping("/sessions/add")
    public String scheduleSession(@RequestParam("sessionId") int sessionId,
                                  @RequestParam("observerId") int observerId,
                                  @RequestParam("telescopeId") int telescopeId,
                                  @RequestParam("objectName") String objectName,
                                  @RequestParam("date") String date,
                                  @RequestParam("startHour") int startHour,
                                  @RequestParam("durationMinutes") int durationMinutes,
                                  @RequestParam(value = "notes", required = false, defaultValue = "") String notes,
                                  Model model) {
        try {
            // Locate core object instances using functional streaming searches
            Observer obs = system.getObservers().stream()
                    .filter(o -> o.getObserverID() == observerId)
                    .findFirst()
                    .orElse(null);

            Telescope tel = system.getTelescopes().stream()
                    .filter(t -> t.getTelescopeID() == telescopeId)
                    .findFirst()
                    .orElse(null);

            CelestialObject target = system.getCelestialObjects().stream()
                    .filter(obj -> obj.getName().equals(objectName))
                    .findFirst()
                    .orElse(null);

            if (obs == null || tel == null || target == null) {
                model.addAttribute("errorMessage", "Invalid entity association selected.");
                attachSystemData(model);
                return "sessions";
            }

            // Execute programmatic conduct logic checking visibility, magnitude, etc.
            system.scheduleSession(obs, tel, target, date, startHour, durationMinutes, notes, sessionId);
            return "redirect:/sessions";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Scheduling error: " + e.getMessage());
            attachSystemData(model);
            return "sessions";
        }
    }

    private void attachSystemData(Model model) {
        model.addAttribute("sessions", system.getSessions());
        model.addAttribute("observers", system.getObservers());
        model.addAttribute("telescopes", system.getTelescopes());
        model.addAttribute("celestialObjects", system.getCelestialObjects());
    }
}
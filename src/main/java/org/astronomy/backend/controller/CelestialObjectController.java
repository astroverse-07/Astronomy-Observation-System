package org.astronomy.backend.controller;

import org.astronomy.backend.exception.InvalidDataException;
import org.astronomy.backend.model.*;
import org.astronomy.backend.service.AstronomySystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CelestialObjectController {

    private final AstronomySystem system;

    public CelestialObjectController(AstronomySystem system) {
        this.system = system;
    }

    @GetMapping("/objects")
    public String objects(Model model) {
        model.addAttribute("celestialObjects", system.getCelestialObjects());
        return "objects";
    }

    @PostMapping("/objects/add")
    public String addCelestialObject(@RequestParam("name") String name,
                                     @RequestParam("lightYears") int lightYears,
                                     @RequestParam("magnitude") double magnitude,
                                     @RequestParam("rightAscension") int rightAscension,
                                     @RequestParam("declination") int declination,
                                     @RequestParam("constellation") String constellation,
                                     @RequestParam("objectType") String objectType,
                                     @RequestParam(value = "spectralClass", required = false, defaultValue = "G") String spectralClass,
                                     @RequestParam(value = "isNebula", defaultValue = "false") boolean isNebula,
                                     @RequestParam(value = "numberOfMoons", required = false, defaultValue = "0") int numberOfMoons,
                                     @RequestParam(value = "hasRings", defaultValue = "false") boolean hasRings,
                                     @RequestParam(value = "galaxyType", required = false, defaultValue = "Spiral") String galaxyType,
                                     @RequestParam(value = "estimatedStars", required = false, defaultValue = "0") int estimatedStars,
                                     Model model) {
        try {
            CelestialObject targetObject;

            switch (objectType.toUpperCase()) {
                case "STAR":
                    targetObject = new Star(name, lightYears, magnitude, rightAscension, declination, constellation, isNebula, spectralClass);
                    break;
                case "PLANET":
                    targetObject = new Planet(name, lightYears, magnitude, rightAscension, declination, constellation, numberOfMoons, hasRings);
                    break;
                case "GALAXY":
                    targetObject = new Galaxy(name, lightYears, magnitude, rightAscension, declination, constellation, galaxyType, estimatedStars);
                    break;
                default:
                    throw new InvalidDataException("Unsupported celestial object type specified.");
            }

            system.addCelestialObject(targetObject);
            return "redirect:/objects";
        } catch (InvalidDataException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("celestialObjects", system.getCelestialObjects());
            return "objects";
        }
    }
}
package frontend.view;

import backend.controller.AstronomySystem;
import backend.filehandler.ObservationLog;
import backend.model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args){
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        AstronomySystem system = new AstronomySystem();
        while (flag){
            System.out.println("====Astronomy Observation System====");
            System.out.println("1. Add Observer");
            System.out.println("2. Add Telescope");
            System.out.println("3. Add Celestial Object");
            System.out.println("4. Schedule Session");
            System.out.println("5. View Session");
            System.out.println("6. View Observers");
            System.out.println("7. View Telescopes");
            System.out.println("8. View Celestial Objects");
            System.out.println("9. Logs");
            System.out.println("10. Exit");

            int choice = sc.nextInt();

            if (choice==1){
                System.out.print("Enter Observer ID: ");
                int observerId = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Observer Name: ");
                String observerName = sc.nextLine();
                System.out.print("Enter Experience Level: ");
                String experienceLevel = sc.nextLine();
                System.out.print("Enter Location: ");
                String location = sc.nextLine();
                Observer observer = new Observer(observerId, observerName, experienceLevel, location);
                system.addObserver(observer);
                System.out.println("Observer Added Successfully!");
            }
            else if (choice==2) {
                System.out.print("Enter Telescope ID: ");
                int telescopeId = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Telescope Model: ");
                String model = sc.nextLine();
                System.out.print("Enter Max Magnification: ");
                int magnification = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Aperture Size: ");
                double aperture = sc.nextDouble();
                System.out.print("Is motorized (true/false): ");
                boolean motorized = sc.nextBoolean();
                Telescope telescope = new Telescope(telescopeId, model, magnification, aperture, motorized);
                system.addTelescope(telescope);
                System.out.println("Telescope Added Successfully!");
            }
            else if (choice==3) {
                System.out.println("Choose Object Type:");
                System.out.println("1. Star");
                System.out.println("2. Planet");
                System.out.println("3. Galaxy");
                int objChoice = sc.nextInt();
                sc.nextLine();

                CelestialObject object = null;

                System.out.print("Enter Name: ");
                String name = sc.nextLine();
                System.out.print("Enter LightYears: ");
                int lightYears = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Magnitude: ");
                double magnitude = sc.nextDouble();
                System.out.print("Enter Right Ascension: ");
                int ascension = sc.nextInt();
                System.out.print("Enter Declination: ");
                int declination = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter Constellation: ");
                String constellation = sc.nextLine();

                if (objChoice==1){
                    System.out.print("Enter Spectral Class: ");
                    String specClass = sc.nextLine();
                    System.out.print("Is Nebula? (true/false): ");
                    boolean nebula = sc.nextBoolean();
                    object = new Star(name, lightYears, magnitude, ascension, declination, constellation, nebula, specClass);
                    System.out.println("Star Added Successfully!");
                }
                else if (objChoice==2) {
                    System.out.print("Enter Number of Moons: ");
                    int moons = sc.nextInt();
                    System.out.print("Has Rings? (true/false): ");
                    boolean rings = sc.nextBoolean();
                    object = new Planet(name, lightYears, magnitude, ascension, declination, constellation, moons, rings);
                    System.out.println("Planet Added Successfully!");
                }
                else if (objChoice==3) {
                    System.out.print("Enter Galaxy Type: ");
                    String galaxyType = sc.nextLine();
                    System.out.print("Enter Estimated Stars: ");
                    int stars = sc.nextInt();
                    object = new Galaxy(name, lightYears, magnitude, ascension, declination, constellation, galaxyType, stars);
                    System.out.println("Galaxy Added Successfully!");
                }
                else {
                    System.out.println("Invalid Choice!");
                }
                if (object!=null){
                    system.addCelestialObject(object);
                }
            }
            else if (choice==4){
                System.out.print("Enter Session Id: ");
                int id = sc.nextInt();
                System.out.println("Available Observers are: ");
                for (int i = 0; i < system.getObservers().size(); i++) {
                    System.out.println(i + " - " + system.getObservers().get(i));
                }
                System.out.print("Select Observer Index: ");
                int observerIndex = sc.nextInt();
                Observer observer = system.getObservers().get(observerIndex);
                System.out.println("Available Telescopes are: ");
                for (int i = 0; i < system.getTelescopes().size(); i++){
                    System.out.println(i + " - " + system.getTelescopes().get(i));
                }
                System.out.print("Select Telescope Index: ");
                int telescopeIndex = sc.nextInt();
                Telescope telescope = system.getTelescopes().get(telescopeIndex);
                System.out.println("Celestial Objects are: ");
                for (int i = 0; i < system.getCelestialObjects().size(); i++){
                    System.out.println(i + " - " + system.getCelestialObjects().get(i));
                }
                System.out.print("Select Celestial Object Index: ");
                int celestialIndex = sc.nextInt();
                sc.nextLine();
                CelestialObject celestialObject = system.getCelestialObjects().get(celestialIndex);
                System.out.print("Enter Date: ");
                String date = sc.nextLine();
                System.out.print("Enter Start Hour: ");
                int startHour = sc.nextInt();
                System.out.print("Enter Duration (In Minutes): ");
                int duration = sc.nextInt();
                sc.nextLine();
                System.out.print("Notes: ");
                String notes = sc.nextLine();

                ObservationSession session = system.scheduleSession(observer, telescope, celestialObject, date, startHour, duration, notes, id);

                System.out.println("\n===== Session Result =====");
                System.out.println(session);

                if ("SUCCESS".equals(session.getStatus())) {
                    System.out.println("Observation SUCCESSFUL");
                }
            }
            else if (choice == 5) {
                System.out.println("\n==== All Observation Sessions ====");

                if (system.getSessions().isEmpty()) {
                    System.out.println("No sessions available.");
                } else {
                    for (int i = 0; i < system.getSessions().size(); i++) {
                        ObservationSession session = system.getSessions().get(i);

                        System.out.println("\nSession " + (i + 1));
                        System.out.println(session);

                        if ("FAILED".equals(session.getStatus())) {
                            System.out.println("Reason: " + session.getFailReason());
                        } else {
                            System.out.println("SUCCESSFUL OBSERVATION");
                        }

                        System.out.println("----------------------------------");
                    }
                }
            }
            else if (choice == 6) {

                System.out.println("\n==== Observers ====");

                if (system.getObservers().isEmpty()) {
                    System.out.println("No observers found.");
                }
                else {
                    for (int i = 0; i < system.getObservers().size(); i++) {
                        System.out.println((i + 1) + ". " + system.getObservers().get(i));
                    }
                }
            }
            else if (choice == 7) {

                System.out.println("\n==== Telescopes ====");

                if (system.getTelescopes().isEmpty()) {
                    System.out.println("No telescopes found.");
                }
                else {
                    for (int i = 0; i < system.getTelescopes().size(); i++) {
                        System.out.println((i + 1) + ". " + system.getTelescopes().get(i));
                    }
                }
            }
            else if (choice == 8) {

                System.out.println("\n==== Celestial Objects ====");

                if (system.getCelestialObjects().isEmpty()) {
                    System.out.println("No celestial objects found.");
                }
                else {
                    for (int i = 0; i < system.getCelestialObjects().size(); i++) {
                        System.out.println((i + 1) + ". " + system.getCelestialObjects().get(i));
                    }
                }
            }
            else if (choice == 9) {

                boolean logMenu = true;

                while (logMenu) {

                    System.out.println("\n==== Logs Menu ====");
                    System.out.println("1. View All Logs");
                    System.out.println("2. Search by Observer");
                    System.out.println("3. Search by Object");
                    System.out.println("4. Filter by Status (SUCCESS/FAILED)");
                    System.out.println("5. Clear Logs");
                    System.out.println("6. Back To Main Menu");
                    System.out.print("Enter Choice: ");

                    int logChoice = sc.nextInt();
                    sc.nextLine();

                    if (logChoice == 1) {

                        ArrayList<String> logs = ObservationLog.loadAllSessions();

                        if (logs.isEmpty()) {
                            System.out.println("No logs found.");
                        }
                        else {
                            System.out.println("\n==== All Logs ====");

                            for (String log : logs) {
                                System.out.println(log);
                            }
                        }
                    }

                    else if (logChoice == 2) {

                        System.out.print("Enter Observer Name: ");
                        String name = sc.nextLine();

                        ArrayList<String> results = ObservationLog.searchByObserver(name);

                        if (results.isEmpty()) {
                            System.out.println("No logs found for: " + name);
                        }
                        else {
                            System.out.println("\n==== Results ====");

                            for (String log : results) {
                                System.out.println(log);
                            }
                        }
                    }

                    else if (logChoice == 3) {

                        System.out.print("Enter Object Name: ");
                        String objectName = sc.nextLine();

                        ArrayList<String> results = ObservationLog.searchByObject(objectName);

                        if (results.isEmpty()) {
                            System.out.println("No logs found for: " + objectName);
                        }
                        else {
                            System.out.println("\n==== Results ====");

                            for (String log : results) {
                                System.out.println(log);
                            }
                        }
                    }

                    else if (logChoice == 4) {

                        System.out.print("Enter Status (SUCCESS/FAILED): ");
                        String status = sc.nextLine();

                        ArrayList<String> results = ObservationLog.filterByStatus(status);

                        if (results.isEmpty()) {
                            System.out.println("No logs found with status: " + status);
                        }
                        else {
                            System.out.println("\n==== Results ====");

                            for (String log : results) {
                                System.out.println(log);
                            }
                        }
                    }

                    else if (logChoice == 5) {

                        ObservationLog.clearLogs();
                    }

                    else if (logChoice == 6) {

                        logMenu = false;
                    }

                    else {

                        System.out.println("Invalid Choice!");
                    }
                }
            }
            else if (choice==10){
                flag = false;
                System.out.println("==== System Exited Successfully! ====");
            }
        }
    }
}

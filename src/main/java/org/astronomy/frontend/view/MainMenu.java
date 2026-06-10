package org.astronomy.frontend.view;

import org.astronomy.service.AstronomySystem;
import org.astronomy.controller.AnalyticsEngine;
import org.astronomy.filehandler.ObservationLog;
import org.astronomy.model.*;
import org.astronomy.exception.InvalidDataException;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        AstronomySystem system = new AstronomySystem();

        while (flag) {
            System.out.println("\n====Astronomy Observation System====");
            System.out.println("1. Add Observer");
            System.out.println("2. Add Telescope");
            System.out.println("3. Add Celestial Object");
            System.out.println("4. Schedule Session");
            System.out.println("5. View/Sort Sessions Menu");
            System.out.println("6. View Observers");
            System.out.println("7. View Telescopes");
            System.out.println("8. View Celestial Objects");
            System.out.println("9. Logs File Engine");
            System.out.println("10. Performance Analytics Reports");
            System.out.println("11. Exit");
            System.out.print("Select Menu Action Option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                if (choice == 1) {
                    System.out.print("Enter Observer ID: ");
                    int observerId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Observer Name: ");
                    String observerName = sc.nextLine();
                    System.out.print("Enter Experience Level (Beginner, Intermediate, Expert): ");
                    String experienceLevel = sc.nextLine();
                    System.out.print("Enter Location: ");
                    String location = sc.nextLine();

                    Observer observer = new Observer(observerId, observerName, experienceLevel, location);
                    system.addObserver(observer);
                    System.out.println("Observer Added Successfully!");
                }
                else if (choice == 2) {
                    System.out.print("Enter Telescope ID: ");
                    int telescopeId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Telescope Model: ");
                    String model = sc.nextLine();
                    System.out.print("Enter Max Magnification: ");
                    int magnification = sc.nextInt();
                    System.out.print("Enter Aperture Size (mm): ");
                    double aperture = sc.nextDouble();
                    System.out.print("Is motorized (true/false): ");
                    boolean motorized = sc.nextBoolean();
                    sc.nextLine();

                    Telescope telescope = new Telescope(telescopeId, model, magnification, aperture, motorized);
                    system.addTelescope(telescope);
                    System.out.println("Telescope Added Successfully!");
                }
                else if (choice == 3) {
                    System.out.println("Choose Object Type:\n1. Star\n2. Planet\n3. Galaxy");
                    int objChoice = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter LightYears: ");
                    int lightYears = sc.nextInt();
                    System.out.print("Enter Magnitude: ");
                    double magnitude = sc.nextDouble();
                    System.out.print("Enter Right Ascension: ");
                    int ascension = sc.nextInt();
                    System.out.print("Enter Declination: ");
                    int declination = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Constellation: ");
                    String constellation = sc.nextLine();

                    CelestialObject object = null;
                    try {
                        if (objChoice == 1) {
                            System.out.print("Enter Spectral Class: ");
                            String specClass = sc.nextLine();
                            System.out.print("Is Nebula? (true/false): ");
                            boolean nebula = sc.nextBoolean();
                            sc.nextLine();
                            object = new Star(name, lightYears, magnitude, ascension, declination, constellation, nebula, specClass);
                            System.out.println("Star Asset Verification Successful!");
                        } else if (objChoice == 2) {
                            System.out.print("Enter Number of Moons: ");
                            int moons = sc.nextInt();
                            System.out.print("Has Rings? (true/false): ");
                            boolean rings = sc.nextBoolean();
                            sc.nextLine();
                            object = new Planet(name, lightYears, magnitude, ascension, declination, constellation, moons, rings);
                            System.out.println("Planet Asset Verification Successful!");
                        } else if (objChoice == 3) {
                            System.out.print("Enter Galaxy Type: ");
                            String galaxyType = sc.nextLine();
                            System.out.print("Enter Estimated Stars (Billions): ");
                            int stars = sc.nextInt();
                            sc.nextLine();
                            object = new Galaxy(name, lightYears, magnitude, ascension, declination, constellation, galaxyType, stars);
                            System.out.println("Galaxy Asset Verification Successful!");
                        } else {
                            System.out.println("Invalid Entity Option Selection!");
                        }

                        if (object != null) {
                            system.addCelestialObject(object);
                        }
                    } catch (org.astronomy.exception.InvalidDataException e) {
                        System.out.println("\n[VALIDATION ERROR] " + e.getMessage());
                        System.out.println("Returning to Main Menu...");
                    }
                }
                else if (choice == 4) {
                    // SMART CHECK: Block configuration run execution sequence if collections maps remain completely empty
                    if (system.getObservers().isEmpty() || system.getTelescopes().isEmpty() || system.getCelestialObjects().isEmpty()) {
                        throw new InvalidDataException("Scheduling Rejected: Ensure at least 1 Observer, 1 Telescope, and 1 Target object are registered in the system!");
                    }

                    System.out.print("Enter Session Id: ");
                    int id = sc.nextInt();

                    System.out.println("\nAvailable Observers:");
                    for (int i = 0; i < system.getObservers().size(); i++) {
                        System.out.println(i + " - " + system.getObservers().get(i));
                    }
                    System.out.print("Select Observer Index: ");
                    int observerIndex = sc.nextInt();

                    System.out.println("\nAvailable Telescopes:");
                    for (int i = 0; i < system.getTelescopes().size(); i++) {
                        System.out.println(i + " - " + system.getTelescopes().get(i));
                    }
                    System.out.print("Select Telescope Index: ");
                    int telescopeIndex = sc.nextInt();

                    System.out.println("\nCelestial Objects:");
                    for (int i = 0; i < system.getCelestialObjects().size(); i++) {
                        System.out.println(i + " - " + system.getCelestialObjects().get(i));
                    }
                    System.out.print("Select Celestial Object Index: ");
                    int celestialIndex = sc.nextInt();
                    sc.nextLine(); // Flush

                    // Safe boundary checks for structural indices selection parameters input values bounds handling
                    if (observerIndex < 0 || observerIndex >= system.getObservers().size() ||
                            telescopeIndex < 0 || telescopeIndex >= system.getTelescopes().size() ||
                            celestialIndex < 0 || celestialIndex >= system.getCelestialObjects().size()) {
                        throw new InvalidDataException("Array index choice out of range allocation!");
                    }

                    Observer observer = system.getObservers().get(observerIndex);
                    Telescope telescope = system.getTelescopes().get(telescopeIndex);
                    CelestialObject celestialObject = system.getCelestialObjects().get(celestialIndex);

                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    System.out.print("Enter Start Hour (0-23): ");
                    int startHour = sc.nextInt();
                    System.out.print("Enter Duration (In Minutes): ");
                    int duration = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Notes: ");
                    String notes = sc.nextLine();

                    ObservationSession session = system.scheduleSession(observer, telescope, celestialObject, date, startHour, duration, notes, id);

                    System.out.println("\n===== Session Result =====");
                    System.out.println(session);
                }
                else if (choice == 5) {
                    boolean sessionMenu = true;
                    while (sessionMenu) {
                        System.out.println("\n--- View/Sort Sessions Submenu ---");
                        System.out.println("1. View Unsorted Sessions (Chronological Application Input Order)");
                        System.out.println("2. Sort and View by Date Chronology");
                        System.out.println("3. Sort and View by Observer Name Alphabetically");
                        System.out.println("4. Sort and View by Verification Status Result");
                        System.out.println("5. Back to Master Interface");
                        System.out.print("Enter Sub-Option Choice Code: ");
                        int sortChoice = sc.nextInt();
                        sc.nextLine();

                        if (sortChoice >= 1 && sortChoice <= 4) {
                            if (system.getSessions().isEmpty()) {
                                System.out.println("No recorded session tracking profiles found.");
                                continue;
                            }
                            if (sortChoice == 2) AnalyticsEngine.sortByDate(system.getSessions());
                            if (sortChoice == 3) AnalyticsEngine.sortByObserverName(system.getSessions());
                            if (sortChoice == 4) AnalyticsEngine.sortByStatus(system.getSessions());

                            System.out.println("\n==== Rendered Sessions List ====");
                            for (ObservationSession s : system.getSessions()) {
                                System.out.println(s);
                                System.out.println("----------------------------------");
                            }
                        } else if (sortChoice == 5) {
                            sessionMenu = false;
                        } else {
                            System.out.println("Invalid Selection code options mapping choice.");
                        }
                    }
                }
                else if (choice == 6) {
                    System.out.println("\n==== Registered System Observers ====");
                    if (system.getObservers().isEmpty()) System.out.println("Empty directory data arrays.");
                    for (int i = 0; i < system.getObservers().size(); i++) {
                        System.out.println((i + 1) + ". " + system.getObservers().get(i));
                    }
                }
                else if (choice == 7) {
                    System.out.println("\n==== Registered System Telescopes ====");
                    if (system.getTelescopes().isEmpty()) System.out.println("Empty directory data arrays.");
                    for (int i = 0; i < system.getTelescopes().size(); i++) {
                        System.out.println((i + 1) + ". " + system.getTelescopes().get(i));
                    }
                }
                else if (choice == 8) {
                    System.out.println("\n==== Registered Celestial Target Profiles ====");
                    if (system.getCelestialObjects().isEmpty()) System.out.println("Empty directory data arrays.");
                    for (int i = 0; i < system.getCelestialObjects().size(); i++) {
                        System.out.println((i + 1) + ". " + system.getCelestialObjects().get(i));
                    }
                }
                else if (choice == 9) {
                    boolean logMenu = true;
                    while (logMenu) {
                        System.out.println("\n==== Logs Menu ====");
                        System.out.println("1. View All Logs");
                        System.out.println("2. Search by Observer");
                        System.out.println("3. Search by Object");
                        System.out.println("4. Filter by Status");
                        System.out.println("5. Clear Logs");
                        System.out.println("6. Back To Main Menu");
                        System.out.print("Enter Choice: ");

                        int logChoice = sc.nextInt();
                        sc.nextLine();

                        if (logChoice == 1) {
                            ArrayList<String> logs = ObservationLog.loadAllSessions();
                            if (logs.isEmpty()) System.out.println("No written records.");
                            for (String log : logs) System.out.println(log);
                        } else if (logChoice == 2) {
                            System.out.print("Enter Observer Name: ");
                            ArrayList<String> results = ObservationLog.searchByObserver(sc.nextLine());
                            for (String log : results) System.out.println(log);
                        } else if (logChoice == 3) {
                            System.out.print("Enter Object Name: ");
                            ArrayList<String> results = ObservationLog.searchByObject(sc.nextLine());
                            for (String log : results) System.out.println(log);
                        } else if (logChoice == 4) {
                            System.out.print("Enter Status (SUCCESS/FAILED): ");
                            ArrayList<String> results = ObservationLog.filterByStatus(sc.nextLine());
                            for (String log : results) System.out.println(log);
                        } else if (logChoice == 5) {
                            ObservationLog.clearLogs();
                        } else if (logChoice == 6) {
                            logMenu = false;
                        }
                    }
                }
                else if (choice == 10) {
                    // Running dynamic evaluation metric arrays directly using analytical components
                    AnalyticsEngine.generateReport(system.getSessions());
                }
                else if (choice == 11) {
                    flag = false;
                    System.out.println("==== System Exited Successfully! ====");
                }
                else {
                    System.out.println("Option input choice command mapping selection exception code unrecognized.");
                }
            } catch (InvalidDataException e) {
                System.out.println("\n[VALIDATION ERROR]: " + e.getMessage());
                System.out.println("Action execution sequence halted safely. Please enter values correctly.");
            } catch (Exception e) {
                System.out.println("\n[SYSTEM UNEXPECTED ERROR]: Process mapping issue occurred. Clearing buffers.");
                sc.nextLine();
            }
        }
        sc.close();
    }
}

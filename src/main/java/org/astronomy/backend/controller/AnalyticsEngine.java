package org.astronomy.backend.controller;

import org.astronomy.backend.model.ObservationSession;
import java.util.ArrayList;

public class AnalyticsEngine {

    public static void generateReport(ArrayList<ObservationSession> sessions) {
        System.out.println("\n=========================================");
        System.out.println("     ASTRONOMY SYSTEM PERFORMANCE REPORT  ");
        System.out.println("=========================================");

        if (sessions.isEmpty()) {
            System.out.println("No recorded sessions found to analyze.");
            return;
        }

        int total = sessions.size();
        int successCount = 0;
        int failedCount = 0;

        // to count success and failures
        for (int i = 0; i < sessions.size(); i++) {
            ObservationSession s = sessions.get(i);
            if ("SUCCESS".equals(s.getStatus())) {
                successCount++;
            } else if ("FAILED".equals(s.getStatus())) {
                failedCount++;
            }
        }

        double successRate = ((double) successCount / total) * 100;

        System.out.println("• Total Processed Sessions : " + total);
        System.out.printf("• Operational Success Rate : %.2f%%\n", successRate);
        System.out.println("• Successful Sessions      : " + successCount);
        System.out.println("• Failed Sessions          : " + failedCount);

        // Find the most observed object using basic nested loops
        String mostObservedObject = "N/A";
        int maxObjectCount = 0;

        for (int i = 0; i < sessions.size(); i++) {
            String currentObjName = sessions.get(i).getTarget().getName();
            int currentCount = 0;

            // Count how many times this specific name appears
            for (int j = 0; j < sessions.size(); j++) {
                if (sessions.get(j).getTarget().getName().equalsIgnoreCase(currentObjName)) {
                    currentCount++;
                }
            }
            // Track the maximum
            if (currentCount > maxObjectCount) {
                maxObjectCount = currentCount;
                mostObservedObject = currentObjName;
            }
        }

        // Find the most active observer using basic nested loops
        String mostActiveObserver = "N/A";
        int maxObserverCount = 0;

        for (int i = 0; i < sessions.size(); i++) {
            String currentObsName = sessions.get(i).getObserver().getObserverName();
            int currentCount = 0;

            for (int j = 0; j < sessions.size(); j++) {
                if (sessions.get(j).getObserver().getObserverName().equalsIgnoreCase(currentObsName)) {
                    currentCount++;
                }
            }

            if (currentCount > maxObserverCount) {
                maxObserverCount = currentCount;
                mostActiveObserver = currentObsName;
            }
        }

        System.out.println("\n[Top Contributor Matrix]");
        System.out.println(" -> Most Active Observer: " + mostActiveObserver + " (" + maxObserverCount + " session(s))");
        System.out.println(" -> Most Targeted Object: " + mostObservedObject + " (" + maxObjectCount + " session(s))");
        System.out.println("=========================================");
    }

    public static void sortByDate(ArrayList<ObservationSession> sessions) {
        int n = sessions.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sessions.get(j).getDate().compareTo(sessions.get(j + 1).getDate()) > 0) {
                    ObservationSession temp = sessions.get(j);
                    sessions.set(j, sessions.get(j + 1));
                    sessions.set(j + 1, temp);
                }
            }
        }
    }

    public static void sortByObserverName(ArrayList<ObservationSession> sessions) {
        int n = sessions.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String name1 = sessions.get(j).getObserver().getObserverName();
                String name2 = sessions.get(j + 1).getObserver().getObserverName();

                if (name1.compareToIgnoreCase(name2) > 0) {
                    ObservationSession temp = sessions.get(j);
                    sessions.set(j, sessions.get(j + 1));
                    sessions.set(j + 1, temp);
                }
            }
        }
    }

    public static void sortByStatus(ArrayList<ObservationSession> sessions) {
        int n = sessions.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sessions.get(j).getStatus().compareTo(sessions.get(j + 1).getStatus()) > 0) {
                    ObservationSession temp = sessions.get(j);
                    sessions.set(j, sessions.get(j + 1));
                    sessions.set(j + 1, temp);
                }
            }
        }
    }
}

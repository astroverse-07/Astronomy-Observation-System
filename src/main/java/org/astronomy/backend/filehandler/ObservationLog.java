package org.astronomy.backend.filehandler;

import org.astronomy.backend.model.ObservationSession;

import java.io.*;
import java.util.ArrayList;
import java.io.File;

public class ObservationLog {

    private static final String FILE_NAME = "observation_logs.txt";

    public static void saveSession(ObservationSession session) {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(session.getLog());
            pw.close();
        }
        catch (IOException e) {
            System.out.println("Error saving session: " + e.getMessage());
        }
    }

    public static ArrayList<String> loadAllSessions() {
        ArrayList<String> logs = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return logs;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = br.readLine()) != null) {
                logs.add(line);
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("Error loading sessions: " + e.getMessage());
        }
        return logs;
    }

    public static ArrayList<String> searchByObserver(String name) {
        ArrayList<String> results = new ArrayList<>();
        for (String log : loadAllSessions()) {
            if (log.toLowerCase().contains(name.toLowerCase())) {
                results.add(log);
            }
        }
        return results;
    }

    public static ArrayList<String> searchByObject(String objectName) {
        ArrayList<String> results = new ArrayList<>();
        for (String log : loadAllSessions()) {
            if (log.toLowerCase().contains(objectName.toLowerCase())) {
                results.add(log);
            }
        }
        return results;
    }

    public static ArrayList<String> filterByStatus(String status) {
        ArrayList<String> results = new ArrayList<>();
        for (String log : loadAllSessions()) {
            if (log.contains(status.toUpperCase())) {
                results.add(log);
            }
        }
        return results;
    }

    public static void clearLogs() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, false);
            fw.close();
            System.out.println("Logs cleared successfully!");
        }
        catch (IOException e) {
            System.out.println("Error clearing logs: " + e.getMessage());
        }
    }
}
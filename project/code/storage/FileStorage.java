package storage;

import java.io.*;
import java.util.*;

public class FileStorage {

    private String directory;

    public FileStorage(String directory) {
        this.directory = directory;

        File folder = new File(directory);

        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void saveRecord(String fileName, String record) {
        File file = new File(directory, fileName);

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, true))) {

            writer.write(record);
            writer.newLine();

            System.out.println("Record saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving record: " + e.getMessage());
        }
    }

    public void overwriteRecords(String fileName, List<String> records) {
        File file = new File(directory, fileName);

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String record : records) {
                writer.println(record);
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + fileName);
        }
    }

    public List<String> readRecords(String fileName) {
        List<String> records = new ArrayList<>();
        File file = new File(directory, fileName);

        if (!file.exists()) {
            return records;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                records.add(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading records: " + e.getMessage());
        }

        return records;
    }

    public void displayRecords(String fileName) {
        List<String> records = readRecords(fileName);

        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (String record : records) {
            System.out.println(record);
        }
    }
}
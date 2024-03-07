package org.example.business.teryt;

import org.apache.commons.csv.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class CSVMerger {
    public static void main(String[] args) throws IOException {
lastDelete();



    }

    private static void podmiana() throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/TERC.csv"), StandardCharsets.UTF_8));
        List<CSVRecord> recordsTERC = CSVFormat.DEFAULT.withDelimiter(';').parse(in).getRecords();

        in = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/SIMC.csv"), StandardCharsets.UTF_8));
        List<CSVRecord> recordsSIMC = CSVFormat.DEFAULT.withDelimiter(';').parse(in).getRecords();
        List<List<String>> newRecords = new ArrayList<>();

        for (CSVRecord recordTERC : recordsTERC) {
            if (recordTERC.toString().contains("województwo")) {
                for (CSVRecord recordSIMC : recordsSIMC) {
                    List<String> newRecord = new ArrayList<>(recordSIMC.toList());
                    if (recordSIMC.get(0).split(";")[0].equals(recordTERC.get(0).split(";")[0])) {
                        newRecord.set(0, recordTERC.get(4));
                        newRecords.add(newRecord);
                    }
                }
            }

            try (Writer writer = new OutputStreamWriter(new FileOutputStream("SIMC.csv"), StandardCharsets.UTF_8)) {
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';'));
                for (List<String> record : newRecords) {
                    csvPrinter.printRecord(record);
                }
                csvPrinter.flush();
            }
        }
    }
    private static void onlyCities() {
        String inputFile = "src/main/resources/SIMC.csv";
        String outputFile = "SIMC.txt";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {

            String result = reader.lines()
                    .filter(line -> line.contains("miasto"))
                    .collect(Collectors.joining("\n"));
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void wmRodz() throws IOException {
        // Utwórz mapę dla kodów RM
        Map<String, String> rmMap = new HashMap<>();
        rmMap.put("00", "część");
        rmMap.put("01", "wieś");
        rmMap.put("02", "kolonia");
        rmMap.put("03", "przysiółek");
        rmMap.put("04", "osada");
        rmMap.put("05", "osada leśna");
        rmMap.put("06", "osiedle");
        rmMap.put("07", "schronisko turystyczne");
        rmMap.put("95", "dzielnica");
        rmMap.put("96", "miasto");
        rmMap.put("98", "delegatura");
        rmMap.put("99", "część miasta");

        Reader in = new InputStreamReader(new FileInputStream("src/main/resources/SIMC.csv"), StandardCharsets.UTF_8);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';').parse(in);
        List<List<String>> newRecords = new ArrayList<>();
        for (CSVRecord record : records) {

            List<String> newRecord = new ArrayList<>(record.toList());
            String rm = newRecord.get(4);
            if (rmMap.containsKey(rm)) {
                newRecord.set(4, rmMap.get(rm));
            }
            newRecords.add(newRecord);
        }

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("SIMC.csv"), StandardCharsets.UTF_8))) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';'));
            for (List<String> record : newRecords) {
                csvPrinter.printRecord(record);
            }
            csvPrinter.flush();
        }
    }


    private static void lastDelete() throws IOException {
        BufferedReader readerSIMC = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/ULIC.csv"), StandardCharsets.UTF_8));

        List<List<String>> newRecords = new ArrayList<>();

        String lineSIMC;
        while ((lineSIMC = readerSIMC.readLine()) != null) {
            List<String> newRecord = new ArrayList<>(Arrays.asList(lineSIMC.split(";")));

            newRecord.remove(newRecord.size() - 2);
            newRecords.add(newRecord);
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream("ULIC.csv"), StandardCharsets.UTF_8)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter(';'));
            for (List<String> record : newRecords) {
                csvPrinter.printRecord(record);
            }
            csvPrinter.flush();
        }
    }
}
package org.example.business.teryt;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.api.dto.CityDTO;
import org.example.api.dto.StreetsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoadDataService {

    private CityDTO cityDTO;

    @Transactional
    public Map<String, Map<String, Map<String, Map<String, List<String>>>>> loadCitiesFromCSV() {
        Map<String, Map<String, Map<String, Map<String, List<String>>>>> cities = new HashMap<>();
        try {
            InputStream inStream = new FileInputStream("src/main/resources/SIMC.csv");
            Reader inSIMC = new InputStreamReader(inStream, StandardCharsets.UTF_8);
            CSVFormat format = CSVFormat.DEFAULT
                    .withDelimiter(';')
                    .withHeader("WOJ", "POW", "GMI", "RODZ", "SYM")
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim();
            Iterable<CSVRecord> records = format.parse(inSIMC);
            for (CSVRecord record : records) {
                String woj = record.get("WOJ");
                String pow = record.get("POW");
                String gmi = record.get("GMI");
                String rodz = record.get("RODZ");
                String sym = record.get("SYM");
                cities.computeIfAbsent(woj, k -> new HashMap<>())
                        .computeIfAbsent(pow, k -> new HashMap<>())
                        .computeIfAbsent(gmi, k -> new HashMap<>())
                        .computeIfAbsent(rodz, k -> new ArrayList<>())
                        .add(sym);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }

    @Transactional
    public Map<String, Set<String>> loadWojPowFromCSV() {
        Map<String, Set<String>> wojPow = new HashMap<>();
        try {
            InputStream inStream = new FileInputStream("src/main/resources/SIMC.csv");
            Reader inSIMC = new InputStreamReader(inStream, StandardCharsets.UTF_8);
            CSVFormat format = CSVFormat.DEFAULT
                    .withDelimiter(';')
                    .withHeader("WOJ", "POW")
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim();
            Iterable<CSVRecord> records = format.parse(inSIMC);
            for (CSVRecord record : records) {
                String woj = record.get("WOJ");
                String pow = record.get("POW");
                wojPow.computeIfAbsent(woj, k -> new HashSet<>()).add(pow);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wojPow;
    }

    @Transactional
    public Map<String, List<String>> loadPowGmiFromCSV() {
        Map<String, List<String>> powGmi = new HashMap<>();
        try {
            InputStream inStream = new FileInputStream("src/main/resources/SIMC.csv");
            Reader inSIMC = new InputStreamReader(inStream, StandardCharsets.UTF_8);
            CSVFormat format = CSVFormat.DEFAULT
                    .withDelimiter(';')
                    .withHeader("POW", "GMI")
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim();
            Iterable<CSVRecord> records = format.parse(inSIMC);
            for (CSVRecord record : records) {
                String pow = record.get("POW");
                String gmi = record.get("GMI");
                powGmi.computeIfAbsent(pow, k -> new ArrayList<>()).add(gmi);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return powGmi;
    }

    @Transactional
    public Map<String, List<String>> loadGmiSymFromCSV() {
        Map<String, List<String>> gmiSym = new HashMap<>();
        try {
            InputStream inStream = new FileInputStream("src/main/resources/SIMC.csv");
            Reader inSIMC = new InputStreamReader(inStream, StandardCharsets.UTF_8);
            CSVFormat format = CSVFormat.DEFAULT
                    .withDelimiter(';')
                    .withHeader("GMI", "SYM")
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim();
            Iterable<CSVRecord> records = format.parse(inSIMC);
            for (CSVRecord record : records) {
                String gmi = record.get("GMI");
                String sym = record.get("SYM");
                gmiSym.computeIfAbsent(gmi, k -> new ArrayList<>()).add(sym);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gmiSym;
    }

    @Transactional
    public List<StreetsDTO> loadStreetsFromCSV() {
        List<StreetsDTO> streets = new ArrayList<>();
        try {
            InputStream inStream = new FileInputStream("src/main/resources/ULIC.csv");
            Reader inULIC = new InputStreamReader(inStream, StandardCharsets.UTF_8);
            CSVFormat format = CSVFormat.DEFAULT
                    .withDelimiter(';')
                    .withHeader("SYM", "SYM_UL", "CECHA", "NAZWA")
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim();
            Iterable<CSVRecord> records = format.parse(inULIC);
            for (CSVRecord record : records) {
                StreetsDTO street = new StreetsDTO();
                street.setSym(record.get("SYM"));
                street.setSymUl(record.get("SYM_UL"));
                street.setCecha(record.get("CECHA"));
                String[] nazwa = record.get("NAZWA").split(";", 2);
                street.setNazwa_1(nazwa[0]);
                street.setNazwa_2(nazwa.length > 1 ? nazwa[1] : "");
                streets.add(street);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return streets;
    }

    @Transactional
    public List<StreetsDTO> getStreetsForGmina(String gminaSymbol) {
        List<StreetsDTO> allStreets = loadStreetsFromCSV();
        List<StreetsDTO> gminaStreets = new ArrayList<>();

        for (StreetsDTO street : allStreets) {
            if (street.getSym().equals(gminaSymbol)) {
                gminaStreets.add(street);
            }
        }

        return gminaStreets;
    }
}

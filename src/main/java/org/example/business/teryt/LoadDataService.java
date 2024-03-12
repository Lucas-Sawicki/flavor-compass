package org.example.business.teryt;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.api.dto.StreetsDTO;
import org.example.domain.exception.CustomException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LoadDataService {

    @Transactional
    public Map<String, Set<String>> loadWojPowFromCSV() {
        Map<String, Set<String>> wojPow = new HashMap<>();
        try (InputStream inStream = new FileInputStream("src/main/resources/SIMC.csv");
             Reader inSIMC = new InputStreamReader(inStream, StandardCharsets.UTF_8)) {
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
        } catch (FileNotFoundException ex) {
            throw new CustomException("File not found.", ex.getMessage());
        } catch (IOException ex) {
            throw new CustomException("Error reading file.", ex.getMessage());
        }
        return wojPow;
    }

    @Transactional
    public Map<String, List<String>> loadPowGmiFromCSV() {
        Map<String, List<String>> powGmi = new HashMap<>();
        try (InputStream inStream = new FileInputStream("src/main/resources/SIMC.csv");
             Reader inSIMC = new InputStreamReader(inStream, StandardCharsets.UTF_8)) {
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
        } catch (FileNotFoundException ex) {
            throw new CustomException("File not found.", ex.getMessage());
        } catch (IOException ex) {
            throw new CustomException("Error reading file.", ex.getMessage());
        }
        return powGmi;
    }

    @Transactional
    public Map<String, List<String>> loadGmiSymFromCSV() {
        Map<String, List<String>> gmiSym = new HashMap<>();
        try (InputStream inStream = new FileInputStream("src/main/resources/SIMC.csv");
             Reader inSIMC = new InputStreamReader(inStream, StandardCharsets.UTF_8)) {
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
        } catch (FileNotFoundException ex) {
            throw new CustomException("File not found.", ex.getMessage());
        } catch (IOException ex) {
            throw new CustomException("Error reading file.", ex.getMessage());
        }
        return gmiSym;
    }

    @Transactional
    public List<StreetsDTO> loadStreetsFromCSV() {
        List<StreetsDTO> streets = new ArrayList<>();
        try (InputStream inStream = new FileInputStream("src/main/resources/ULIC.csv");
             Reader inULIC = new InputStreamReader(inStream, StandardCharsets.UTF_8)) {
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
                street.setNazwa_1(nazwa.length > 0 ? nazwa[0] : "");
                street.setNazwa_2(nazwa.length > 1 ? nazwa[1] : "");
                streets.add(street);
            }
        } catch (FileNotFoundException ex) {
            throw new CustomException("File not found.", ex.getMessage());
        } catch (IOException ex) {
            throw new CustomException("Error reading file.", ex.getMessage());
        }
        return streets;
    }

    @Transactional
    public List<StreetsDTO> getStreetsForGmina(String gminaSymbol) {
        try {
            List<StreetsDTO> allStreets = loadStreetsFromCSV();
            List<StreetsDTO> gminaStreets = new ArrayList<>();

            for (StreetsDTO street : allStreets) {
                if (street.getSym().equals(gminaSymbol)) {
                    gminaStreets.add(street);
                }
            }
            if (gminaStreets.isEmpty()) {
                throw new CustomException("No streets found for this symbol.");
            }
            return gminaStreets;
        } catch (IllegalArgumentException ex) {
            throw new CustomException("Invalid symbol.", ex.getMessage());
        }
    }
}
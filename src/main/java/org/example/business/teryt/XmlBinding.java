package org.example.business.teryt;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@RequiredArgsConstructor
public class XmlBinding {

    public static void binding(String selectedWoj, String selectedMiejscowosc, String selectedUlica) {
        try {
            Map<String, List<Object>> dataMap = new HashMap<>();

            String[] fileNames = {"src/main/resources/TERC/SIMC_Urzedowy_2024-03-05.xml",
                    "src/main/resources/TERC/TERC_Urzedowy_2024-03-05.xml",
                    "src/main/resources/TERC/ULIC_Urzedowy_2024-03-05.xml",
                    "src/main/resources/TERC/WMRODZ_2024-03-05.xml"};
            Class[] classes = {SIMC.class, TERC.class, ULIC.class, WMRODZ.class};

            for (int i = 0; i < fileNames.length; i++) {
                JAXBContext jaxbContext = JAXBContext.newInstance(classes[i]);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                File file = new File(fileNames[i]);
                Object row = jaxbUnmarshaller.unmarshal(file);

                // Dodawanie danych do mapy
                String wojKey = ((TERC) row).getWoj();
                List<Object> rows = dataMap.getOrDefault(wojKey, new ArrayList<>());
                rows.add(row);
                dataMap.put(wojKey, rows);
            }

            // Wybór danych dla wybranego województwa, miejscowości i ulicy
            List<Object> selectedData = dataMap.get(selectedWoj);
            for (Object obj : selectedData) {
                Location data = (Location) obj;
                if (data.getMz().equals(selectedMiejscowosc) && data.getNazwa().equals(selectedUlica)) {
                    System.out.println(data.getNazwa());
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}

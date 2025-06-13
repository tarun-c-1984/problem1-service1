package org.example;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HoldingEventCsvReader {
    public List<HoldingEvent> readFromCsv(String filePath) throws IOException {
        List<HoldingEvent> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { // skip header
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
              //if (values.length < 10) continue; // skip invalid rows
                HoldingEvent event = new HoldingEvent();
                event.setHoldingId(Integer.parseInt(values[0].trim()));
                event.setAssetId(values[1].trim());
                event.setAccountId(values[2].trim());
                event.setPlaceOfSafekeeping(values[3].trim());
                event.setHoldingType(values[4].trim());
                event.setSettQnty(Integer.parseInt(values[5].trim()));
                event.setPendQnty(Integer.parseInt(values[6].trim()));
                event.setPendRcvQntty(Integer.parseInt(values[7].trim()));
                event.setPendDlvrQntty(Integer.parseInt(values[8].trim()));
                event.setDate(values[9].trim());
                events.add(event);
            }
        }
        return events;
    }
}

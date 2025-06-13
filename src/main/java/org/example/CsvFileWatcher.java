package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Component
public class CsvFileWatcher implements Runnable {
    @Value("${csv.source.path}")
    private String directoryPath;

    @Autowired
    private HoldingEventCsvReader csvReader;

    @Autowired
    private HoldingEventProducer holdingEventProducer;

    public CsvFileWatcher() {
        // Default constructor for Spring
    }

    @Override
    public void run() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(directoryPath);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path fileName = (Path) event.context();
                        if (fileName.toString().toLowerCase().endsWith(".csv")) {
                            String filePath = path.resolve(fileName).toString();
                            System.out.println("New CSV file detected: " + filePath);
                            Thread.sleep(2000);
                            try {
                                List<HoldingEvent> events = csvReader.readFromCsv(filePath);
                                System.out.println("Read " + events.size() + " events from " + filePath);
                                for (HoldingEvent eventObj : events) {
                                    String eventJson = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(eventObj);
                                    String partitionKey = eventObj.getAssetId();
                                    holdingEventProducer.sendHoldingEvent(partitionKey, eventJson);
                                }
                                System.out.println("Published all events to Kafka.");
                                // Move file to processed directory
                                Path processedDir = path.resolve("processed");
                                if (!Files.exists(processedDir)) {
                                    Files.createDirectory(processedDir);
                                }
                                Path targetPath = processedDir.resolve(fileName);
                                Files.move(Paths.get(filePath), targetPath, StandardCopyOption.REPLACE_EXISTING);
                                System.out.println("Moved processed file to: " + targetPath);
                            } catch (Exception e) {
                                System.err.println("Failed to read CSV or publish to Kafka: " + e.getMessage());
                                // Move file to failed directory
                                e.printStackTrace();
                                try {
                                    Path failedDir = path.resolve("failed");
                                    if (!Files.exists(failedDir)) {
                                        Files.createDirectory(failedDir);
                                    }
                                    Path failedPath = failedDir.resolve(fileName);
                                    Files.move(Paths.get(filePath), failedPath, StandardCopyOption.REPLACE_EXISTING);
                                    System.out.println("Moved failed file to: " + failedPath);
                                } catch (IOException ex) {
                                    System.err.println("Failed to move file to failed directory: " + ex.getMessage());
                                }
                            }
                        }
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/csv")
public class FailedCsvController {

    @Value("${csv.source.path}")
    private String csvSourcePath;

    @PostMapping("/process-failed")
    public String processFailedCsv() {
        Path failedDir = Paths.get(csvSourcePath, "failed");
        if (!Files.exists(failedDir)) {
            return "No failed directory found.";
        }
        int i=0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(failedDir, "*.csv")) {
            for (Path file : stream) {
                i++;
                // Move file back to incoming directory for reprocessing
                Path target = Paths.get(csvSourcePath, file.getFileName().toString());
                Files.move(file, target, StandardCopyOption.REPLACE_EXISTING);
            }
            if(i==0)
                return "No failed CSV files found for reprocessing.";
        } catch (IOException e) {
            return "Error processing failed files: " + e.getMessage();
        }
        return "All failed CSV files moved for reprocessing.";
    }
}


package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

@Component
public class CsvFileWatcherStarter {
    @Autowired
    private CsvFileWatcher csvFileWatcher;

    @EventListener(ApplicationReadyEvent.class)
    public void startWatcher() {
        Thread watcherThread = new Thread(csvFileWatcher);
        watcherThread.setDaemon(false); // keep app alive
        watcherThread.start();
        System.out.println("CsvFileWatcher started in background thread.");
    }
}


package com.backend.habitsduel;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.io.File;

@Component
public class StorageInit {

    @PostConstruct
    public void init() {
        new File("uploads").mkdirs();
    }
}
package com.batch.lotto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {
    @GetMapping("/")
    public BatchConfiguration runBatch() {
        return new BatchConfiguration();
    }
}
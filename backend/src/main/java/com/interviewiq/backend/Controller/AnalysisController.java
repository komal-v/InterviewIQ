package com.interviewiq.backend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.interviewiq.backend.Service.AnalysisService;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyze(
            @RequestParam("jobDescription") String jobDescription,
            @RequestParam("resume") MultipartFile resume
    ) {

        Map<String, Object> result = analysisService.analyze(jobDescription, resume);      

        return ResponseEntity.ok(result);
        
    }
}
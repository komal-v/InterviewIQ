package com.interviewiq.backend.Service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AnalysisService {

    private final ResumeParserService resumeParserService;
    private final SkillAnalysisService skillAnalysisService;

    public AnalysisService(ResumeParserService resumeParserService,
                           SkillAnalysisService skillAnalysisService) {
        this.resumeParserService = resumeParserService;
        this.skillAnalysisService = skillAnalysisService;
    }
    
    public Map<String, Object> analyze(String jobDescription, MultipartFile resume) {

        String resumeText = resumeParserService.extractText(resume);

        return skillAnalysisService.analyze(resumeText, jobDescription);   

    }
}
package com.interviewiq.backend.Service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkillAnalysisService {

    private static final List<String> SKILLS = List.of(
            "Java",
            "Spring Boot",
            "React",
            "TypeScript",
            "JavaScript",
            "AWS",
            "Azure",
            "Docker",
            "Kubernetes",
            "Kafka",
            "Microservices",
            "SQL",
            "Terraform",
            "REST APIs",
            "Git",
            "Python"
    );

    public Map<String, Object> analyze(
            String resumeText,
            String jobDescription) {

        String resume = resumeText.toLowerCase();
        String job = jobDescription.toLowerCase();

        List<String> requiredSkills = new ArrayList<>();
        List<String> matchedSkills = new ArrayList<>();
        List<String> skillGaps = new ArrayList<>();

        for (String skill : SKILLS) {

            String normalizedSkill = skill.toLowerCase();

            // Is this skill required by the job?
            if (job.contains(normalizedSkill)) {

                requiredSkills.add(skill);

                // Does the resume contain it?
                if (resume.contains(normalizedSkill)) {
                    matchedSkills.add(skill);
                } else {
                    skillGaps.add(skill);
                }
            }
        }

        int score = 0;

        if (!requiredSkills.isEmpty()) {
            score = (matchedSkills.size() * 100)
                    / requiredSkills.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("score", score);
        result.put("matchedSkills", matchedSkills);
        result.put("skillGaps", skillGaps);
        result.put("requiredSkills", requiredSkills);

        return result;
    }
}
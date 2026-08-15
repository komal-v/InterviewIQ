package com.interviewiq.backend.Service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
@Service
public class SkillAnalysisService {

    private static final Map<String, List<String>> SKILL_ALIASES = createSkillAliases();

    private static Map<String, List<String>> createSkillAliases() {

        Map<String, List<String>> skills = new LinkedHashMap<>();

        skills.put("Java", List.of(
                "java"));

        skills.put("Spring Boot", List.of(
                "spring boot",
                "springboot"));

        skills.put("React", List.of(
                "react",
                "reactjs",
                "react.js"));

        skills.put("TypeScript", List.of(
                "typescript"));

        skills.put("JavaScript", List.of(
                "javascript",
                "js"));

        skills.put("AWS", List.of(
                "aws",
                "amazon web services"));

        skills.put("Docker", List.of(
                "docker"));

        skills.put("Kubernetes", List.of(
                "kubernetes",
                "k8s"));

        skills.put("Kafka", List.of(
                "kafka",
                "apache kafka"));

        skills.put("Microservices", List.of(
                "microservices",
                "microservice architecture"));

        skills.put("SQL", List.of(
                "sql"));

        skills.put("Terraform", List.of(
                "terraform"));

        skills.put("REST APIs", List.of(
                "rest api",
                "rest apis",
                "restful api",
                "restful apis"));

        skills.put("Git", List.of(
                "git",
                "github"));

        skills.put("Python", List.of(
                "python"));

        skills.put("PostgreSQL", List.of(
                "postgresql",
                "postgres"));

        return skills;
    }

    private boolean containsAny(
        String text,
        List<String> aliases) {

    for (String alias : aliases) {

        String pattern =
                "\\b"
                + Pattern.quote(alias)
                + "\\b";

        if (Pattern.compile(pattern)
                .matcher(text)
                .find()) {

            return true;
        }
    }

    return false;
}

    public Map<String, Object> analyze(
            String resumeText,
            String jobDescription) {

        String resume = resumeText.toLowerCase();
        String job = jobDescription.toLowerCase();

        List<String> requiredSkills = new ArrayList<>();
        List<String> matchedSkills = new ArrayList<>();
        List<String> skillGaps = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : SKILL_ALIASES.entrySet()) {

            String skill = entry.getKey();
            List<String> aliases = entry.getValue();

            boolean required = containsAny(job, aliases);

            if (!required) {
                continue;
            }

            requiredSkills.add(skill);

            if (containsAny(resume, aliases)) {
                matchedSkills.add(skill);
            } else {
                skillGaps.add(skill);
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
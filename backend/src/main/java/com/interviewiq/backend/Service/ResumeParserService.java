package com.interviewiq.backend.Service;

import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeParserService {

    public String extractText(MultipartFile resume) {

        try {
            byte[] bytes = resume.getBytes();

            try (PDDocument document = Loader.loadPDF(bytes)) {

                PDFTextStripper stripper = new PDFTextStripper();

                return stripper.getText(document);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read resume PDF", e
            );
        }
    }
}
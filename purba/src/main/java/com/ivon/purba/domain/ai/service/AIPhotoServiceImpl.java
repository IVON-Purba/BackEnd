package com.ivon.purba.domain.ai.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivon.purba.domain.ai.dto.PhotoAnalysis;
import com.ivon.purba.domain.ai.service.interfaces.AIPhotoService;
import com.ivon.purba.exception.exceptions.AIAnalysisException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service 
public class AIPhotoServiceImpl implements AIPhotoService {

    @Override
    public PhotoAnalysis analyzePhoto(String photoPath) {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "python",
                "C:\\Users\\dydal\\PycharmProjects\\pythonProject\\openai_test.py",
                photoPath
        );

        try {
            Process process = processBuilder.start();
            StringBuilder jsonResultBuilder = new StringBuilder();
            StringBuilder errorResultBuilder = new StringBuilder();

            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonResultBuilder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        errorResultBuilder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new AIAnalysisException("AI 분석 실패: Python 스크립트가 비정상 종료되었습니다. 오류 메시지:" + errorResultBuilder.toString());
            }

            String jsonResult = jsonResultBuilder.toString();
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(jsonResult, PhotoAnalysis.class);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AIAnalysisException("AI 분석 실패:" + e.getMessage());
        }
    }
}



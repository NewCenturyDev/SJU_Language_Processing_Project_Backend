package com.sju.sju_language_processing.domains.sentences.service;

import com.sju.sju_language_processing.commons.message.MessageConfig;
import com.sju.sju_language_processing.domains.musics.service.MusicCrudInterface;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import com.sju.sju_language_processing.domains.sentences.entity.SentenceInput;
import com.sju.sju_language_processing.domains.sentences.repository.SentenceInputRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SentenceLogicServ {
    protected SentenceInputRepo inputRepo;
    protected MusicCrudInterface musicCrudInterface;
    protected static MessageSource msgSrc = MessageConfig.getSentenceMsgSrc();

    protected SentenceInput predictInputEmotion(SentenceInput input) throws Exception {
        if (!input.getText().matches("[ a-zA-Z.,?!'\"]+")) {
            throw new Exception(msgSrc.getMessage("error.sentence.notEnglish", null, Locale.ENGLISH));
        }

        URL resource = getClass().getClassLoader().getResource("python/english_prediction.py");
        String pythonScriptPath = Paths.get(Objects.requireNonNull(resource).toURI()).toString();
        ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath, input.getText());
        Process p = pb.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String resultText = in.readLine();
        EmotionCategory predictionResult = EmotionCategory.valueOf(resultText);
        input.setCategory(predictionResult);
        input.setMusic(musicCrudInterface.fetchRandomMusicByEmotion(predictionResult));
        return input;
    }
}

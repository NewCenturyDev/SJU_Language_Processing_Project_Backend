package com.sju.sju_language_processing.domains.trains.service;

import com.sju.sju_language_processing.commons.message.MessageConfig;
import com.sju.sju_language_processing.domains.musics.service.MusicCrudInterface;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import com.sju.sju_language_processing.domains.trains.repository.SentenceTrainRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;

@Service
@AllArgsConstructor
public class SentenceTrainLogicServ {
    protected SentenceTrainRepo inputRepo;
    protected MusicCrudInterface musicCrudInterface;
    protected static MessageSource msgSrc = MessageConfig.getSentenceMsgSrc();

    protected void checkLanguageIsSupported(String languageStr) throws Exception {
        if (!languageStr.equals("en") && !languageStr.equals("ko")) {
            throw new Exception(msgSrc.getMessage("error.sentence.language", null, Locale.ENGLISH));
        }
    }

    protected int parseCategorySentiment(EmotionCategory category) {
        final int padding = -1;
        String[] candidates = {"NEGATIVE", "NEUTRAL", "POSITIVE"};
        return Arrays.asList(candidates).indexOf(category.toString()) + padding;
    }
}

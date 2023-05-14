package com.sju.sju_language_processing.domains.sentences.service;

import com.sju.sju_language_processing.commons.message.MessageConfig;
import com.sju.sju_language_processing.domains.musics.service.MusicCrudInterface;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import com.sju.sju_language_processing.domains.sentences.entity.SentenceInput;
import com.sju.sju_language_processing.domains.sentences.repository.SentenceInputRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SentenceLogicServ {
    protected SentenceInputRepo inputRepo;
    protected MusicCrudInterface musicCrudInterface;
    protected static MessageSource msgSrc = MessageConfig.getSentenceMsgSrc();

    protected SentenceInput predictInputEmotion(SentenceInput input) throws Exception {
        // TODO: text 를 조회해서 AI 모델로 보낸 후, 맞는 감정 가져오기
        EmotionCategory predictionResult = EmotionCategory.NEUTRAL;
        input.setCategory(predictionResult);
        input.setMusic(musicCrudInterface.fetchRandomMusicByEmotion(predictionResult));
        return input;
    }
}

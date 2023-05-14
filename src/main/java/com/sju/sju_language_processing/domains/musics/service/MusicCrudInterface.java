package com.sju.sju_language_processing.domains.musics.service;

import com.sju.sju_language_processing.domains.musics.entity.Music;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;

public interface MusicCrudInterface {
    Music fetchRandomMusicByEmotion(EmotionCategory emotion) throws Exception;
}

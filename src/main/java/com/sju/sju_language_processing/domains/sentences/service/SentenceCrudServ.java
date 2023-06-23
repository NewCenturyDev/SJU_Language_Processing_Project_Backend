package com.sju.sju_language_processing.domains.sentences.service;

import com.sju.sju_language_processing.domains.musics.service.MusicCrudInterface;
import com.sju.sju_language_processing.domains.sentences.dto.req.CreateSentenceInputReqDTO;
import com.sju.sju_language_processing.domains.sentences.dto.req.UpdateSentenceInputReqDTO;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import com.sju.sju_language_processing.domains.sentences.entity.SentenceInput;
import com.sju.sju_language_processing.domains.sentences.repository.SentenceInputRepo;
import com.sju.sju_language_processing.domains.users.profile.entity.UserProfile;
import com.sju.sju_language_processing.domains.users.profile.services.UserProfileServCommon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class SentenceCrudServ extends SentenceLogicServ implements UserProfileServCommon {
    public SentenceCrudServ(SentenceInputRepo inputRepo, MusicCrudInterface musicCrudInterface) {
        super(inputRepo, musicCrudInterface);
    }

    @Transactional
    public SentenceInput createSentence(Authentication auth, CreateSentenceInputReqDTO reqDTO) throws Exception {
        UserProfile user = this.fetchCurrentUser(auth);

        SentenceInput sentence = SentenceInput.builder()
                .text(reqDTO.getText())
                .userId(user.getId())
                .userEmail(user.getEmail())
                .timestamp(LocalDateTime.now())
                .build();

        sentence = inputRepo.save(sentence);

        sentence = this.predictInputEmotion(sentence);

        return sentence;
    }

    public SentenceInput fetchSentenceById(Long id) throws Exception {
        return inputRepo.findById(id).orElseThrow(
                () -> new Exception(msgSrc.getMessage("error.sentence.notExist", null, Locale.ENGLISH))
        );
    }

    public Page<SentenceInput> fetchAllSentences(int pageIdx, int pageLimit) {
        return inputRepo.findAll(PageRequest.of(pageIdx, pageLimit));
    }

    public Page<SentenceInput> fetchSentencesByCategory(EmotionCategory category, int pageIdx, int pageLimit) {
        return inputRepo.findAllByCategory(category, PageRequest.of(pageIdx, pageLimit));
    }

    public Page<SentenceInput> fetchSentenceByMusicId(Long musicId, int pageIdx, int pageLimit) {
        return inputRepo.findAllByMusicId(musicId, PageRequest.of(pageIdx, pageLimit));
    }

    public Page<SentenceInput> fetchSentenceByTimeRange(LocalDateTime from, LocalDateTime to, int pageIdx, int pageLimit) {
        return inputRepo.findAllByTimestampAfterAndTimestampBefore(from, to == null ? LocalDateTime.now() : to, PageRequest.of(pageIdx, pageLimit));
    }

    @Transactional
    public SentenceInput updateSentence(UpdateSentenceInputReqDTO reqDTO) throws Exception {
        SentenceInput target = this.fetchSentenceById(reqDTO.getId());

        target.setCategory(reqDTO.getCategory());
        return target;
    }

    @Transactional
    public Long deleteSentence(Long id) throws Exception {
        SentenceInput target = this.fetchSentenceById(id);
        inputRepo.delete(target);
        return target.getId();
    }
}

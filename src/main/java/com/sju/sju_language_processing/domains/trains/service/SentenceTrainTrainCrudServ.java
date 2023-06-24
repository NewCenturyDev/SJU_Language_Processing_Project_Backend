package com.sju.sju_language_processing.domains.trains.service;

import com.sju.sju_language_processing.domains.musics.service.MusicCrudInterface;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import com.sju.sju_language_processing.domains.trains.dto.req.CreateSentenceTrainReqDTO;
import com.sju.sju_language_processing.domains.trains.dto.req.UpdateSentenceTrainReqDTO;
import com.sju.sju_language_processing.domains.trains.entity.SentenceTrain;
import com.sju.sju_language_processing.domains.trains.repository.SentenceTrainRepo;
import com.sju.sju_language_processing.domains.users.profile.services.UserProfileServCommon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class SentenceTrainTrainCrudServ extends SentenceTrainLogicServ implements UserProfileServCommon {
    public SentenceTrainTrainCrudServ(SentenceTrainRepo inputRepo, MusicCrudInterface musicCrudInterface) {
        super(inputRepo, musicCrudInterface);
    }

    @Transactional
    public SentenceTrain createSentence(CreateSentenceTrainReqDTO reqDTO) throws Exception {
        this.checkLanguageIsSupported(reqDTO.getLanguage());
        SentenceTrain sentence = SentenceTrain.builder()
                .language(new Locale(reqDTO.getLanguage()))
                .category(reqDTO.getCategory())
                .text(reqDTO.getText())
                .sentiment(this.parseCategorySentiment(reqDTO.getCategory()))
                .build();
        sentence = inputRepo.save(sentence);

        return sentence;
    }

    public SentenceTrain fetchSentenceById(Long id) throws Exception {
        return inputRepo.findById(id).orElseThrow(
                () -> new Exception(msgSrc.getMessage("error.sentence.notExist", null, Locale.ENGLISH))
        );
    }

    public Page<SentenceTrain> fetchAllSentences(int pageIdx, int pageLimit) {
        return inputRepo.findAll(PageRequest.of(pageIdx, pageLimit));
    }

    public Page<SentenceTrain> fetchSentencesByCategory(EmotionCategory category, int pageIdx, int pageLimit) {
        return inputRepo.findAllByCategory(category, PageRequest.of(pageIdx, pageLimit));
    }

    @Transactional
    public SentenceTrain updateSentence(UpdateSentenceTrainReqDTO reqDTO) throws Exception {
        SentenceTrain target = this.fetchSentenceById(reqDTO.getId());

        target.setCategory(reqDTO.getCategory());
        return target;
    }

    @Transactional
    public Long deleteSentence(Long id) throws Exception {
        SentenceTrain target = this.fetchSentenceById(id);
        inputRepo.delete(target);
        return target.getId();
    }
}

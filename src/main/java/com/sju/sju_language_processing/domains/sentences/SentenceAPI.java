package com.sju.sju_language_processing.domains.sentences;

import com.sju.sju_language_processing.commons.http.APIUtil;
import com.sju.sju_language_processing.domains.sentences.dto.req.*;
import com.sju.sju_language_processing.domains.sentences.dto.res.CreateSentenceInputResDTO;
import com.sju.sju_language_processing.domains.sentences.dto.res.DeleteSentenceInputResDTO;
import com.sju.sju_language_processing.domains.sentences.dto.res.FetchSentenceInputResDTO;
import com.sju.sju_language_processing.domains.sentences.dto.res.UpdateSentenceInputResDTO;
import com.sju.sju_language_processing.domains.sentences.entity.SentenceInput;
import com.sju.sju_language_processing.domains.sentences.service.SentenceCrudServ;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SentenceAPI {
    private final SentenceCrudServ sentenceCrudServ;

    @PostMapping("/sentences")
    ResponseEntity<?> createSentenceInput(Authentication auth, @Valid @RequestBody CreateSentenceInputReqDTO reqDTO) {
        CreateSentenceInputResDTO resDTO = new CreateSentenceInputResDTO();

        return new APIUtil<CreateSentenceInputResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setCreatedInput(sentenceCrudServ.createSentence(auth, reqDTO));
            }
        }.execute(resDTO, "res.sentence.create.success");
    }

    @GetMapping("/sentences")
    ResponseEntity<?> fetchSentenceInput(@Valid FetchSentenceInputReqDTO reqDTO, @RequestHeader("Request-Type") String reqHeader) {
        FetchSentenceInputReqType reqType = FetchSentenceInputReqType.valueOf(reqHeader);
        FetchSentenceInputResDTO resDTO = new FetchSentenceInputResDTO();

        return new APIUtil<FetchSentenceInputResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                switch (reqType) {
                    case ID -> resDTO.setSentenceInput(sentenceCrudServ.fetchSentenceById(reqDTO.getId()));
                    case ALL_PAGE -> {
                        Page<SentenceInput> sentenceInputPage = sentenceCrudServ.fetchAllSentences(reqDTO.getPageIdx(), reqDTO.getPageLimit());
                        resDTO.setSentenceInputs(sentenceInputPage.getContent());
                        buildPageableResDTO(resDTO, sentenceInputPage);
                    }
                    case CATEGORY -> {
                        Page<SentenceInput> sentenceInputPage = sentenceCrudServ.fetchSentencesByCategory(reqDTO.getCategory(), reqDTO.getPageIdx(), reqDTO.getPageLimit());
                        resDTO.setSentenceInputs(sentenceInputPage.getContent());
                        buildPageableResDTO(resDTO, sentenceInputPage);
                    }
                    case MUSIC_ID -> {
                        Page<SentenceInput> sentenceInputPage = sentenceCrudServ.fetchSentenceByMusicId(reqDTO.getMusicId(), reqDTO.getPageIdx(), reqDTO.getPageLimit());
                        resDTO.setSentenceInputs(sentenceInputPage.getContent());
                        buildPageableResDTO(resDTO, sentenceInputPage);
                    }
                    case TIME_RANGE -> {
                        Page<SentenceInput> sentenceInputPage = sentenceCrudServ.fetchSentenceByTimeRange(reqDTO.getTimeRangeFrom(), reqDTO.getTimeRangeTo(), reqDTO.getPageIdx(), reqDTO.getPageLimit());
                        resDTO.setSentenceInputs(sentenceInputPage.getContent());
                        buildPageableResDTO(resDTO, sentenceInputPage);
                    }
                }
            }
        }.execute(resDTO, "res.sentence.fetch.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @PutMapping("/sentences")
    ResponseEntity<?> updateSentenceInput(@Valid @RequestBody UpdateSentenceInputReqDTO reqDTO) {
        UpdateSentenceInputResDTO resDTO = new UpdateSentenceInputResDTO();

        return new APIUtil<UpdateSentenceInputResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setUpdatedInput(sentenceCrudServ.updateSentence(reqDTO));
            }
        }.execute(resDTO, "res.sentence.update.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @DeleteMapping("/sentences")
    ResponseEntity<?> deleteSentenceInput(@Valid DeleteSentenceInputReqDTO reqDTO) {
        DeleteSentenceInputResDTO resDTO = new DeleteSentenceInputResDTO();

        return new APIUtil<DeleteSentenceInputResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setDeletedInputId(sentenceCrudServ.deleteSentence(reqDTO.getId()));
            }
        }.execute(resDTO, "res.sentence.delete.success");
    }
}

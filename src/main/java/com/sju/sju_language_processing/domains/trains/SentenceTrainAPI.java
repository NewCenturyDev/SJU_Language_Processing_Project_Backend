package com.sju.sju_language_processing.domains.trains;

import com.sju.sju_language_processing.commons.http.APIUtil;
import com.sju.sju_language_processing.domains.trains.dto.req.*;
import com.sju.sju_language_processing.domains.trains.dto.res.CreateSentenceTrainResDTO;
import com.sju.sju_language_processing.domains.trains.dto.res.DeleteSentenceTrainResDTO;
import com.sju.sju_language_processing.domains.trains.dto.res.FetchSentenceTrainResDTO;
import com.sju.sju_language_processing.domains.trains.dto.res.UpdateSentenceTrainResDTO;
import com.sju.sju_language_processing.domains.trains.entity.SentenceTrain;
import com.sju.sju_language_processing.domains.trains.service.SentenceTrainTrainCrudServ;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SentenceTrainAPI {
    private final SentenceTrainTrainCrudServ sentenceTrainCrudServ;

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @PostMapping("/sentences")
    ResponseEntity<?> createSentenceInput(@Valid @RequestBody CreateSentenceTrainReqDTO reqDTO) {
        CreateSentenceTrainResDTO resDTO = new CreateSentenceTrainResDTO();

        return new APIUtil<CreateSentenceTrainResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setCreatedTrain(sentenceTrainCrudServ.createSentence(reqDTO));
            }
        }.execute(resDTO, "res.sentence.create.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @GetMapping("/sentences")
    ResponseEntity<?> fetchSentenceInput(@Valid FetchSentenceTrainReqDTO reqDTO, @RequestHeader("Request-Type") String reqHeader) {
        FetchSentenceTrainReqType reqType = FetchSentenceTrainReqType.valueOf(reqHeader);
        FetchSentenceTrainResDTO resDTO = new FetchSentenceTrainResDTO();

        return new APIUtil<FetchSentenceTrainResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                switch (reqType) {
                    case ID -> resDTO.setSentenceTrain(sentenceTrainCrudServ.fetchSentenceById(reqDTO.getId()));
                    case ALL_PAGE -> {
                        Page<SentenceTrain> sentenceInputPage = sentenceTrainCrudServ.fetchAllSentences(reqDTO.getPageIdx(), reqDTO.getPageLimit());
                        resDTO.setSentenceTrains(sentenceInputPage.getContent());
                        buildPageableResDTO(resDTO, sentenceInputPage);
                    }
                    case CATEGORY -> {
                        Page<SentenceTrain> sentenceInputPage = sentenceTrainCrudServ.fetchSentencesByCategory(reqDTO.getCategory(), reqDTO.getPageIdx(), reqDTO.getPageLimit());
                        resDTO.setSentenceTrains(sentenceInputPage.getContent());
                        buildPageableResDTO(resDTO, sentenceInputPage);
                    }
                }
            }
        }.execute(resDTO, "res.sentence.fetch.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @PutMapping("/sentences")
    ResponseEntity<?> updateSentenceInput(@Valid @RequestBody UpdateSentenceTrainReqDTO reqDTO) {
        UpdateSentenceTrainResDTO resDTO = new UpdateSentenceTrainResDTO();

        return new APIUtil<UpdateSentenceTrainResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setUpdatedTrain(sentenceTrainCrudServ.updateSentence(reqDTO));
            }
        }.execute(resDTO, "res.sentence.update.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @DeleteMapping("/sentences")
    ResponseEntity<?> deleteSentenceInput(@Valid DeleteSentenceTrainReqDTO reqDTO) {
        DeleteSentenceTrainResDTO resDTO = new DeleteSentenceTrainResDTO();

        return new APIUtil<DeleteSentenceTrainResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setDeletedTrainId(sentenceTrainCrudServ.deleteSentence(reqDTO.getId()));
            }
        }.execute(resDTO, "res.sentence.delete.success");
    }
}

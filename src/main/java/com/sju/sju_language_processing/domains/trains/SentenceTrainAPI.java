package com.sju.sju_language_processing.domains.trains;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sju.sju_language_processing.commons.http.APIUtil;
import com.sju.sju_language_processing.commons.http.DTOMetadata;
import com.sju.sju_language_processing.commons.http.GeneralResDTO;
import com.sju.sju_language_processing.domains.trains.dto.req.*;
import com.sju.sju_language_processing.domains.trains.dto.res.CreateSentenceTrainResDTO;
import com.sju.sju_language_processing.domains.trains.dto.res.DeleteSentenceTrainResDTO;
import com.sju.sju_language_processing.domains.trains.dto.res.FetchSentenceTrainResDTO;
import com.sju.sju_language_processing.domains.trains.dto.res.UpdateSentenceTrainResDTO;
import com.sju.sju_language_processing.domains.trains.entity.SentenceTrain;
import com.sju.sju_language_processing.domains.trains.service.SentenceTrainCrudServ;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SentenceTrainAPI {
    private final SentenceTrainCrudServ sentenceTrainCrudServ;
    private final ObjectMapper objectMapper;

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @PostMapping("/trains")
    ResponseEntity<?> createSentenceTrain(@Valid @RequestBody CreateSentenceTrainReqDTO reqDTO) {
        CreateSentenceTrainResDTO resDTO = new CreateSentenceTrainResDTO();

        return new APIUtil<CreateSentenceTrainResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setCreatedTrain(sentenceTrainCrudServ.createSentence(reqDTO));
            }
        }.execute(resDTO, "res.sentence.create.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @GetMapping("/trains")
    ResponseEntity<?> fetchSentenceTrain(@Valid FetchSentenceTrainReqDTO reqDTO, @RequestHeader("Request-Type") String reqHeader) {
        FetchSentenceTrainReqType reqType = FetchSentenceTrainReqType.valueOf(reqHeader);
        FetchSentenceTrainResDTO resDTO = new FetchSentenceTrainResDTO();

        return new APIUtil<FetchSentenceTrainResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                switch (reqType) {
                    case ID -> resDTO.setSentenceTrain(sentenceTrainCrudServ.fetchSentenceById(reqDTO.getId()));
                    case ALL_PAGE -> {
                        Page<SentenceTrain> sentenceTrainPage = sentenceTrainCrudServ.fetchAllSentences(reqDTO.getPageIdx(), reqDTO.getPageLimit());
                        resDTO.setSentenceTrains(sentenceTrainPage.getContent());
                        buildPageableResDTO(resDTO, sentenceTrainPage);
                    }
                    case CATEGORY -> {
                        Page<SentenceTrain> sentenceTrainPage = sentenceTrainCrudServ.fetchSentencesByCategory(reqDTO.getCategory(), reqDTO.getPageIdx(), reqDTO.getPageLimit());
                        resDTO.setSentenceTrains(sentenceTrainPage.getContent());
                        buildPageableResDTO(resDTO, sentenceTrainPage);
                    }
                }
            }
        }.execute(resDTO, "res.sentence.fetch.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @GetMapping("/trains/data")
    void downloadSentenceTrain(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename=\"NLP_Train_Data.zip\"");
        try {
            this.sentenceTrainCrudServ.downloadTrainData(response.getOutputStream());
        } catch (Exception e) {
            try {
                GeneralResDTO resDTO = new GeneralResDTO(new DTOMetadata(e.getLocalizedMessage(), e.getClass().toString()));
                response.setStatus(422);
                response.setContentType("application/json");
                String responseJSONString = objectMapper.writeValueAsString(resDTO);
                response.getWriter().write(responseJSONString);
            } catch (Exception ignored) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @PutMapping("/trains")
    ResponseEntity<?> updateSentenceTrain(@Valid @RequestBody UpdateSentenceTrainReqDTO reqDTO) {
        UpdateSentenceTrainResDTO resDTO = new UpdateSentenceTrainResDTO();

        return new APIUtil<UpdateSentenceTrainResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setUpdatedTrain(sentenceTrainCrudServ.updateSentence(reqDTO));
            }
        }.execute(resDTO, "res.sentence.update.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @DeleteMapping("/trains")
    ResponseEntity<?> deleteSentenceTrain(@Valid DeleteSentenceTrainReqDTO reqDTO) {
        DeleteSentenceTrainResDTO resDTO = new DeleteSentenceTrainResDTO();

        return new APIUtil<DeleteSentenceTrainResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setDeletedTrainId(sentenceTrainCrudServ.deleteSentence(reqDTO.getId()));
            }
        }.execute(resDTO, "res.sentence.delete.success");
    }
}

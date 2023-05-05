package com.sju.sju_language_processing.domains.sentences;

import com.sju.sju_language_processing.commons.http.APIUtil;
import com.sju.sju_language_processing.domains.sentences.dto.req.*;
import com.sju.sju_language_processing.domains.sentences.dto.res.CreateSentenceInputResDTO;
import com.sju.sju_language_processing.domains.sentences.dto.res.DeleteSentenceInputResDTO;
import com.sju.sju_language_processing.domains.sentences.dto.res.FetchSentenceInputResDTO;
import com.sju.sju_language_processing.domains.sentences.dto.res.UpdateSentenceInputResDTO;
import com.sju.sju_language_processing.domains.sentences.service.SentenceCrudServ;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class SentenceAPI {
    private SentenceCrudServ sentenceCrudServ;

    @PostMapping("/sentences/inputs")
    ResponseEntity<?> createSentenceInput(Authentication auth, @Valid @RequestBody CreateSentenceInputReqDTO reqDTO) {
        CreateSentenceInputResDTO resDTO = new CreateSentenceInputResDTO();

        return new APIUtil<CreateSentenceInputResDTO>() {
            @Override
            protected void onSuccess() throws Exception {

            }
        }.execute(resDTO, "sentence.input.success");
    }

    @GetMapping("/sentences/inputs")
    ResponseEntity<?> createSentenceInput(Authentication auth, @Valid FetchSentenceInputReqDTO reqDTO, @RequestHeader("Request-Type") String reqHeader) {
        FetchSentenceInputReqType reqType = FetchSentenceInputReqType.valueOf(reqHeader);
        FetchSentenceInputResDTO resDTO = new FetchSentenceInputResDTO();

        return new APIUtil<FetchSentenceInputResDTO>() {
            @Override
            protected void onSuccess() throws Exception {

            }
        }.execute(resDTO, "sentence.input.fetch.success");
    }

    @PutMapping("/sentences/inputs")
    ResponseEntity<?> createSentenceInput(Authentication auth, @Valid @RequestBody UpdateSentenceInputReqDTO reqDTO) {
        UpdateSentenceInputResDTO resDTO = new UpdateSentenceInputResDTO();

        return new APIUtil<UpdateSentenceInputResDTO>() {
            @Override
            protected void onSuccess() throws Exception {

            }
        }.execute(resDTO, "sentence.input.update.success");
    }

    @DeleteMapping("/sentences/inputs")
    ResponseEntity<?> createSentenceInput(Authentication auth, @Valid DeleteSentenceInputReqDTO reqDTO) {
        DeleteSentenceInputResDTO resDTO = new DeleteSentenceInputResDTO();

        return new APIUtil<DeleteSentenceInputResDTO>() {
            @Override
            protected void onSuccess() throws Exception {

            }
        }.execute(resDTO, "sentence.input.delete.success");
    }
}

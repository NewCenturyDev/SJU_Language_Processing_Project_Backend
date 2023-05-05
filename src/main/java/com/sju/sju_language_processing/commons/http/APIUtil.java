package com.sju.sju_language_processing.commons.http;

import com.sju.sju_language_processing.commons.message.MessageConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.data.domains.Page;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public abstract class APIUtil<TResDTO extends GeneralResDTO> {
    private final MessageSource msgSrc = MessageConfig.getResponseMsgSrc();
    private static final Logger logger = LogManager.getLogger();

    protected abstract void onSuccess() throws Exception;

    public ResponseEntity<?> execute(TResDTO resDTO, String successMsg) {
        try {
            logger.info("Requested API: " + resDTO.getClass().getName());
            onSuccess();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            logger.warn("------------------------------ Request Process Failed | StackTrace ------------------------------");
            logger.warn(e);
            logger.warn("-------------------------------------------------------------------------------------------------");
            resDTO.set_metadata(new DTOMetadata(e.getMessage(), e.getClass().getName()));
            return ResponseEntity.status(400).body(resDTO);
        }
        resDTO.set_metadata(new DTOMetadata(msgSrc.getMessage(successMsg, null, Locale.ENGLISH)));
        return ResponseEntity.ok(resDTO);
    }

    protected <TPageResDTO extends GeneralPageableResDTO> void buildPageableResDTO(TPageResDTO resDTO, Page<?> page) {
        resDTO.setPageable(true);
        resDTO.setPageIdx(page.getNumber());
        resDTO.setTotalPage(page.getTotalPages());
        resDTO.setPageElementSize(page.getTotalElements());
    }
}

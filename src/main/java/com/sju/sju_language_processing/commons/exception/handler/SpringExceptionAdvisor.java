package com.sju.sju_language_processing.commons.exception.handler;

import com.sju.sju_language_processing.commons.http.DTOMetadata;
import com.sju.sju_language_processing.commons.http.GeneralResDTO;
import com.sju.sju_language_processing.commons.message.MessageConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Objects;

@ControllerAdvice
@RestController
/* 전역 예외 처리 어드바이저 */
public class SpringExceptionAdvisor {
    private static final Logger logger = LogManager.getLogger();
    private final MessageSource msgSrc = MessageConfig.getValidationMsgSrc();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> processValidationError(MethodArgumentNotValidException ex) {
        DTOMetadata dtoMetadata;
        String errMsg;
        try {
            errMsg = msgSrc.getMessage(
                    Objects.requireNonNull(ex.getAllErrors().get(0).getDefaultMessage()),
                    ex.getAllErrors().get(0).getArguments(),
                    Locale.ENGLISH
            );
        } catch (Exception e) {
            errMsg = "Error message localize failure - Unknown Error";
        }

        logger.warn(ex.toString());
        dtoMetadata = new DTOMetadata(errMsg, ex.getClass().getName());
        return ResponseEntity.status(400).body(new GeneralResDTO(dtoMetadata));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> processBindError(BindException ex) {
        DTOMetadata dtoMetadata;
        String errMsg;
        try {
            errMsg = msgSrc.getMessage(
                    "valid.binding",
                    null,
                    Locale.ENGLISH
            );
        } catch (Exception e) {
            errMsg = "Error message localize failure - Unknown Error";
        }

        logger.warn(ex.toString());
        dtoMetadata = new DTOMetadata(errMsg, ex.getClass().getName());
        return ResponseEntity.status(400).body(new GeneralResDTO(dtoMetadata));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> processMissingParamError(Exception ex) {
        DTOMetadata dtoMetadata;
        String errMsg;
        try {
            errMsg = ex.getLocalizedMessage();
        } catch (Exception e) {
            errMsg = "Error message localize failure - Unknown Error";
        }

        logger.warn(ex.toString());
        dtoMetadata = new DTOMetadata(errMsg, ex.getClass().getName());
        return ResponseEntity.status(400).body(new GeneralResDTO(dtoMetadata));
    }
}

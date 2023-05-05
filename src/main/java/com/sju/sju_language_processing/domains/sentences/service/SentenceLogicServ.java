package com.sju.sju_language_processing.domains.sentences.service;

import com.sju.sju_language_processing.domains.sentences.repository.SentenceInputRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SentenceLogicServ {
    protected SentenceInputRepo inputRepo;
}

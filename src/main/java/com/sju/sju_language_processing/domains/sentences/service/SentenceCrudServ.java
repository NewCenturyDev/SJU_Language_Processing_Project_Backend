package com.sju.sju_language_processing.domains.sentences.service;

import com.sju.sju_language_processing.domains.sentences.repository.SentenceInputRepo;
import org.springframework.stereotype.Service;

@Service
public class SentenceCrudServ extends SentenceLogicServ {
    public SentenceCrudServ(SentenceInputRepo inputRepo) {
        super(inputRepo);
    }
}

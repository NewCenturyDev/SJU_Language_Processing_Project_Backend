package com.sju.sju_language_processing.domains.musics.service;

import com.sju.sju_language_processing.domains.musics.repository.MusicRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MusicLogicServ {
    protected MusicRepo musicRepo;
}

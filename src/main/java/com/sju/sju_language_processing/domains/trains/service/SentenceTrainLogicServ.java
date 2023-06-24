package com.sju.sju_language_processing.domains.trains.service;

import com.sju.sju_language_processing.commons.message.MessageConfig;
import com.sju.sju_language_processing.commons.storage.StorageService;
import com.sju.sju_language_processing.domains.musics.service.MusicCrudInterface;
import com.sju.sju_language_processing.domains.sentences.entity.EmotionCategory;
import com.sju.sju_language_processing.domains.trains.repository.SentenceTrainRepo;
import jakarta.servlet.ServletOutputStream;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@AllArgsConstructor
public class SentenceTrainLogicServ {
    protected SentenceTrainRepo inputRepo;
    protected MusicCrudInterface musicCrudInterface;
    protected StorageService storageServ;
    protected static MessageSource msgSrc = MessageConfig.getSentenceMsgSrc();

    protected void checkLanguageIsSupported(String languageStr) throws Exception {
        if (!languageStr.equals("en") && !languageStr.equals("ko")) {
            throw new Exception(msgSrc.getMessage("error.sentence.language", null, Locale.ENGLISH));
        }
    }

    protected int parseCategorySentiment(EmotionCategory category) {
        final int padding = -1;
        String[] candidates = {"NEGATIVE", "NEUTRAL", "POSITIVE"};
        return Arrays.asList(candidates).indexOf(category.toString()) + padding;
    }

    protected void zipDataFiles(ServletOutputStream stream) throws Exception{
        FileInputStream fis;
        ZipOutputStream zipOut = new ZipOutputStream(stream);
        URL[] resources = {
                getClass().getClassLoader().getResource("python/preprocessed/data_configs.json"),
                getClass().getClassLoader().getResource("python/preprocessed/tokenizer.json"),
                getClass().getClassLoader().getResource("python/preprocessed/train_clean.csv"),
                getClass().getClassLoader().getResource("python/preprocessed/train_input.npy"),
                getClass().getClassLoader().getResource("python/preprocessed/train_label.npy")
        };
        for (URL res : resources) {
            if (res != null) {
                File resFile = Paths.get(res.toURI()).toFile();
                zipOut.putNextEntry(new ZipEntry(resFile.getName()));
                fis = new FileInputStream(resFile);
                StreamUtils.copy(fis, zipOut);
                fis.close();
                zipOut.closeEntry();
            }
        }
        zipOut.close();
    }

    protected void preProcessTrainData() throws Exception {
        URL resource = getClass().getClassLoader().getResource("python/english_preprocess.py");
        String pythonScriptPath = Paths.get(Objects.requireNonNull(resource).toURI()).toString();
        ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath);
        Process p = pb.start();
        p.waitFor();
    }

    protected void cleanWorkingDir() throws Exception{
        URL[] resources = {
                getClass().getClassLoader().getResource("python/preprocessed/data_configs.json"),
                getClass().getClassLoader().getResource("python/preprocessed/tokenizer.json"),
                getClass().getClassLoader().getResource("python/preprocessed/train_clean.csv"),
                getClass().getClassLoader().getResource("python/preprocessed/train_input.npy"),
                getClass().getClassLoader().getResource("python/preprocessed/train_label.npy")
        };
        for (URL res : resources) {
            if (res != null) {
                File previousResFile = Paths.get(res.toURI()).toFile();
                if (!previousResFile.delete()) {
                    throw new Exception(msgSrc.getMessage("error.sentence.reset", null, Locale.ENGLISH));
                }
            }
        }
    }
}

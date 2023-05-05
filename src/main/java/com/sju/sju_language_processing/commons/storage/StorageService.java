package com.sju.sju_language_processing.commons.storage;

import com.sju.sju_language_processing.commons.base.media.FileMetadata;
import com.sju.sju_language_processing.commons.message.MessageConfig;
import lombok.Getter;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class StorageService {
    private final MessageSource msgSrc = MessageConfig.getStorageMsgSrc();

    /***************************** 변경 금지 구역 *******************************/
    // IMPORTANT: storageRootPath 는 환경 변수별로 분리된 application.properties 에 기록합니다. (storage.rootPath 값)

    @Getter
    @Value("${storage.rootPath}")
    private String storageRootPath;
    /**************************************************************************/

    // 확장자 제한 사항
    private final String[] PHOTO_EXT = new String[]{
            "jpg", "png", "jpeg", "gif", "webp"
    };
    private final String[] VIDEO_EXT = new String[]{
            "3gp", "mp4", "webm"
    };
    private final String[] DOCUMENT_EXT = new String[]{
            "doc", "docx", "hwp", "pdf", "txt", "ppt", "pptx", "psd", "ai", "xls", "xlsx",
            "rar", "tar", "zip", "exe", "apk"
    };
    private final String[] AUDIO_EXT = new String[]{
            "mp3", "flac", "wav"
    };

    // 공통 - 파일 확장자 추출
    private String getFileExt(String originalFileName) {
        List<String> fileNameTokens = Arrays.asList(originalFileName.split("\\."));
        return fileNameTokens.get(fileNameTokens.size() - 1);
    }

    // 공통 - 파일 이름 생성 로직
    private String generateFileName(String originalFileName, String domain, Long domainId) {
        // 파일이름 중복 방지를 위해 엔티티 접두어 + 엔티티 ID + UUID 에 확장자를 붙여 새로운 파일 이름을 생성
        return domain + "_" + domainId.toString() + "_" + UUID.randomUUID() + "." + this.getFileExt(Objects.requireNonNull(originalFileName));
    }

    // 공통 - 파일 검증 로직
    private void checkFileValidity(MultipartFile uploadedFile, FileType fileType) throws Exception {
        // 업로드 파일 원본 파일명
        String originalFileName = uploadedFile.getOriginalFilename();
        String[] acceptableExtensions = null;

        // 용량 및 확장자 제한 사항
        long MEGABYTE = 1000000;
        long fileSizeLimit = MEGABYTE;
        switch (fileType) {
            case MEDIA_PHOTO -> {
                acceptableExtensions = this.PHOTO_EXT;
                fileSizeLimit = 2 * MEGABYTE;
            }
            case MEDIA_VIDEO -> {
                acceptableExtensions = this.VIDEO_EXT;
                fileSizeLimit = 100 * MEGABYTE;
            }
            case MEDIA_DOCUMENT -> {
                acceptableExtensions = this.DOCUMENT_EXT;
                fileSizeLimit = 10 * MEGABYTE;
            }
            case MEDIA_AUDIO -> {
                acceptableExtensions = this.AUDIO_EXT;
                fileSizeLimit = 20 * MEGABYTE;
            }
        }

        // 빈 파일 검사
        if (uploadedFile.isEmpty()) {
            throw new Exception(msgSrc.getMessage("error.file.empty", new String[]{originalFileName}, Locale.ENGLISH));
        }
        // 파일 확장자 적합성 검사
        else if (acceptableExtensions == null || Arrays.stream(acceptableExtensions).noneMatch(
                extension -> this.getFileExt(Objects.requireNonNull(uploadedFile.getOriginalFilename())).toLowerCase().equals(extension)
        )) {
            throw new Exception(msgSrc.getMessage("error.file.extension.valid", new String[]{originalFileName}, Locale.ENGLISH));
        }
        // 파일 크기 적합성 검사
        else if (uploadedFile.getSize() > fileSizeLimit) {
            throw new Exception(msgSrc.getMessage("error.file.size", new String[]{originalFileName}, Locale.ENGLISH));
        }
    }

    // 공통 - 특정 엔티티 스토리지에 빈 파일 생성
    public File createEmptyFile(Path parentPath, String entityName, Long entityId, String extension) {
        String generatedFileName = this.generateFileName("file", entityName, entityId);
        generatedFileName = generatedFileName.replace(".file", "." + extension);
        Path fileFullPath = Paths.get(this.getEntityStoragePath(parentPath, entityName, entityId).toString(), generatedFileName);
        return fileFullPath.toFile();
    }

    // 공통 - 파일 저장
    public FileMetadata saveFile(MultipartFile uploadedFile, FileType fileType, String entityName, Long entityId) throws Exception {
        // 파일 유효성 검사
        this.checkFileValidity(uploadedFile, fileType);
        String generatedFileName = this.generateFileName(uploadedFile.getOriginalFilename(), entityName, entityId);
        Path fileFullPath = Paths.get(this.getEntityStoragePath(entityName, entityId).toString(), generatedFileName);

        // 파일 저장
        uploadedFile.transferTo(fileFullPath);

        // 파일 메타데이터 반환
        return buildFileMetadata(fileFullPath, fileType, uploadedFile.getSize());
    }

    public FileMetadata saveFile(MultipartFile uploadedFile, FileType fileType, Path parentPath, String entityName, Long entityId) throws Exception {
        // 파일 유효성 검사
        this.checkFileValidity(uploadedFile, fileType);
        String generatedFileName = this.generateFileName(uploadedFile.getOriginalFilename(), entityName, entityId);
        Path fileFullPath = Paths.get(this.getEntityStoragePath(parentPath, entityName, entityId).toString(), generatedFileName);

        // 파일 저장
        // Create Parent Dir If not Exists
        Files.createDirectories(fileFullPath.getParent());
        uploadedFile.transferTo(fileFullPath);

        // 파일 메타데이터 반환
        return buildFileMetadata(fileFullPath, fileType, uploadedFile.getSize());
    }

    private FileMetadata buildFileMetadata(Path savePath, FileType fileType, Long fileSize) {
        // 파일 메타데이터 생성 (윈도우 환경 대응을 위해 \\를 /로 일괄 변환)
        return FileMetadata.builder()
                .type(fileType)
                .fileName(savePath.getFileName().toString())
                .fileURL("/storage" + savePath.toString().replace(this.storageRootPath, "").replace("\\", "/"))
                .fileSize(fileSize)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // 공통 - 파일 삭제
    public void deleteFile(String url) throws Exception {
        String osName = System.getProperty("os.name").toLowerCase();

        // 파일 삭제
        Path savePath;
        if (osName.contains("win")) {
            savePath = Paths.get(this.storageRootPath, url.replace("/storage", "").replace("/", "\\"));
        } else {
            savePath = Paths.get(this.storageRootPath, url.replace("/storage", ""));
        }
        Files.deleteIfExists(savePath);
    }

    // 공통 - 특정 경로에 디렉터리 생성
    private void createDirectory(Path dirPath) throws Exception {
        if (!dirPath.toFile().mkdir()) {
            throw new Exception(
                    msgSrc.getMessage("error.dir.create.fail", null, Locale.ENGLISH)
            );
        }
    }

    // 공통 - 디렉터리가 존재하지 않을 경우 디렉토리 생성
    private void createDirectoryIfNotExist(Path dirPath) throws Exception {
        if (!dirPath.toFile().isDirectory()) {
            this.createDirectory(dirPath);
        }
    }

    // 공통 - 엔티티 루트 디렉토리 생성
    private void createEntityRootStorageIfNotExist(String entityName) throws Exception {
        Path entityStorageRootPath = Paths.get(this.storageRootPath, entityName);
        this.createDirectoryIfNotExist(entityStorageRootPath);
    }

    // 공통 - 하위 엔티티 루트 디렉토리 생성
    private void createEntityRootStorageIfNotExist(Path parentEntityStoragePath, String subEntityName) throws Exception {
        Path subEntityStorageRootPath = Paths.get(parentEntityStoragePath.toString(), subEntityName);
        // Create Parent Dir If not Exists
        Files.createDirectories(subEntityStorageRootPath);
        this.createDirectoryIfNotExist(subEntityStorageRootPath);
    }

    // 공통 - 엔티티 개별 디렉토리 생성
    public void createEntityStorage(String entityName, Long entityId) throws Exception {
        Path entityStoragePath = Paths.get(this.storageRootPath, entityName, entityName + "_" + entityId.toString());
        this.createEntityRootStorageIfNotExist(entityName);
        if (entityStoragePath.toFile().isDirectory()) {
            throw new Exception(
                    this.msgSrc.getMessage("storage.dir.exists", null, Locale.ENGLISH)
            );
        } else {
            this.createDirectory(entityStoragePath);
        }
    }

    // 공통 - 하위 엔티티 개별 디렉토리 생성
    public void createEntityStorage(Path parentEntityStoragePath, String subEntity, Long subEntityId) throws Exception {
        Path subEntityStoragePath = Paths.get(parentEntityStoragePath.toString(), subEntity, subEntity + "_" + subEntityId);
        this.createEntityRootStorageIfNotExist(parentEntityStoragePath, subEntity);
        if (subEntityStoragePath.toFile().isDirectory()) {
            throw new Exception(
                    this.msgSrc.getMessage("storage.dir.exists", null, Locale.ENGLISH)
            );
        } else {
            this.createDirectory(subEntityStoragePath);
        }
    }

    // 공통 - 엔티티 스토리지 경로 조회
    public Path getEntityStoragePath(String entityName, Long entityId) {
        return Paths.get(this.storageRootPath, entityName, entityName + "_" + entityId);
    }

    // 공통 - 하위 엔티티 스토리지 경로 조회
    public Path getEntityStoragePath(Path parentEntityStoragePath, String subEntityName, Long subEntityId) {
        return Paths.get(parentEntityStoragePath.toString(), subEntityName, subEntityName + "_" + subEntityId);
    }

    // 공통 - 엔티티 스토리지 삭제
    public void deleteEntityStorage(String entityName, Long entityId) throws Exception {
        FileUtils.deleteDirectory(this.getEntityStoragePath(entityName, entityId).toFile());
    }

    // 공통 - 자식 엔티티 스토리지 삭제
    public void deleteEntityStorage(Path parentEntityStoragePath, String subEntityName, Long subEntityId) throws Exception {
        FileUtils.deleteDirectory(this.getEntityStoragePath(parentEntityStoragePath, subEntityName, subEntityId).toFile());
    }

    /* 코스 관련 */

    // 코스 삭제시 첨부파일 디렉토리 삭제 메소드
    public void deleteCourseFileStorage(Long courseId) throws IOException {
        File courseDetailFileStorage = Paths.get(this.storageRootPath, "courses", "course_" + courseId.toString()).toFile();
        FileUtils.deleteDirectory(courseDetailFileStorage);
    }
}

package com.sju.sju_language_processing.domains.musics;

import com.sju.sju_language_processing.commons.http.APIUtil;
import com.sju.sju_language_processing.domains.musics.dto.req.DeleteMusicReqDTO;
import com.sju.sju_language_processing.domains.musics.dto.req.FetchMusicReqDTO;
import com.sju.sju_language_processing.domains.musics.dto.req.UpdateMusicCategoryReqDTO;
import com.sju.sju_language_processing.domains.musics.dto.req.UploadMusicReqDTO;
import com.sju.sju_language_processing.domains.musics.dto.res.DeleteMusicResDTO;
import com.sju.sju_language_processing.domains.musics.dto.res.FetchMusicResDTO;
import com.sju.sju_language_processing.domains.musics.dto.res.UpdateMusicCategoryResDTO;
import com.sju.sju_language_processing.domains.musics.dto.res.UploadMusicResDTO;
import com.sju.sju_language_processing.domains.musics.service.MusicCrudServ;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MusicAPI {
    private final MusicCrudServ musicCrudServ;

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @PostMapping("/musics")
    ResponseEntity<?> uploadMusic(@Valid UploadMusicReqDTO reqDTO) {
        UploadMusicResDTO resDTO = new UploadMusicResDTO();

        return new APIUtil<UploadMusicResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setUploadedMusic(musicCrudServ.createMusic(reqDTO));
            }
        }.execute(resDTO, "res.music.upload.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROOT_ADMIN')")
    @GetMapping("/musics")
    ResponseEntity<?> fetchMusic(@Valid FetchMusicReqDTO reqDTO) {
        FetchMusicResDTO resDTO = new FetchMusicResDTO();

        return new APIUtil<FetchMusicResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setMusics(musicCrudServ.fetchAllMusicsByEmotion(reqDTO.getCategory()));
            }
        }.execute(resDTO, "res.music.fetch.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @PutMapping("/musics")
    ResponseEntity<?> updateMusicCategory(@Valid @RequestBody UpdateMusicCategoryReqDTO reqDTO) {
        UpdateMusicCategoryResDTO resDTO = new UpdateMusicCategoryResDTO();

        return new APIUtil<UpdateMusicCategoryResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setUpdatedMusic(musicCrudServ.updateMusicCategory(reqDTO));
            }
        }.execute(resDTO, "res.music.update.success");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','ROOT_ADMIN')")
    @DeleteMapping("/musics")
    ResponseEntity<?> deleteMusic(@Valid DeleteMusicReqDTO reqDTO) {
        DeleteMusicResDTO resDTO = new DeleteMusicResDTO();

        return new APIUtil<DeleteMusicResDTO>() {
            @Override
            protected void onSuccess() throws Exception {
                resDTO.setDeletedMusicId(musicCrudServ.deleteMusic(reqDTO.getId()));
            }
        }.execute(resDTO, "res.music.delete.success");
    }
}

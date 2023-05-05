package com.sju.sju_language_processing.domains.musics;

import com.sju.sju_language_processing.commons.http.APIUtil;
import com.sju.sju_language_processing.domains.musics.dto.req.DeleteMusicReqDTO;
import com.sju.sju_language_processing.domains.musics.dto.req.UpdateMusicCategoryReqDTO;
import com.sju.sju_language_processing.domains.musics.dto.req.UploadMusicReqDTO;
import com.sju.sju_language_processing.domains.musics.dto.res.DeleteMusicResDTO;
import com.sju.sju_language_processing.domains.musics.dto.res.UpdateMusicCategoryResDTO;
import com.sju.sju_language_processing.domains.musics.dto.res.UploadMusicResDTO;
import com.sju.sju_language_processing.domains.musics.service.MusicCrudServ;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class MusicAPI {
    MusicCrudServ musicCrudServ;

    @PostMapping("/musics")
    ResponseEntity<?> uploadMusic(Authentication auth, @Valid UploadMusicReqDTO reqDTO) {
        UploadMusicResDTO resDTO = new UploadMusicResDTO();

        return new APIUtil<UploadMusicResDTO>() {
            @Override
            protected void onSuccess() throws Exception {

            }
        }.execute(resDTO, "success.music.upload");
    }

    @PutMapping("/musics")
    ResponseEntity<?> updateMusicCategory(Authentication auth, @Valid @RequestBody UpdateMusicCategoryReqDTO reqDTO) {
        UpdateMusicCategoryResDTO resDTO = new UpdateMusicCategoryResDTO();

        return new APIUtil<UpdateMusicCategoryResDTO>() {
            @Override
            protected void onSuccess() throws Exception {

            }
        }.execute(resDTO, "success.music.update");
    }

    @DeleteMapping("/musics")
    ResponseEntity<?> deleteMusic(Authentication auth, @Valid DeleteMusicReqDTO reqDTO) {
        DeleteMusicResDTO resDTO = new DeleteMusicResDTO();

        return new APIUtil<DeleteMusicResDTO>() {
            @Override
            protected void onSuccess() throws Exception {

            }
        }.execute(resDTO, "success.music.delete");
    }
}

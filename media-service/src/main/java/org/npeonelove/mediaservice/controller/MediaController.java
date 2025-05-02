package org.npeonelove.mediaservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.npeonelove.mediaservice.service.MediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<List<String>> uploadMedia(@RequestPart("directory") String directory,
                                                    @RequestPart("file") MultipartFile[] files) {
        return ResponseEntity.ok(mediaService.uploadFiles(directory, files));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteMedia(@RequestParam("keys") String[] keys) {
        mediaService.deleteFiles(keys);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

package org.npeonelove.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "profile-client", url = "http://localhost:8084")
public interface ProfileClient {

//    // возвращает список из ссылок на добавленные объекты
//    @PostMapping(value = "/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    List<String> uploadMedia(@RequestPart("directory") String directory, @RequestPart("file") MultipartFile[] files);
//
//    // возвращает http статус что все ок (ex: "OK")
//    @DeleteMapping(value = "/media")
//    String deleteMedia(@RequestParam("keys") String[] keys);

    @PostMapping(value = "/profile")
    void createProfile();
}

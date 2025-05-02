package org.npeonelove.catalogservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "media-client", url = "http://localhost:8082")
public interface MediaClient {

    @PostMapping(value = "/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<String> uploadMedia(@RequestPart("directory") String directory, @RequestPart("file") MultipartFile[] files);

    @DeleteMapping(value = "/media")
    String deleteMedia(@RequestParam("keys") String[] keys);
}

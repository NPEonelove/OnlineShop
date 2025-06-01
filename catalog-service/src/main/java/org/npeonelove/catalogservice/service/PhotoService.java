package org.npeonelove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.npeonelove.catalogservice.client.MediaFeignClient;
import org.npeonelove.catalogservice.dto.photo.GetPhotoDTO;
import org.npeonelove.catalogservice.exception.photo.PhotoNotExistsException;
import org.npeonelove.catalogservice.exception.product.ProductNotExistsException;
import org.npeonelove.catalogservice.model.Photo;
import org.npeonelove.catalogservice.model.Product;
import org.npeonelove.catalogservice.repository.PhotoRepository;
import org.npeonelove.catalogservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final ProductRepository productRepository;
    private final MediaFeignClient mediaFeignClient;

    @Transactional
    public void saveProductPhotos(long id, MultipartFile[] files) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotExistsException("Product not found");
        }
        List<String> links = mediaFeignClient.uploadMedia("catalog/productPhotos", files);


        for (String link : links) {
            Photo photo = new Photo();
            photo.setProduct(product.get());
            photo.setPhotoLink(link);
            photoRepository.save(photo);
        }
    }

    public List<GetPhotoDTO> getProductPhotos(long id) {
        List<Photo> photos = photoRepository.findPhotoByProductId(id);
        List<GetPhotoDTO> getPhotoDTOS = new ArrayList<>();
        for (Photo photo : photos) {
            GetPhotoDTO getPhotoDTO = new GetPhotoDTO();
            getPhotoDTO.setId(photo.getId());
            getPhotoDTO.setProductId(photo.getProduct().getId());
            getPhotoDTO.setPhotolink(photo.getPhotoLink());
            getPhotoDTOS.add(getPhotoDTO);
        }
        return getPhotoDTOS;
    }

    @Transactional
    public void deleteAllProductPhotos(long id) {
        List<Photo> photos = photoRepository.findPhotoByProductId(id);
        for (Photo photo : photos) {
            mediaFeignClient.deleteMedia(Collections.singletonList(photo.getPhotoLink()).toArray(new String[0]));
            photoRepository.removePhotoById(photo.getId());
        }
    }

    @Transactional
    public void deletePhotosById(Long[] ids) {
        for (Long id : ids) {
            if (photoRepository.existsById(id)) {
                mediaFeignClient.deleteMedia(Collections.singletonList(photoRepository.findPhotoById(id).getPhotoLink()).toArray(new String[0]));
                photoRepository.removePhotoById(id);
            } else {
                throw new PhotoNotExistsException("Photo not found");
            }
        }
    }
}

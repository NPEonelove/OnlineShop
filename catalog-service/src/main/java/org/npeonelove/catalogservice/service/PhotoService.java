package org.npeonelove.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.npeonelove.catalogservice.client.MediaClient;
import org.npeonelove.catalogservice.dto.photo.GetPhoto;
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
    private final MediaClient mediaClient;

    @Transactional
    public void saveProductPhotos(long id, MultipartFile[] files) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotExistsException("Product not found");
        }
        List<String> links = mediaClient.uploadMedia("catalog/productPhotos", files);


        for (String link : links) {
            Photo photo = new Photo();
            photo.setProduct(product.get());
            photo.setPhotoLink(link);
            photoRepository.save(photo);
        }
    }

    public List<GetPhoto> getProductPhotos(long id) {
        List<Photo> photos = photoRepository.findPhotoByProductId(id);
        List<GetPhoto> getPhotos = new ArrayList<>();
        for (Photo photo : photos) {
            GetPhoto getPhoto = new GetPhoto();
            getPhoto.setId(photo.getId());
            getPhoto.setProductId(photo.getProduct().getId());
            getPhoto.setPhotolink(photo.getPhotoLink());
            getPhotos.add(getPhoto);
        }
        return getPhotos;
    }

    @Transactional
    public void deleteAllProductPhotos(long id) {
        List<Photo> photos = photoRepository.findPhotoByProductId(id);
        for (Photo photo : photos) {
            mediaClient.deleteMedia(Collections.singletonList(photo.getPhotoLink()).toArray(new String[0]));
            photoRepository.removePhotoById(photo.getId());
        }
    }

    @Transactional
    public void deletePhotosById(Long[] ids) {
        for (Long id : ids) {
            if (photoRepository.existsById(id)) {
                mediaClient.deleteMedia(Collections.singletonList(photoRepository.findPhotoById(id).getPhotoLink()).toArray(new String[0]));
                photoRepository.removePhotoById(id);
            } else {
                throw new PhotoNotExistsException("Photo not found");
            }
        }
    }
}

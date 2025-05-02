package org.npeonelove.catalogservice.repository;

import org.npeonelove.catalogservice.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findPhotoByProductId(Long id);

    void removePhotoById(Long id);

    Photo findPhotoById(Long id);
}

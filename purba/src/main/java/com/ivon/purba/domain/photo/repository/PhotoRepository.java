package com.ivon.purba.domain.photo.repository;

import com.ivon.purba.domain.photo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Object> findByUrl(String photoUrl);
}

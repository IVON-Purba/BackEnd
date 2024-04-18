package com.ivon.purba.domain.content.repository;

import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.content.entity.ContentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
    Optional<ContentType> findByName(String typeName);
}

package com.Images.Image;

import com.Images.Image.models.Image;
import com.Images.Image.models.ImageIdAndName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

    @Query("SELECT i.name as name, i.id as id FROM Image i JOIN i.tags t WHERE (" +
            "t IN :providedTags AND (i.username = :userName OR i.isPublic = true)) ")
    List<ImageIdAndName> findImageByTags(@Param("providedTags") String[] Tags,
                                         @Param("userName") String username);

    Optional<Image> findByName(String name);

    boolean existsByName(String name);
}

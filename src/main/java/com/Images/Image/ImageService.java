package com.Images.Image;

import com.Images.Image.models.Image;
import com.Images.Image.models.ImageIdAndName;
import com.Images.Image.models.Response;
import com.Images.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository ImageRepo;
    @Autowired
    private UserService UserService;

    public Response getImageById(Long id, String tokenValue) {
        Optional<Image> imageOption = ImageRepo.findById(id);
        if (!imageOption.isPresent()) {
            return new Response("Image with specified ID does not exist", HttpStatus.BAD_REQUEST);
        }

        Image image = imageOption.get();
        if ( image.isPublic()) {
            return new Response(image.getImageBytes(), HttpStatus.OK);
        }
        if (UserService.authenticate(tokenValue, image.getUsername())) {
            return new Response(image.getImageBytes(), HttpStatus.OK);
        }
        return new Response("User has invalid permissions to access this image", HttpStatus.UNAUTHORIZED);
    }

    public Response getImageByName(String name, String tokenValue) {
        Optional<Image> imageOption = ImageRepo.findByName(name);
        if (!imageOption.isPresent()) {
            return new Response("Image with specified ID does not exist", HttpStatus.BAD_REQUEST);
        }

        Image image = imageOption.get();
        if ( image.isPublic()) {
            return new Response(image.getImageBytes(), HttpStatus.OK);
        }
        if (UserService.authenticate(tokenValue, image.getUsername())) {
            return new Response(image.getImageBytes(), HttpStatus.OK);
        }
        return new Response("User has invalid permissions to access this image", HttpStatus.UNAUTHORIZED);
    }

    public boolean doesImageExist(String name) {
        if ( ImageRepo.existsByName(name) ) {
            return true;
        }
        return false;
    }

    public Long saveImage(Image image, byte[] bytes, String username) {
        image.setImageBytes(bytes);
        image.setUsername(username);
        ImageRepo.save(image);
        return image.getId();
    }

    public List<ImageIdAndName> findImagesByTags(String[] tags, String username) {
        List<ImageIdAndName> images = ImageRepo.findImageByTags(tags, username);
        return images;
    }

}

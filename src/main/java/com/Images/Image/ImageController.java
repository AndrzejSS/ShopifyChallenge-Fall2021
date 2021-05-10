package com.Images.Image;

import com.Images.Image.models.Image;
import com.Images.Image.models.ImageIdAndName;
import com.Images.Image.models.Response;
import com.Images.User.TokenRepository;
import com.Images.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private ImageRepository ImageRepo;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/download/id/{imageID}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Object> downloadImageByID(@PathVariable("imageID") Long id,
                                                    @RequestHeader("Token") String tokenValue ) {
        Response resp = imageService.getImageById(id, tokenValue);
        return new ResponseEntity<>(resp.getImage(),resp.getStatus());
    }

    @GetMapping(value = "/download/name/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Object> downloadImageByName(@PathVariable("imageName") String name,
                                                      @RequestHeader("Token") String tokenValue) {
        Response resp = imageService.getImageByName(name, tokenValue);
        return new ResponseEntity<>(resp.getImage(),resp.getStatus());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Object> uploadImage( @RequestPart(value = "file", required = true) MultipartFile file,
                                               @RequestPart("model") Image image,
                                               @RequestHeader("Token") String tokenValue) throws IOException {

        if (imageService.doesImageExist(image.getName()) ) {
            return new ResponseEntity<>("Error: File with this name already exists", HttpStatus.BAD_REQUEST);
        }
        String tokenUsername = userService.getUsenameFromToken(tokenValue);
        if ( tokenUsername.isEmpty()) {
            return new ResponseEntity<>("INVALID TOKEN ", HttpStatus.UNAUTHORIZED);
        }
        Long id = imageService.saveImage(image, file.getBytes(), tokenUsername);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/search/desc")
    @ResponseBody
    public ResponseEntity<List<ImageIdAndName>> findImageByTag(@RequestParam(value = "tags", required = true) String[] tags,
                                                               @RequestHeader("Token") String tokenValue) {
        String tokenUsername = userService.getUsenameFromToken(tokenValue);
        if ( tokenUsername.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        List<ImageIdAndName> images = imageService.findImagesByTags(tags, tokenUsername);
        return new ResponseEntity<List<ImageIdAndName>>(images, HttpStatus.OK);
    }
}

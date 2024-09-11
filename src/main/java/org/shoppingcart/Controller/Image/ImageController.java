package org.shoppingcart.Controller.Image;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.Model.DTO.ImageDTO;
import org.shoppingcart.Model.Entity.ImageEntity;
import org.shoppingcart.Model.Response.APIResponse;
import org.shoppingcart.Service.Image.IImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @PostMapping(value = "/uploads")
    public ResponseEntity<APIResponse> saveImage(@RequestParam List<MultipartFile> files,
                                                 @RequestParam String productId) {
        try {
            List<ImageDTO> list = imageService.saveImages(files, productId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse("Upload success!", list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Upload fail!", e.getMessage()));
        }
    }

    @GetMapping(value = "/download/{imageId}")
    public ResponseEntity<Resource> downloadImageURL(@PathVariable(value = "imageId") String imageId) throws SQLException {
        ImageEntity image = imageService.getImageEntityById(imageId);
        ByteArrayResource byteArrayResource = new ByteArrayResource(image.getImage().getBytes(1L, (int) image.getImage().length()));
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(image.getFiletype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                .body(byteArrayResource);
        // Thiết lập header có content-type là contentDisposition và value là attachment sẽ báo tải về file dưới dạng file đính kèm
    }

    @PutMapping(value = "/{imageId}")
    public ResponseEntity<APIResponse> updateImage(@PathVariable(value = "imageId") String id,
                                                   @RequestBody MultipartFile file) {
        try {
            ImageDTO image = imageService.getImageById(id);
            if(image != null) {
                imageService.updateImage(file, id);
                return ResponseEntity.status(HttpStatus.OK).body(new APIResponse("Update image success!",
                        imageService.getImageById(id)));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Update image fail!", "id: " + id + " is not exists!"));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse("Update image fail!", exception.getMessage()));
        }
    }
    @DeleteMapping(value = "/{imageId}")
    public ResponseEntity<APIResponse> deleteImage(@PathVariable(value = "imageId") String id) {
        try {
            ImageDTO image = imageService.getImageById(id);
            if(image != null) {
                imageService.deleteImageById(id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new APIResponse("Delete image success!", null));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Delete image fail!", "id: " + id + " is not exists!"));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse("Delete image fail!", exception.getMessage()));
        }
    }
}

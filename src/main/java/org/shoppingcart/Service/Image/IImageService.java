package org.shoppingcart.Service.Image;

import org.shoppingcart.Model.DTO.ImageDTO;
import org.shoppingcart.Model.Entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    ImageDTO getImageById(String id);
    ImageEntity getImageEntityById(String id);
    void deleteImageById(String id);
    List<ImageDTO> saveImages(List<MultipartFile> files, String productId);
    void updateImage(MultipartFile file, String imageId);
}

package org.shoppingcart.Service.Image;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.Convertor.ImageConvertor;
import org.shoppingcart.Exceptions.ResourceNotFoundException;
import org.shoppingcart.Model.DTO.ImageDTO;
import org.shoppingcart.Model.Entity.ImageEntity;
import org.shoppingcart.Model.Entity.ProductEntity;
import org.shoppingcart.Repository.Image.ImageRepository;
import org.shoppingcart.Repository.Product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ImageConvertor imageConvertor;
    @Override
    public ImageDTO getImageById(String id) {
        return imageConvertor.entityToDTO(getImageEntityById(id));
    }

    @Override
    public ImageEntity getImageEntityById(String id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found!"));
    }

    @Override
    public void deleteImageById(String id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Image not found!");
                        });
    }

    @Override
    public List<ImageDTO> saveImages(List<MultipartFile> files, String productId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        List<ImageDTO> imageDTOList = new ArrayList<>();
        try {
            for(MultipartFile file : files) {
                ImageEntity image = imageConvertor.multiPartFileToEntity(file);
                image.setProduct(product);
                ImageEntity savedImage = imageRepository.save(image);
                savedImage.setDownloadURL("/images/download/" + savedImage.getId());
                imageRepository.save(savedImage);
                ImageDTO imageDTO = imageConvertor.entityToDTO(savedImage);
                imageDTOList.add(imageDTO);
            }
        } catch (IOException | SQLException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return imageDTOList;
    }

    @Override
    public void updateImage(MultipartFile file, String imageId) {
        try {
            ImageEntity image = imageConvertor.multiPartFileToEntity(file);
            imageRepository.save(image);
        } catch(IOException | SQLException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}

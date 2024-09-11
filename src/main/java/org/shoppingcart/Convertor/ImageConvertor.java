package org.shoppingcart.Convertor;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.shoppingcart.Model.DTO.ImageDTO;
import org.shoppingcart.Model.Entity.ImageEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;

@Component
public class ImageConvertor {
    public ImageEntity multiPartFileToEntity(MultipartFile file) throws IOException, SQLException {
        return ImageEntity.builder()
                .filename(file.getOriginalFilename())
                .filetype(file.getContentType())
                .image(new SerialBlob(file.getBytes()))
                .build();
    }

    public ImageDTO entityToDTO(ImageEntity image) {
        return ImageDTO.builder()
                .imageId(image.getId())
                .downloadURL(image.getDownloadURL())
                .imageName(image.getFilename())
                .build();
    }
}

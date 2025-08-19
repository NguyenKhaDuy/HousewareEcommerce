package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Model.DTO.ImageProductDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.ImageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageProductService {
    Page<ImageProductDTO> getAll(Integer pageNo);
    MessageDTO getById(Long id);
    MessageDTO createImage(ImageRequest imageRequest);
    MessageDTO deleteImage(Long id);

    MessageDTO updateImage(Long imageId, MultipartFile newImage);
}

package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.*;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.ProductDTO;
import com.example.housewareecommerce.Model.Request.ProductRequest;
import com.example.housewareecommerce.Repository.CategoryRepository;
import com.example.housewareecommerce.Repository.EvaluateRepository;
import com.example.housewareecommerce.Repository.ProductRepository;
import com.example.housewareecommerce.Repository.StatusRepository;
import com.example.housewareecommerce.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
@SuppressWarnings("all")
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EvaluateRepository evaluateRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        List<ProductEntity> products = productRepository.findByCategoryIdWithImages(categoryId);

        if (products.isEmpty()) {
            return new ArrayList<>();
        }

        return products.stream()
                .map(this::convertToListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductEntity getProductDetailById(Long productId) {
        ProductEntity product = productRepository.findByIdWithBasicRelations(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId));

        ProductEntity productWithImages = productRepository.findByIdWithImages(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy product Images = " + productId));
        product.setImageEntities(productWithImages.getImageEntities());

        ProductEntity productWithEvaluates = productRepository.findByIdWithEvaluates(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy evalute = " + productId));
        product.setEvaluateEntities(productWithEvaluates.getEvaluateEntities());

        if (product.getImageEntities() != null) {
            product.getImageEntities().forEach(img -> {
                if (img.getImageUrl() != null && img.getImageUrl().length > 0) {
                    try {
                        String base64Image;
                        if (isAlreadyBase64(img.getImageUrl())) {
                            base64Image = new String(img.getImageUrl(), StandardCharsets.UTF_8);
                        } else {
                            base64Image = Base64.getEncoder().encodeToString(img.getImageUrl());
                        }

                        // Kiểm tra kích thước base64
                        if (isBase64TooLarge(base64Image)) {
                            // Nén ảnh nếu quá lớn
                            base64Image = compressAndEncodeImage(img.getImageUrl());
                        }

                        img.setBase64Image(base64Image);
                    } catch (Exception e) {
                        img.setBase64Image(null);
                    }
                }
            });
        }

        return product;
    }

    private boolean isAlreadyBase64(byte[] data) {
        try {
            String str = new String(data, StandardCharsets.UTF_8);
            // Kiểm tra format base64
            return str.matches("^[A-Za-z0-9+/]*={0,2}$") && str.length() % 4 == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isBase64TooLarge(String base64) {
        // Giới hạn ~50KB cho base64 string (tương đương ~37KB binary)
        return base64.length() > 50000;
    }

    private String compressAndEncodeImage(byte[] originalImageData) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(originalImageData);
            BufferedImage originalImage = ImageIO.read(bis);

            if (originalImage == null) {
                return null;
            }

            // Resize nếu quá lớn
            BufferedImage resizedImage = resizeImage(originalImage, 800, 600);

            // Nén với quality 70%
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.7f);

            writer.write(null, new IIOImage(resizedImage, null, null), param);
            writer.dispose();
            ios.close();

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            return null;
        }
    }

    private BufferedImage resizeImage(BufferedImage original, int maxWidth, int maxHeight) {
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        // Tính tỷ lệ scale
        double scaleWidth = (double) maxWidth / originalWidth;
        double scaleHeight = (double) maxHeight / originalHeight;
        double scale = Math.min(scaleWidth, scaleHeight);

        if (scale >= 1.0) {
            return original; // Không cần resize
        }

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(original, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return resized;
    }


    private ProductDTO convertToListDTO(ProductEntity product) {
        List<String> imageList = new ArrayList<>();

        if (product.getImageEntities() != null && !product.getImageEntities().isEmpty()) {
            imageList = product.getImageEntities()
                    .stream()
                    .filter(img -> img.getImageUrl() != null && img.getImageUrl().length > 0)
                    .map(img -> Base64.getEncoder().encodeToString(img.getImageUrl()))
                    .collect(Collectors.toList());
        }

        return new ProductDTO(
                product.getId(),
                product.getNameProduct(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                imageList,
                product.getStatusEntity().getStatusCode()
        );
    }

    @Override
    public Page<ProductDTO> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);
        List<ProductDTO> results = new ArrayList<>();
        for(ProductEntity productEntity : productEntities){
            ProductDTO productDTO = new ProductDTO();
            modelMapper.map(productEntity, productDTO);
            productDTO.setCategoryName(productEntity.getCategoryEntity().getNameCategory());
            productDTO.setStatusCode(productEntity.getStatusEntity().getStatusCode());
            List<byte[]> images = new ArrayList<>();
            for (ImageEntity imageEntity : productEntity.getImageEntities()) {
                byte[] imageBytes = imageEntity.getImageUrl(); // BLOB trong DB
                if (imageBytes != null && imageBytes.length > 0) {
                    images.add(imageBytes);
                }
            }
            productDTO.setImages(images);
            results.add(productDTO);
        }
        return new PageImpl<>(results, productEntities.getPageable(), productEntities.getTotalElements());
    }

    @Override
    public MessageDTO findById(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            ProductEntity productEntity = productRepository.findById(id).get();
            Integer evaluateRating = averageRating(id);
            ProductDTO productDTO = new ProductDTO();
            modelMapper.map(productEntity, productDTO);
            productDTO.setCategoryName(productEntity.getCategoryEntity().getNameCategory());
            productDTO.setStatusCode(productEntity.getStatusEntity().getStatusCode());
            List<byte[]> images = new ArrayList<>();
            for (ImageEntity imageEntity : productEntity.getImageEntities()) {
                byte[] imageBytes = imageEntity.getImageUrl(); // BLOB trong DB
                if (imageBytes != null && imageBytes.length > 0) {
                    images.add(imageBytes);
                }
            }
            //productDTO.setEvaluateRating(evaluateRating);
            productDTO.setImages(images);
            messageDTO.setMessage("Sản phẩm tồn tại");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(productDTO);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy sản phẩm");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    public Integer averageRating(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).get();
        List<EvaluateEntity> evaluateEntities = evaluateRepository.findByProductEntity(productEntity);
        Integer sumStar = 0;
        for (EvaluateEntity it : evaluateEntities){
            sumStar += it.getStar();
        }
        Integer result = sumStar / evaluateEntities.size();
        return result;
    }

    @Override
    public MessageDTO createProduct(ProductRequest productRequest) {
        ProductEntity productEntity = new ProductEntity();
        MessageDTO messageDTO = new MessageDTO();
        modelMapper.map(productRequest, productEntity);
        try{
            StatusEntity statusEntity  = statusRepository.findById(productRequest.getStatusCode()).get();
            CategoryEntity categoryEntity = categoryRepository.findById(productRequest.getCategoryCode()).get();
            productEntity.setCategoryEntity(categoryEntity);
            productEntity.setStatusEntity(statusEntity);
            productEntity.setCreated(LocalDateTime.now());
            if (productRequest.getImages() != null && !productRequest.getImages().isEmpty()) {
                for (MultipartFile file : productRequest.getImages()) {
                    try {
                        ImageEntity imageEntity = new ImageEntity();
                        imageEntity.setImageUrl(file.getBytes());
                        imageEntity.setProductEntity(productEntity);
                        productEntity.getImageEntities().add(imageEntity);
                    } catch (IOException e) {
                        messageDTO.setMessage("Lỗi xử lý ảnh");
                        messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
                        messageDTO.setData(null);
                        return messageDTO;
                    }
                }
            }
            ProductEntity check = productRepository.save(productEntity);
            if (check == null) {
                messageDTO.setMessage("Thêm sản phảm không thành công");
                messageDTO.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                messageDTO.setData(null);
            }else{
                ProductDTO productDTO = new ProductDTO();
                modelMapper.map(productEntity, productDTO);
                productDTO.setCategoryName(productEntity.getCategoryEntity().getNameCategory());
                productDTO.setStatusCode(productEntity.getStatusEntity().getStatusCode());
                List<byte[]> images = new ArrayList<>();
                for (ImageEntity imageEntity : productEntity.getImageEntities()) {
                    byte[] imageBytes = imageEntity.getImageUrl(); // BLOB trong DB
                    if (imageBytes != null && imageBytes.length > 0) {
                        images.add(imageBytes);
                    }
                }
                productDTO.setImages(images);;
                messageDTO.setMessage("Tạo sản phẩm thành công");
                messageDTO.setHttpStatus(HttpStatus.OK);
                messageDTO.setData(productDTO);
            }
        }catch (Exception e){
            messageDTO.setMessage("Thêm sản phảm không thành công");
            messageDTO.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messageDTO.setData(null);
        }

        return messageDTO;
    }

    @Override
    public MessageDTO updateProduct(ProductRequest productRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            ProductEntity productEntity = productRepository.findById(productRequest.getId()).get();

            modelMapper.map(productRequest, productEntity);
            StatusEntity statusEntity  = statusRepository.findById(productRequest.getStatusCode()).get();
            CategoryEntity categoryEntity = categoryRepository.findById(productRequest.getCategoryCode()).get();
            productEntity.setCategoryEntity(categoryEntity);
            productEntity.setStatusEntity(statusEntity);
            try{
                ProductEntity product = productRepository.save(productEntity);

                ProductDTO productDTO = new ProductDTO();
                modelMapper.map(product, productDTO);
                productDTO.setCategoryName(product.getCategoryEntity().getNameCategory());
                productDTO.setStatusCode(product.getStatusEntity().getStatusCode());
                List<byte[]> images = new ArrayList<>();
                for (ImageEntity imageEntity : product.getImageEntities()) {
                    byte[] imageBytes = imageEntity.getImageUrl(); // BLOB trong DB
                    if (imageBytes != null && imageBytes.length > 0) {
                        images.add(imageBytes);
                    }
                }
                productDTO.setImages(images);
                messageDTO.setMessage("Cập nhật thành công");
                messageDTO.setHttpStatus(HttpStatus.OK);
                messageDTO.setData(productDTO);
            }catch (Exception e){
                messageDTO.setMessage("Cập nhật không thành công");
                messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
                messageDTO.setData(null);
            }

        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy sản phẩm");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO deleteProduct(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            ProductEntity productEntity = productRepository.findById(id).get();
            try{
                productRepository.deleteById(id);
                messageDTO.setMessage("Xóa thành công sản phẩm");
                messageDTO.setHttpStatus(HttpStatus.OK);
                messageDTO.setData(null);
            }catch (Exception e){
                messageDTO.setMessage("Xóa không thành công");
                messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
                messageDTO.setData(null);
            }

        }catch(NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy sản phẩm");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public Page<ProductDTO> searchProduct(Integer pageNo, String nameProduct) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Page<ProductEntity> productEntities = productRepository.searchProduct(pageable, nameProduct);

        List<ProductDTO> results = new ArrayList<>();
        for(ProductEntity productEntity : productEntities){
            ProductDTO productDTO = new ProductDTO();
            modelMapper.map(productEntity, productDTO);
            productDTO.setCategoryName(productEntity.getCategoryEntity().getNameCategory());
            productDTO.setStatusCode(productEntity.getStatusEntity().getStatusCode());
            List<byte[]> images = new ArrayList<>();
            for (ImageEntity imageEntity : productEntity.getImageEntities()) {
                byte[] imageBytes = imageEntity.getImageUrl(); // BLOB trong DB
                if (imageBytes != null && imageBytes.length > 0) {
                    images.add(imageBytes);
                }
            }
            productDTO.setImages(images);
            results.add(productDTO);
        }

        return new PageImpl<>(results, productEntities.getPageable(), productEntities.getTotalElements());
    }
}

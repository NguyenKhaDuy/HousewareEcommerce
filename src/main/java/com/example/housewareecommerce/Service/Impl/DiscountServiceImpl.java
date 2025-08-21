package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.DiscountEntity;
import com.example.housewareecommerce.Entity.StatusEntity;
import com.example.housewareecommerce.Model.DTO.DiscountDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.Request.DiscountRequest;
import com.example.housewareecommerce.Repository.DiscoutRepository;
import com.example.housewareecommerce.Repository.StatusRepository;
import com.example.housewareecommerce.Service.DiscoutService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscoutService {
    @Autowired
    DiscoutRepository discoutRepository;

    @Autowired
    StatusRepository statusRepository;

    private static final Long ACTIVE_STATUS_ID = 38L;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public Page<DiscountDTO> getAll(Integer pageOne) {
        Pageable pageable = PageRequest.of(pageOne - 1, 5);
        Page<DiscountEntity> discountEntities = discoutRepository.findAll(pageable);
        List<DiscountDTO> results = new ArrayList<>();
        for(DiscountEntity discountEntity : discountEntities){
            DiscountDTO discountDTO = new DiscountDTO();
            modelMapper.map(discountEntity, discountDTO);
            discountDTO.setStatusCode(discountEntity.getStatusEntity().getStatusCode());
            results.add(discountDTO);
        }
        return new PageImpl<>(results, discountEntities.getPageable(), discountEntities.getTotalElements());
    }

    @Override
    public MessageDTO getById(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            DiscountEntity discountEntity = discoutRepository.findById(id).get();
            DiscountDTO discountDTO = new DiscountDTO();
            modelMapper.map(discountEntity, discountDTO);
            discountDTO.setStatusCode(discountEntity.getStatusEntity().getStatusCode());

            messageDTO.setMessage("Success");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(discountDTO);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy mã khuyến mãi");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO createDisount(DiscountRequest discountRequest) {
        MessageDTO messageDTO = new MessageDTO();
        DiscountEntity discountEntity = new DiscountEntity();

        try {
            StatusEntity statusEntity = statusRepository.findById(discountRequest.getStatusId()).get();
            modelMapper.map(discountRequest, discountEntity);
            discountEntity.setStatusEntity(statusEntity);
            discountEntity.setDateStart(discountRequest.getDateStart());
            discountEntity.setDateEnd(discountRequest.getDateEnd());
            discountEntity.setCreated(LocalDateTime.now());
            DiscountEntity discount = discoutRepository.save(discountEntity);
            DiscountDTO discountDTO = new DiscountDTO();
            modelMapper.map(discount, discountDTO);
            discountDTO.setStatusCode(discount.getStatusEntity().getStatusCode());

            messageDTO.setMessage("Thêm thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(discountDTO);

        }catch (Exception e){
            messageDTO.setMessage("Thêm không thành công");
            messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            messageDTO.setData(null);
        }

        return messageDTO;
    }

    @Override
    public MessageDTO updateDiscount(DiscountRequest discountRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            DiscountEntity discountEntity = discoutRepository.findById(discountRequest.getId()).get();
            try {
                StatusEntity statusEntity = statusRepository.findById(discountRequest.getStatusId()).get();
                modelMapper.map(discountRequest, discountEntity);
                discountEntity.setStatusEntity(statusEntity);

                DiscountEntity discount = discoutRepository.save(discountEntity);
                DiscountDTO discountDTO = new DiscountDTO();
                modelMapper.map(discount, discountDTO);
                discountDTO.setStatusCode(discount.getStatusEntity().getStatusCode());

                messageDTO.setMessage("Cập nhật thành công");
                messageDTO.setHttpStatus(HttpStatus.OK);
                messageDTO.setData(discountDTO);
            }catch (NoSuchElementException e){
                messageDTO.setMessage("Cập nhật không thành công");
                messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
                messageDTO.setData(null);
            }
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy mã khuyến mãi");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO deleteDiscount(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            DiscountEntity discountEntity = discoutRepository.findById(id).get();
            discoutRepository.deleteById(id);
            messageDTO.setMessage("Xóa thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(null);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy mã khuyến mãi");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public Optional<Float> validateDiscountCode(String discountCode) {
        return discoutRepository.findPercentDiscountByValidCode(discountCode);
    }
}

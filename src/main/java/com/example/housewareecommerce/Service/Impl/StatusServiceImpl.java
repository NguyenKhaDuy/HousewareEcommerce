package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.StatusEntity;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.StatusDTO;
import com.example.housewareecommerce.Model.Request.StatusRequest;
import com.example.housewareecommerce.Repository.StatusRepository;
import com.example.housewareecommerce.Service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<StatusEntity> getAllStatus() {
        return statusRepository.findAll();
    }

    @Override
    public MessageDTO getById(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            StatusEntity statusEntity = statusRepository.findById(id).get();
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setId(statusEntity.getId());
            statusDTO.setStatusCode(statusEntity.getStatusCode());

            messageDTO.setMessage("Success");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(statusDTO);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Trạng thái không tồn tại");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO createStatus(StatusRequest statusRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            StatusEntity statusEntity = new StatusEntity();
            statusEntity.setStatusCode(statusRequest.getStatusCode());

            StatusEntity status = statusRepository.save(statusEntity);
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setId(status.getId());
            statusDTO.setStatusCode(status.getStatusCode());

            messageDTO.setMessage("Thêm thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(statusDTO);
        }catch (Exception e){
            messageDTO.setMessage("Thêm không thành công");
            messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO updateStatus(StatusRequest statusRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            StatusEntity statusEntity = statusRepository.findById(statusRequest.getId()).get();
            statusEntity.setStatusCode(statusRequest.getStatusCode());
            statusRepository.save(statusEntity);

            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setId(statusEntity.getId());
            statusDTO.setStatusCode(statusEntity.getStatusCode());

            messageDTO.setMessage("Cập nhật thành công trạng thái");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(statusDTO);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy trạng thái");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO deleteStatus(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            StatusEntity statusEntity = statusRepository.findById(id).get();
            statusRepository.deleteById(id);

            messageDTO.setMessage("Xóa thành công trạng thái");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(null);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy trạng thái");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }
}

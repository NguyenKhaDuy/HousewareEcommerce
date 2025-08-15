package com.example.housewareecommerce.Service.Impl;

import com.example.housewareecommerce.Entity.*;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.OrdersDTO;
import com.example.housewareecommerce.Model.DTO.OrdersDetailDTO;
import com.example.housewareecommerce.Model.Request.OrdersDetailsRequest;
import com.example.housewareecommerce.Model.Request.OrdersRequest;
import com.example.housewareecommerce.Repository.*;
import com.example.housewareecommerce.Service.OrdersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    DiscoutRepository discoutRepository;
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public Page<OrdersDTO> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Page<OrderEntity> orderEntities = ordersRepository.findAll(pageable);
        List<OrdersDTO> results = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities){
            OrdersDTO ordersDTO = new OrdersDTO();
            modelMapper.map(orderEntity,ordersDTO);

            ordersDTO.setPaymentMethod(orderEntity.getPaymentMethodEntity().getNameMethod());
            ordersDTO.setUserName(orderEntity.getUserEntity().getName());
            ordersDTO.setStatusCode(orderEntity.getStatusEntity().getStatusCode());
            ordersDTO.setNameDiscount(orderEntity.getDiscountEntity().getNameDiscount());
            ordersDTO.setPercentDiscount(orderEntity.getDiscountEntity().getPercentDiscount());

            List<OrdersDetailDTO> ordersDetailDTOS = new ArrayList<>();
            for (OrderDetailsEntity orderDetails : orderEntity.getOrderDetails()){
                OrdersDetailDTO ordersDetailDTO = new OrdersDetailDTO();
                ordersDetailDTO.setId(orderDetails.getId());
                ordersDetailDTO.setNameProduct(orderDetails.getProductEntity().getNameProduct());
                ordersDetailDTO.setQuality(orderDetails.getQuality());
                ordersDetailDTO.setPriceQuotation(orderDetails.getPriceQuotation());
                ordersDetailDTO.setTotalAmount(orderDetails.getTotalAmount());
                ordersDetailDTOS.add(ordersDetailDTO);
            }

            ordersDTO.setOrdersDetailDTOS(ordersDetailDTOS);

            results.add(ordersDTO);
        }

        return new PageImpl<>(results, orderEntities.getPageable(), orderEntities.getTotalElements());
    }

    @Override
    public MessageDTO getById(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            OrderEntity orderEntity = ordersRepository.findById(id).get();
            OrdersDTO ordersDTO = new OrdersDTO();
            modelMapper.map(orderEntity,ordersDTO);
            ordersDTO.setPaymentMethod(orderEntity.getPaymentMethodEntity().getNameMethod());
            ordersDTO.setUserName(orderEntity.getUserEntity().getName());
            ordersDTO.setStatusCode(orderEntity.getStatusEntity().getStatusCode());
            ordersDTO.setNameDiscount(orderEntity.getDiscountEntity().getNameDiscount());
            ordersDTO.setPercentDiscount(orderEntity.getDiscountEntity().getPercentDiscount());
            List<OrdersDetailDTO> ordersDetailDTOS = new ArrayList<>();
            for (OrderDetailsEntity orderDetails : orderEntity.getOrderDetails()){
                OrdersDetailDTO ordersDetailDTO = new OrdersDetailDTO();
                ordersDetailDTO.setId(orderDetails.getId());
                ordersDetailDTO.setNameProduct(orderDetails.getProductEntity().getNameProduct());
                ordersDetailDTO.setQuality(orderDetails.getQuality());
                ordersDetailDTO.setPriceQuotation(orderDetails.getPriceQuotation());
                ordersDetailDTO.setTotalAmount(orderDetails.getTotalAmount());
                ordersDetailDTOS.add(ordersDetailDTO);
            }
            ordersDTO.setOrdersDetailDTOS(ordersDetailDTOS);
            messageDTO.setMessage("Success");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(ordersDTO);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy đơn hàng");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO createOrders(OrdersRequest ordersRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try {
            OrderEntity orderEntity = new OrderEntity();
            UserEntity userEntity = null;
            StatusEntity statusEntity = null;
            PaymentMethodEntity paymentMethodEntity = null;
            DiscountEntity discountEntity = null;
            try {
                userEntity = userRepository.findById(ordersRequest.getUserId()).get();
                statusEntity = statusRepository.findById(ordersRequest.getStatusId()).get();
                paymentMethodEntity = paymentMethodRepository.findById(ordersRequest.getPaymentMethodId()).get();
                discountEntity = discoutRepository.findByDiscounCode(ordersRequest.getDiscoutCode());
            }catch (NoSuchElementException e){
                messageDTO.setMessage("Thêm không thành công");
                messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
                messageDTO.setData(null);
                return messageDTO;
            }
//            List<OrderDetailsEntity> orderDetailsEntities = new ArrayList<>();
            for (OrdersDetailsRequest it : ordersRequest.getOrdersDetailsRequests()){
                OrderDetailsEntity orderDetails = new OrderDetailsEntity();
                orderDetails.setQuality(it.getQuality());
                orderDetails.setTotalAmount(it.getTotalAmount());
                orderDetails.setPriceQuotation(it.getPriceQuotation());
                ProductEntity productEntity = productRepository.findById(it.getProductId()).get();
                orderDetails.setProductEntity(productEntity);
                orderEntity.getOrderDetails().add(orderDetails);
                orderDetails.setOrderEntity(orderEntity);
            }
            orderEntity.setDateOrder(LocalDate.now());
            orderEntity.setTotalPrice(ordersRequest.getTotalPrice());
            orderEntity.setNote(ordersRequest.getNote());
            orderEntity.setUserEntity(userEntity);
            orderEntity.setStatusEntity(statusEntity);
            orderEntity.setPaymentMethodEntity(paymentMethodEntity);
            orderEntity.setDiscountEntity(discountEntity);

            OrderEntity order = ordersRepository.save(orderEntity);

            OrdersDTO ordersDTO = new OrdersDTO();
            modelMapper.map(orderEntity,ordersDTO);
            ordersDTO.setPaymentMethod(order.getPaymentMethodEntity().getNameMethod());
            ordersDTO.setUserName(order.getUserEntity().getName());
            ordersDTO.setStatusCode(order.getStatusEntity().getStatusCode());
            ordersDTO.setNameDiscount(order.getDiscountEntity().getNameDiscount());
            ordersDTO.setPercentDiscount(order.getDiscountEntity().getPercentDiscount());
            List<OrdersDetailDTO> ordersDetailDTOS = new ArrayList<>();
            for (OrderDetailsEntity orderDetails : order.getOrderDetails()){
                OrdersDetailDTO ordersDetailDTO = new OrdersDetailDTO();
                ordersDetailDTO.setId(orderDetails.getId());
                ordersDetailDTO.setNameProduct(orderDetails.getProductEntity().getNameProduct());
                ordersDetailDTO.setQuality(orderDetails.getQuality());
                ordersDetailDTO.setPriceQuotation(orderDetails.getPriceQuotation());
                ordersDetailDTO.setTotalAmount(orderDetails.getTotalAmount());
                ordersDetailDTOS.add(ordersDetailDTO);
            }
            ordersDTO.setOrdersDetailDTOS(ordersDetailDTOS);

            messageDTO.setMessage("Thêm thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(ordersDTO);
        }catch (Exception e){
            messageDTO.setMessage("Thêm không thành công");
            messageDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO updateStatusOrders(OrdersRequest ordersRequest) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            OrderEntity orderEntity = ordersRepository.findById(ordersRequest.getId()).get();
            StatusEntity statusEntity = statusRepository.findById(ordersRequest.getStatusId()).get();
            orderEntity.setStatusEntity(statusEntity);
            ordersRepository.save(orderEntity);

            OrdersDTO ordersDTO = new OrdersDTO();
            modelMapper.map(orderEntity,ordersDTO);
            ordersDTO.setPaymentMethod(orderEntity.getPaymentMethodEntity().getNameMethod());
            ordersDTO.setUserName(orderEntity.getUserEntity().getName());
            ordersDTO.setStatusCode(orderEntity.getStatusEntity().getStatusCode());
            ordersDTO.setNameDiscount(orderEntity.getDiscountEntity().getNameDiscount());
            ordersDTO.setPercentDiscount(orderEntity.getDiscountEntity().getPercentDiscount());
            List<OrdersDetailDTO> ordersDetailDTOS = new ArrayList<>();
            for (OrderDetailsEntity orderDetails : orderEntity.getOrderDetails()){
                OrdersDetailDTO ordersDetailDTO = new OrdersDetailDTO();
                ordersDetailDTO.setId(orderDetails.getId());
                ordersDetailDTO.setNameProduct(orderDetails.getProductEntity().getNameProduct());
                ordersDetailDTO.setQuality(orderDetails.getQuality());
                ordersDetailDTO.setPriceQuotation(orderDetails.getPriceQuotation());
                ordersDetailDTO.setTotalAmount(orderDetails.getTotalAmount());
                ordersDetailDTOS.add(ordersDetailDTO);
            }
            ordersDTO.setOrdersDetailDTOS(ordersDetailDTOS);

            messageDTO.setMessage("Cập nhật trạng thái đơn hàng thành công");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(ordersDTO);

        }catch (NoSuchElementException e){
            messageDTO.setMessage("Không tìm thấy đơn hàng");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }

    @Override
    public MessageDTO deleteOrders(Long id) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            OrderEntity orderEntity = ordersRepository.findById(id).get();
            ordersRepository.deleteById(id);
            messageDTO.setMessage("Xóa thành công đơn hàng");
            messageDTO.setHttpStatus(HttpStatus.OK);
            messageDTO.setData(null);
        }catch (NoSuchElementException e){
            messageDTO.setMessage("Đơn hàng không tồn tại");
            messageDTO.setHttpStatus(HttpStatus.NOT_FOUND);
            messageDTO.setData(null);
        }
        return messageDTO;
    }
}

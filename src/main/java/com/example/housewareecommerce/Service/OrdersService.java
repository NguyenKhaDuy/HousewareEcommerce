package com.example.housewareecommerce.Service;

import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.OrdersDTO;
import com.example.housewareecommerce.Model.Request.OrdersRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface OrdersService {
    Page<OrdersDTO> getAll(Integer pageNo);
    MessageDTO getById(Long id);
    MessageDTO createOrders(OrdersRequest ordersRequest);
    MessageDTO updateStatusOrders(OrdersRequest OrdersRequest);
    MessageDTO deleteOrders(Long id);
}

package com.example.housewareecommerce.Controller.Client;

import com.example.housewareecommerce.Model.DTO.ListDTO;
import com.example.housewareecommerce.Model.DTO.MessageDTO;
import com.example.housewareecommerce.Model.DTO.OrdersDTO;
import com.example.housewareecommerce.Model.Request.OrdersRequest;
import com.example.housewareecommerce.Service.OrdersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewOrdersController {

    @Autowired
    OrdersService ordersService;

    @GetMapping(value = "/orders/user")
    public String getOrdersByUser(@RequestParam(name = "page"
                    , defaultValue = "1") Integer pageNo
            , Model model
            , HttpSession session){

        Long userId = (Long) session.getAttribute("userid");

        Page<OrdersDTO> ordersDTOS = ordersService.getAllByUser(userId,pageNo);
        ListDTO<OrdersDTO> listDTO = new ListDTO<>();
        listDTO.setTotalPage(ordersDTOS.getTotalPages());
        listDTO.setCurrentPage(pageNo);
        listDTO.setData(ordersDTOS.getContent());

        model.addAttribute("listDTO", listDTO);

        return "user/user-order";
    }

    @PostMapping(value = "/orders")
    @ResponseBody
    public ResponseEntity<?> createOrders(@RequestBody OrdersRequest ordersRequest){
        MessageDTO messageDTO = ordersService.createOrders(ordersRequest);
        if (messageDTO.getHttpStatus() == HttpStatus.BAD_REQUEST){
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/orders/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteOrders(@PathVariable Long id){
        MessageDTO messageDTO = ordersService.deleteOrders(id);
        if (messageDTO.getHttpStatus() == HttpStatus.NOT_FOUND){
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @GetMapping("/orders/detail/{id}")
    public String getOrderDetail(@PathVariable Long id, Model model) {
        MessageDTO messageDTO = ordersService.getById(id);

        if (messageDTO.getHttpStatus() == HttpStatus.OK) {
            model.addAttribute("order", messageDTO.getData());
            return "user/order-detail";
        } else {
            return "redirect:/orders/user?error=notfound";
        }
    }
}

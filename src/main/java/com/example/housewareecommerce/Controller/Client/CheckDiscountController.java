package com.example.housewareecommerce.Controller.Client;

import com.example.housewareecommerce.Model.Response.DiscountValidationResponse;
import com.example.housewareecommerce.Service.DiscoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class CheckDiscountController {

    @Autowired
    private DiscoutService discoutService;

    @GetMapping("/validate/{discountCode}")
    public ResponseEntity<DiscountValidationResponse> validateDiscount(@PathVariable String discountCode) {
        Optional<Float> percentDiscount = discoutService.validateDiscountCode(discountCode);

        return percentDiscount.map(aFloat -> ResponseEntity.ok(new DiscountValidationResponse(
                true,
                "Discount code is valid",
                discountCode,
                aFloat
        ))).orElseGet(() -> ResponseEntity.ok(new DiscountValidationResponse(
                false,
                "Discount code is invalid or inactive",
                discountCode,
                null
        )));
    }
}

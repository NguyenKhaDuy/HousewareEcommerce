package com.example.housewareecommerce.Config;

import com.example.housewareecommerce.Entity.ProductEntity;
import com.example.housewareecommerce.Model.DTO.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper ModalMapperConfig(){
        ModelMapper modelMapper = new ModelMapper();
        // Bỏ qua field evaluate, tự set sau
        modelMapper.typeMap(ProductEntity.class, ProductDTO.class).addMappings(mapper -> {
            mapper.skip(ProductDTO::setEvaluate);
        });
        return modelMapper;
    }
}

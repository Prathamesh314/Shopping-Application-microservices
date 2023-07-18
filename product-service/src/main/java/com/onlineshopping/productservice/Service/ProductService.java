package com.onlineshopping.productservice.Service;

import com.onlineshopping.productservice.Model.Product;
import com.onlineshopping.productservice.Repository.ProductRepository;
import com.onlineshopping.productservice.dto.ProductRequest;
import com.onlineshopping.productservice.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll().stream().map(this::MapToResponse).toList();

    }

    private ProductResponse MapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}

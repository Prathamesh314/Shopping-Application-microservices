package com.onlineshopping.productservice.Controller;


import com.onlineshopping.productservice.Service.ProductService;
import com.onlineshopping.productservice.dto.ProductRequest;
import com.onlineshopping.productservice.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
        return "Product added successfully";
    }

    // get
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    // delete


    // update

}

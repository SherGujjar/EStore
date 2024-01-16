package com.example.ElectronicStore.Controller;

import com.example.ElectronicStore.Dto.ProductDto;
import com.example.ElectronicStore.Entity.Product;
import com.example.ElectronicStore.Service.ProductService;
import com.example.ElectronicStore.Utils.ApiResponseMessage;
import com.example.ElectronicStore.Utils.PageableResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping()
    public PageableResponse<ProductDto> getAllProduct(@RequestParam(value ="pageNo",defaultValue = "0", required = false) int pageNo,@RequestParam(value ="pageNo",defaultValue = "0", required = false) int pageSize, @RequestParam(value ="pageNo",defaultValue = "0", required = false) String sortBy ,@RequestParam(value ="pageNo",defaultValue = "0", required = false) String sortDir){
        return productService.getAllProducts(pageNo,pageSize,sortBy,sortDir);
    }

    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody Product product){
        ProductDto productDto = productService.createNewProduct(product);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponseMessage> deleteProduct(@RequestParam Long id){
        ApiResponseMessage response = productService.deleteProduct(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
        ProductDto product = productService.updateProduct(id,productDto);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }







}

package com.example.ElectronicStore.Controller;

import com.example.ElectronicStore.Dto.CategoryDto;
import com.example.ElectronicStore.Dto.ProductDto;
import com.example.ElectronicStore.Entity.Product;
import com.example.ElectronicStore.Service.CategoryService;
import com.example.ElectronicStore.Service.ProductService;
import com.example.ElectronicStore.Utils.ApiResponseMessage;
import com.example.ElectronicStore.Utils.PageableResponse;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private ProductService productService;

    private CategoryService categoryService;

    public ProductController(ProductService productService,CategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public PageableResponse<ProductDto> getAllProduct(@RequestParam(value ="pageNo",defaultValue = "0", required = false) int pageNo,@RequestParam(value ="paseSize",defaultValue = "10", required = false) int pageSize, @RequestParam(value ="sortBy",defaultValue = "title", required = false) String sortBy ,@RequestParam(value ="pageDir",defaultValue = "aesc", required = false) String sortDir){
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

    @PostMapping("/category")
    public ResponseEntity<ProductDto> createProductWithCategory(@RequestBody Product product,@RequestParam Long categoryId ){
        ProductDto productDto = productService.createProductWithCategory(product,categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.CREATED);
    }

    @PutMapping("/assignCategory")
    public ResponseEntity<ProductDto> assignCategoryToProduct(@RequestParam Long productId, @RequestParam Long categoryId){
        ProductDto productDto = productService.assignCategoryToProduct(productId,categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.ACCEPTED);
    }

    @GetMapping("/byCategoryType")
    public ResponseEntity<PageableResponse<ProductDto>> getProductByCategory(@RequestParam String categoryType,
                                                                             @RequestParam(value ="pageNo",defaultValue = "0", required = false) int pageNo,
                                                                             @RequestParam(value ="paseSize",defaultValue = "10", required = false) int pageSize,
                                                                             @RequestParam(value ="sortBy",defaultValue = "title", required = false) String sortBy,
                                                                             @RequestParam(value ="pageDir",defaultValue = "aesc", required = false) String sortDir){
        PageableResponse<ProductDto> response = productService.getProductOfCategory(categoryType,pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }





}

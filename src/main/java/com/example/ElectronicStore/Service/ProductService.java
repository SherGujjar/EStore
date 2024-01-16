package com.example.ElectronicStore.Service;

import com.example.ElectronicStore.Dto.CategoryDto;
import com.example.ElectronicStore.Dto.ProductDto;
import com.example.ElectronicStore.Entity.Category;
import com.example.ElectronicStore.Entity.Product;
import com.example.ElectronicStore.Repository.ProductRepository;
import com.example.ElectronicStore.Utils.ApiResponseMessage;
import com.example.ElectronicStore.Utils.GenricPageableResponse;
import com.example.ElectronicStore.Utils.NullUtils;
import com.example.ElectronicStore.Utils.PageableResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.NullableUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private CategoryService categoryService;

    private final ObjectMapper mapper = new ObjectMapper();

    public ProductService(ProductRepository productRepository, CategoryService categoryService){
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    // get all
    public PageableResponse<ProductDto> getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Product> allResponse = productRepository.findAll(pageable);
        PageableResponse<ProductDto> response = GenricPageableResponse.getPageableResponse(allResponse, ProductDto.class);
        return response;
    }

    // update one
    public ProductDto updateProduct(Long id, ProductDto productDto){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("No Such product id present in the database"));
        product.setCategory(productDto.getCategory());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        return mapper.convertValue(productRepository.save(product),ProductDto.class);

    }

    // delete One
    public ApiResponseMessage deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("No Such product id present in the database"));
        productRepository.delete(product);
        return ApiResponseMessage.builder().message("Deleted successfully").success(true).status(HttpStatus.OK).build();
    }

    //get one
    public ProductDto getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("No Such product id present in the database"));
        return mapper.convertValue(product,ProductDto.class);
    }

    // create one
    public ProductDto createNewProduct(Product product){
        Category category = product.getCategory();
        if(!NullUtils.isNull(category.getId())){
            Long id = category.getId();
            categoryService.getCategorybyId(id);
        }
        return mapper.convertValue(productRepository.save(product),ProductDto.class);
    }

}

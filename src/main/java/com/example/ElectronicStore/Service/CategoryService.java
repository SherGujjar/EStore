package com.example.ElectronicStore.Service;

import com.example.ElectronicStore.Dto.CategoryDto;
import com.example.ElectronicStore.Entity.Category;
import com.example.ElectronicStore.Repository.CategoryRepository;
import com.example.ElectronicStore.Utils.ApiResponseMessage;
import com.example.ElectronicStore.Utils.GenricPageableResponse;
import com.example.ElectronicStore.Utils.PageableResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    private ObjectMapper mapper = new ObjectMapper();

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    // gat all
    public PageableResponse<CategoryDto> getAllCategory(int pageNo,int pageSize, String sortDir, String sortBy ){
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Category> allCategory = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> response = GenricPageableResponse.getPageableResponse(allCategory, CategoryDto.class);
        return response;

    }
    // find one
    public CategoryDto getInfoByTitle(String type){
        Category category = categoryRepository.findByType(type).orElseThrow(() -> new RuntimeException("No Such category typw found"));
        CategoryDto categoryDto = mapper.convertValue(category, CategoryDto.class);
        return categoryDto;
    }

    // delete one
    public ApiResponseMessage deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("No Such category present with Given id"));
        categoryRepository.deleteById(id);
        return ApiResponseMessage.builder().message("Deleted Successfully").status(HttpStatus.OK).success(true).build();
    }

    // update one
    public CategoryDto updateCategory(CategoryDto categoryDto,Long id){
        Category categoryFromDb = categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("No Such category present with Given id"));
        categoryFromDb.setTitle(categoryDto.getTitle());
        categoryFromDb.setType(categoryDto.getType());
        categoryFromDb.setDescription(categoryDto.getDescription());
        return mapper.convertValue(categoryRepository.save(categoryFromDb),CategoryDto.class);
    }

    // create one
    public CategoryDto createCategory(Category category){
        return mapper.convertValue(categoryRepository.save(category),CategoryDto.class);
    }

    public CategoryDto getCategorybyId(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()->new RuntimeException("no such category id is preset"));
        return mapper.convertValue(category,CategoryDto.class);
    }

}

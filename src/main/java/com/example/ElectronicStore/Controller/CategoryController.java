package com.example.ElectronicStore.Controller;

import com.example.ElectronicStore.Dto.CategoryDto;
import com.example.ElectronicStore.Entity.Category;
import com.example.ElectronicStore.Service.CategoryService;
import com.example.ElectronicStore.Utils.ApiResponseMessage;
import com.example.ElectronicStore.Utils.PageableResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping()
    public PageableResponse<CategoryDto> getAllCategory(@RequestParam (value = "pageNo",defaultValue ="0" ,required=false) int pageNo,
                                                        @RequestParam (value = "pageSize",defaultValue ="10" ,required=false) int pageSize,
                                                        @RequestParam (value = "sortDir",defaultValue ="asc" ,required=false) String sortDir,
                                                        @RequestParam (value = "sortBy",defaultValue ="type" ,required=false) String sortBy){
        PageableResponse response = categoryService.getAllCategory(pageNo,pageSize,sortDir,sortBy);
        return response;

    }

    @GetMapping(path = "/byType")
    public ResponseEntity<CategoryDto> getCategoryByType(String type){
        CategoryDto infoByTitle = categoryService.getInfoByTitle(type);
        return new ResponseEntity<>(infoByTitle,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(@RequestBody Category category){
        CategoryDto categoryDto = categoryService.createCategory(category);
        return new ResponseEntity<>(categoryDto,HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponseMessage> deleteCategory(@RequestParam Long id){
        ApiResponseMessage apiResponseMessage = categoryService.deleteCategory(id);
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto category,@PathVariable Long id){
        CategoryDto categoryDto = categoryService.updateCategory(category,id);
        return new ResponseEntity<>(categoryDto,HttpStatus.ACCEPTED);
    }


}

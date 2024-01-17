package com.example.ElectronicStore.Utils;

import com.example.ElectronicStore.Dto.UserDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.stream.Collectors;

public class GenricPageableResponse {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page,Class<V> type){
        List<U> entityList = page.getContent();
        List<V> dtoList = entityList.stream().map(eachObject->mapper.convertValue(eachObject, type)).collect(Collectors.toList());
        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;

    }
}

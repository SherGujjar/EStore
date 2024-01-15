package com.example.ElectronicStore.Utils;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApiResponseMessage {

    private String message;
    private HttpStatus status;
    private boolean success;

}

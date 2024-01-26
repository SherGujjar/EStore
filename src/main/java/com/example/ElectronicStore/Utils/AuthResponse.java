package com.example.ElectronicStore.Utils;

import com.example.ElectronicStore.Dto.UserDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private UserDto userDto;

    private String token;

}

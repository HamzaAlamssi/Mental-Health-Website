package com.example.MentalHealthSystem.request;

import com.example.MentalHealthSystem.constants.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private UserRoles userRole;
}

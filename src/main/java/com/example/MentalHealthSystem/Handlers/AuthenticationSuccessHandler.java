package com.example.MentalHealthSystem.Handlers;

import com.example.MentalHealthSystem.constants.UserRoles;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Locale;

@Slf4j
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        boolean isDoctor = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> UserRoles.DOCTOR.equals(UserRoles.getEnumByValue(grantedAuthority.getAuthority())));
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> UserRoles.ADMIN.equals(UserRoles.getEnumByValue(grantedAuthority.getAuthority())));
        if (isAdmin){
            setDefaultTargetUrl("/admins/dashboard");
        } else if(isDoctor){
            log.error("doctors/dashboard");
            setDefaultTargetUrl("/doctors/dashboard");
        }

        else
//            setDefaultTargetUrl("/patients/dashboard");
        setDefaultTargetUrl("/homepage");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

package uz.pdp.revolusiondemo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.SignInDto;
import uz.pdp.revolusiondemo.payload.SignUpDto;
import uz.pdp.revolusiondemo.payload.TokenDto;

public interface AuthService extends UserDetailsService {
    ApiResultDto<?> signUp(SignUpDto signUpDto);

    ApiResultDto<?> verifyEmail(String code, String email);

    ApiResultDto<TokenDto> signIn(SignInDto sign);

    ApiResultDto<?> resetPassword(String email);

    ApiResultDto<?> acceptResetPassword(String code, String email, String password);
}

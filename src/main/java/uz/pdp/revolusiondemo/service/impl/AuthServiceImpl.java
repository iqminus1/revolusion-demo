package uz.pdp.revolusiondemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.revolusiondemo.enums.RoleEnum;
import uz.pdp.revolusiondemo.model.Code;
import uz.pdp.revolusiondemo.model.User;
import uz.pdp.revolusiondemo.payload.ApiResultDto;
import uz.pdp.revolusiondemo.payload.SignInDto;
import uz.pdp.revolusiondemo.payload.SignUpDto;
import uz.pdp.revolusiondemo.payload.TokenDto;
import uz.pdp.revolusiondemo.repository.CodeRepository;
import uz.pdp.revolusiondemo.repository.UserRepository;
import uz.pdp.revolusiondemo.security.JwtProvider;
import uz.pdp.revolusiondemo.service.AuthService;
import uz.pdp.revolusiondemo.service.MailService;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final Random random;
    private final AuthenticationProvider provider;
    private final JwtProvider jwtProvider;

    @Value("${app.code.attempt}")
    private Integer attempt;

    @Value("${app.code.expireMinute}")
    private Integer expireMinute;
    private final CodeRepository codeRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow();
    }

    @Override
    public ApiResultDto<?> signUp(SignUpDto signUpDto) {
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return ApiResultDto.error("Exists by email");
        }
        if (!signUpDto.getConfirmPassword().equals(signUpDto.getPassword())) {
            return ApiResultDto.error("Passwords not equals");
        }
        User user = User.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .balance(0.0d)
                .role(RoleEnum.USER)
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();
        userRepository.save(user);

        Code code = new Code(user.getEmail(), getCode(), attempt, System.currentTimeMillis() + expireMinute);
        codeRepository.save(code);

        mailService.verifyEmail(user.getEmail(), code.getCode(), false);

        return ApiResultDto.success("Verify email");
    }

    @Override
    public ApiResultDto<?> verifyEmail(String code, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        if (user.isEnable()) {
            return null;
        }

        Code codeEntity = codeRepository.getByEmail(user.getEmail());
        if (codeEntity.getExpireAt() <= System.currentTimeMillis()) {
            codeRepository.delete(codeEntity);
            userRepository.delete(user);
            return ApiResultDto.error("Ended verify time and your account is deleted.\nPlease retry create account");
        }

        if (!codeEntity.getCode().equals(code)) {
            if (codeEntity.getAttempt() == 1) {
                codeRepository.delete(codeEntity);
                userRepository.delete(user);
                return ApiResultDto.error("Your attempt ended and deleted user");
            }
            codeEntity.setAttempt(codeEntity.getAttempt() - 1);
            codeRepository.save(codeEntity);
            return ApiResultDto.error("Code not equals");
        }

        codeRepository.delete(codeEntity);

        user.setEnable(true);
        userRepository.save(user);

        return ApiResultDto.success("Ok");
    }

    @Override
    public ApiResultDto<TokenDto> signIn(SignInDto signIn) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword());
            provider.authenticate(authentication);
            String token = jwtProvider.generateToken(signIn.getEmail());
            return ApiResultDto.success(new TokenDto(token));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResultDto<?> resetPassword(String email) {
        Code code = new Code(email, getCode(), attempt, System.currentTimeMillis() + expireMinute);
        codeRepository.save(code);

        mailService.verifyEmail(email, code.getCode(), true);
        return ApiResultDto.success("Sent code for email");
    }

    @Override
    public ApiResultDto<?> acceptResetPassword(String code, String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow();

        if (!user.isEnable()) {
            return null;
        }

        Code codeEntity = codeRepository.getByEmail(user.getEmail());
        if (codeEntity.getExpireAt() <= System.currentTimeMillis()) {
            codeRepository.delete(codeEntity);
            return ApiResultDto.error("Expired code. Please retry later");
        }

        if (!codeEntity.getCode().equals(code)) {
            if (codeEntity.getAttempt() == 1) {
                userRepository.delete(user);
                return ApiResultDto.error("Code deleted");
            }
            codeEntity.setAttempt(codeEntity.getAttempt() - 1);
            codeRepository.save(codeEntity);
            return ApiResultDto.error("Code not equals");
        }

        codeRepository.delete(codeEntity);

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        return ApiResultDto.success("Ok");
    }

    private String getCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            if (random.nextBoolean()) {
                sb.append(random.nextInt(0, 9));
            } else {
                if (random.nextBoolean()) {
                    sb.append((char) random.nextInt(65, 90));
                } else {
                    sb.append((char) random.nextInt(97, 122));
                }
            }
        }
        return sb.toString();
    }
}

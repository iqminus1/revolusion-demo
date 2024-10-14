package uz.pdp.revolusiondemo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.revolusiondemo.model.User;
import uz.pdp.revolusiondemo.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class CommonUtils {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            return userRepository.findById(1).orElseThrow();
        }
        return (User) principal;
    }
}

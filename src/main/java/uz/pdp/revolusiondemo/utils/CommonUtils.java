package uz.pdp.revolusiondemo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.revolusiondemo.model.User;

@RequiredArgsConstructor
@Component
public class CommonUtils {
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

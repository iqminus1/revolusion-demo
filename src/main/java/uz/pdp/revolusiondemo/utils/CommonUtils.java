package uz.pdp.revolusiondemo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.revolusiondemo.model.User;

@RequiredArgsConstructor
@Component
public class CommonUtils {

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (User) principal;
    }


}

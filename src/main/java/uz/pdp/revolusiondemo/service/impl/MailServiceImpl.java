package uz.pdp.revolusiondemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import uz.pdp.revolusiondemo.service.MailService;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final MailSender mailSender;
    @Value("${app.java-mail.verification-url}")
    private String verificationUrl;

    @Value("${app.java-mail.reset-password}")
    private String resetPasswordUrl;

    @Override
    public void verifyEmail(String email, String code, boolean isReset) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Hotel");
        if (isReset)
            message.setText("For reset password click the link -> : " + resetPasswordUrl + "?email=%s&code=%s".formatted(email, code));
        else
            message.setText("Verification email click the link -> : " + verificationUrl + "?email=%s&code=%s".formatted(email, code));
        mailSender.send(message);
    }
}

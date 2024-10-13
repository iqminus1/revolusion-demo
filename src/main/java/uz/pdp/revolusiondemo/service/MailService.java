package uz.pdp.revolusiondemo.service;

public interface MailService {
    void verifyEmail(String email, String code, boolean isReset);
}

package by.vstu.service;

import by.vstu.model.user.UserToken;
import by.vstu.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//TODO: Create a notification service for that. Use Kafka or HTTP calls for integration
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService mailService;

    public void userRegistered(UserToken userToken) {
        mailService.confirmEmail(userToken.getUser().getEmail(), userToken.getToken());
    }

    public void userResetPassword(UserToken userToken) {
        mailService.resetPassword(userToken.getUser().getEmail(), userToken.getToken());
    }

    public void userRegisteredWithGeneratedPassword(String email, String password) {
        mailService.generateAccount(email, password);
    }
}

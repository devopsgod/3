package by.vstu.controller;

import by.vstu.dto.CreateUnconfirmedAccountDTO;
import by.vstu.dto.ResetPasswordDTO;
import by.vstu.dto.user.AccountDTO;
import by.vstu.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<AccountDTO> registerUser(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(service.registerAccount(accountDTO));
    }

    @GetMapping("/confirm")
    public String confirmMail(@RequestParam(value = "uid") String uid) {
        service.confirmMail(uid);
        return "redirect:https://priem.vstu.by/cabinet/";
    }

    @ResponseBody
    @PostMapping("/resend-mail")
    public void resendMail(@RequestParam(value = "email") String email) {
        service.resendConfirmMail(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AccountDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        return ResponseEntity.ok(service.resetPassword(resetPasswordDTO));
    }

    @ResponseBody
    @PostMapping("/drop-password")
    public void dropPassword(@RequestParam String email) {
        service.dropPassword(email);
    }

    @PostMapping("/server-create")
    @PreAuthorize("#oauth2.hasScope('user:write') && #oauth2.hasScope('backend-service')")
    public ResponseEntity<AccountDTO> createUser(@Valid @RequestBody CreateUnconfirmedAccountDTO accountDTO) {
        return ResponseEntity.ok(service.createUnconfirmedAccount(accountDTO));
    }
}

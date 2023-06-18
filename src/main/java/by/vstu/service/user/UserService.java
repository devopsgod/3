package by.vstu.service.user;

import by.vstu.dto.CreateUnconfirmedAccountDTO;
import by.vstu.dto.ResetPasswordDTO;
import by.vstu.dto.user.AccountDTO;
import by.vstu.dto.user.UserDTO;
import by.vstu.exception.BusinessEntityNotFoundException;
import by.vstu.exception.BusinessException;
import by.vstu.model.user.User;
import by.vstu.model.user.UserToken;
import by.vstu.repository.UserRepository;
import by.vstu.repository.UserTokenRepository;
import by.vstu.service.NotificationService;
import by.vstu.service.mapper.AccountDTOMapper;
import by.vstu.service.mapper.UserDTOMapper;
import by.vstu.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${token.email.confirm.time.hours}")
    private Long tokenExpireHours;

    private final UserRepository userRepository;
    private final UserTokenRepository tokenRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AccountDTOMapper accountDTOMapper;
    private final UserDTOMapper userDTOMapper;
    private final NotificationService notificationService;

    @Transactional(readOnly = true)
    public Page<UserDTO> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userDTOMapper::toDTO);
    }

    @Transactional(readOnly = true)
    User findByEmail(String email) {
        return userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new BusinessEntityNotFoundException("User not found"));
    }

    @Transactional
    public UserDTO update(UUID userId, @Valid UserDTO userDTO) {
        if (!userRepository.existsById(userId)) {
            throw new BusinessEntityNotFoundException("User not found");
        } else if (userRepository.existsByEmailAndIdNotAndDeletedFalse(userDTO.getEmail(), userId)) {
            throw new BusinessException("User email should be unique across system");
        }
        User user = userDTOMapper.toEntity(userDTO);
        return userDTOMapper.toDTO(userRepository.save(user));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountDTO registerAccount(AccountDTO accountDTO) {
        User saved = saveUser(accountDTOMapper.toEntity(accountDTO));

        notificationService.userRegistered(generateUserToken(saved));
        return accountDTOMapper.toDTO(saved);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDTO createAccount(UserDTO userDTO) {
        return userDTOMapper.toDTO(saveUser(userDTOMapper.toEntity(userDTO)));
    }

    /*
     *   TODO: Should be unconfirmed & with default account password
     */
    @Transactional
    public AccountDTO createUnconfirmedAccount(CreateUnconfirmedAccountDTO unconfirmedAccountDTO) {
        if (userRepository.existsByEmailAndDeletedFalse(unconfirmedAccountDTO.getEmail())) {
            return accountDTOMapper.toDTO(findByEmail(unconfirmedAccountDTO.getEmail()));
        }
        String password = CommonUtils.generateRandomString(8);
        User user = new User(unconfirmedAccountDTO.getEmail(),
                passwordEncoder.encode(password),
                roleService.getRoleByName(unconfirmedAccountDTO.getRole()));
        user.setActivated(true);
        User saved = userRepository.save(user);

        notificationService.userRegisteredWithGeneratedPassword(saved.getEmail(), password);
        return accountDTOMapper.toDTO(saved);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void confirmMail(String token) {
        UserToken emailToken = tokenRepository.getByTokenAndExpiredAfter(token, LocalDateTime.now())
                .orElseThrow(() -> new BusinessException("Token is not valid"));
        String email = emailToken.getUser().getEmail();

        userRepository.confirmUserEmail(email);
        tokenRepository.delete(emailToken);
    }

    @Transactional
    public void resendConfirmMail(String email) {
        UserToken emailToken = tokenRepository.getByUserEmail(email)
                .orElseThrow(() -> new BusinessEntityNotFoundException("Token not found"));

        notificationService.userRegistered(generateUserToken(emailToken.getUser()));
        tokenRepository.delete(emailToken);
    }

    @Transactional
    public void dropPassword(String email) {
        notificationService.userResetPassword(generateUserToken(findByEmail(email)));
    }

    @Transactional
    public AccountDTO resetPassword(ResetPasswordDTO resetPasswordDTO) {
        UserToken emailToken = tokenRepository.getByTokenAndExpiredAfter(resetPasswordDTO.getToken(), LocalDateTime.now())
                .orElseThrow(() -> new BusinessEntityNotFoundException("Token not found"));

        User user = findByEmail(emailToken.getUser().getEmail());
        user.setLocked(false);
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));

        return accountDTOMapper.toDTO(userRepository.save(user));
    }

    // TODO: rewrite it!
    @Transactional
    public UserDTO patchUser(UUID userId, Map<String, Object> patchMap) {
        UserDTO user = userDTOMapper.toDTO(findByIdNotNull(userId));
        for (Map.Entry<String, Object> entry : patchMap.entrySet()) {
            try {
                String methodName = "set".concat(entry.getKey().substring(0, 1).toUpperCase()).concat(entry.getKey().substring(1));
                Method method = user.getClass().getMethod(methodName, String.class);
                method.invoke(user, entry.getValue().toString());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new BusinessException("Patch for some fields can't be accepted");
            }
        }
        return update(userId, user);
    }

    private User findByIdNotNull(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessEntityNotFoundException("User not found"));
    }

    private User saveUser(User user) {
        if (userRepository.existsByEmailAndDeletedFalse(user.getEmail())) {
            throw new BusinessException("Conflict exception");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private UserToken generateUserToken(User user) {
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime expiredDate = createDate.plusHours(tokenExpireHours);
        String token = CommonUtils.generateRandomString(80);
        return tokenRepository.save(new UserToken(token, user, createDate, expiredDate));
    }
}

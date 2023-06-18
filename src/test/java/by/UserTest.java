package by;

import by.vstu.dto.CreateUnconfirmedAccountDTO;
import by.vstu.dto.RoleDTO;
import by.vstu.dto.user.AccountDTO;
import by.vstu.model.user.UserToken;
import by.vstu.repository.UserTokenRepository;
import by.vstu.service.user.RoleService;
import by.vstu.util.CommonUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTest extends BaseTest {

    @Autowired
    RoleService roleService;

    @Autowired
    UserTokenRepository tokenRepository;

    private List<RoleDTO> roles;

    @BeforeClass
    void initUnitTest() {
        roles = roleService.getAvailableRoles();
    }

    @Test
    void registerUserWithAvailableRole() throws Exception {
        AccountDTO dto = generateRandomAccountData();

        mvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        mvc.perform(post("/token")
                .param("grant_type", "password")
                .param("username", dto.getEmail())
                .param("password", dto.getPassword())
                .header("Authorization", DEFAULT_BASIC_AUTH_HEADER)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        Optional<UserToken> token = tokenRepository.getByUserEmail(dto.getEmail());
        Assert.assertTrue(token.isPresent());

        mvc.perform(get("/account/confirm")
                .param("uid", token.get().getToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());

        mvc.perform(post("/token")
                .param("grant_type", "password")
                .param("username", dto.getEmail())
                .param("password", dto.getPassword())
                .header("Authorization", DEFAULT_BASIC_AUTH_HEADER)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void serverCreate() throws Exception {
        CreateUnconfirmedAccountDTO accountDTO = new CreateUnconfirmedAccountDTO();
        accountDTO.setEmail("validEmail123@gmail.com");
        accountDTO.setRole(roles.get(0).getName());

        mvc.perform(post("/account/server-create").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ".concat(bearerToken))
                .content(new Gson().toJson(accountDTO)))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = mvc.perform(post("/account/server-create").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ".concat(bearerToken))
                .content(new Gson().toJson(accountDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String email = executeFieldFromResponse(result.getResponse().getContentAsString(), "email").getAsString();
        Assert.assertEquals(accountDTO.getEmail(), email);
    }

    @Test
    void confirmInvalidUserToken() throws Exception {
        String randomToken = CommonUtils.generateRandomString(80);

        mvc.perform(get("/account/confirm")
                .param("uid", randomToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private AccountDTO generateRandomAccountData() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setRoleId(roles.get(0).getId());
        accountDTO.setEmail(CommonUtils.generateRandomString(6).concat("@gmail.com"));
        accountDTO.setPassword(CommonUtils.generateRandomString(8));
        return accountDTO;
    }
}

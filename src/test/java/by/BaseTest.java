package by;

import by.vstu.AuthServiceApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest(classes = AuthServiceApplication.class)
public class BaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    MockMvc mvc;

    protected String bearerToken;

    protected final String DEFAULT_CLIENT_ID = "FULL_ACCESS_CLIENT";
    protected final String DEFAULT_CLIENT_SECRET = "FULL_ACCESS_CLIENT";

    protected final String DEFAULT_BASIC_AUTH_HEADER = "Basic ".concat(Base64.getEncoder()
            .encodeToString(DEFAULT_CLIENT_ID.concat(":").concat(DEFAULT_CLIENT_SECRET).getBytes()));

    @BeforeClass
    void setUp() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(springSecurityFilterChain).build();

        MvcResult result = mvc.perform(post("/token?grant_type=client_credentials")
                .header("Authorization", DEFAULT_BASIC_AUTH_HEADER)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        bearerToken = executeFieldFromResponse(result.getResponse().getContentAsString(), "access_token").getAsString();
    }

    protected String generateClientBearerToken(String clientId, String clientSecret) {
        try {
            MvcResult result = mvc.perform(post("/token?grant_type=client_credentials")
                    .header("Authorization", "Basic ".concat(Base64.getEncoder().encodeToString(clientId.concat(":").concat(clientSecret).getBytes())))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
            return executeFieldFromResponse(result.getResponse().getContentAsString(), "access_token").getAsString();
        } catch (Exception ex) {
            logger.error(ex);
        }
        return bearerToken;
    }

    protected String generateBearerTokenWithUserCredentials(String clientId, String clientSecret, String username, String password) {
        try {
            MvcResult result = mvc.perform(post("/token")
                    .param("grant_type", "password")
                    .param("username", username)
                    .param("password", password)
                    .header("Authorization", "Basic ".concat(Base64.getEncoder().encodeToString(clientId.concat(":").concat(clientSecret).getBytes())))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
            return executeFieldFromResponse(result.getResponse().getContentAsString(), "access_token").getAsString();
        } catch (Exception ex) {
            logger.error(ex);
        }
        return bearerToken;
    }

    protected JsonElement executeFieldFromResponse(String response, String fieldName) {
        JsonElement jsonElement = JsonParser.parseString(response);
        return jsonElement.getAsJsonObject().get(fieldName);
    }
}

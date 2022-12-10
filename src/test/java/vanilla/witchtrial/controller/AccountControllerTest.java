package vanilla.witchtrial.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vanilla.witchtrial.domain.dto.SignupDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("사용자 회원가입")
    @Test
    void signupSucess() throws Exception {
        SignupDto signupDto = SignupDto.builder()
                .email("vanille@gmail.com")
                .username("vanille")
                .password("1q2w3e4r")
                .build();

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signupDto)))
                .andExpect(status().is2xxSuccessful());
    }

    @DisplayName("이메일 입력이 안되었을 BindException 발생한다.")
    @Test
    void signupValidationEmail() throws Exception {
        SignupDto signupDto = SignupDto.builder()
                .username("vanille")
                .password("1q2w3e4r")
                .build();

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signupDto)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("이메일 형식이 안맞을 때 BindException 발생한다.")
    @Test
    void signupValidationEmailPattern() throws Exception {
        SignupDto signupDto = SignupDto.builder()
                .email("vanille123")
                .username("vanille")
                .password("1q2w3e4r")
                .build();

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signupDto)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("사용자 이름 입력이 안되었을 BindException 발생한다.")
    @Test
    void signupValidationUsername() throws Exception {
        SignupDto signupDto = SignupDto.builder()
                .email("vanille@gmail.com")
                .password("1q2w3e4r")
                .build();

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signupDto)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("패스워드 입력이 안되었을 BindException 발생한다.")
    @Test
    void signupValidationPassword() throws Exception {
        SignupDto signupDto = SignupDto.builder()
                .email("vanille@gmail.com")
                .username("vanille")
                .build();

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signupDto)))
                .andExpect(status().is4xxClientError());
    }
}
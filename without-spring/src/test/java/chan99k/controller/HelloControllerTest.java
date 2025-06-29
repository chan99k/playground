package chan99k.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class HelloControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    @DisplayName("GET /api/hello는 'hello world' 문자열을 반환해야 한다")
    void hello_ShouldReturnHelloWorld() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        String contentType = result.getResponse().getContentType();

        assertThat(content)
                .isNotNull()
                .isEqualTo("hello world");

        assertThat(contentType)
                .isNotNull()
                .startsWith("text/plain");
    }
}

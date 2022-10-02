package mio68.lab.spring.app.api;

import mio68.lab.spring.app.map.PersonMapper;
import mio68.lab.spring.app.map.PersonMapperImpl;
import mio68.lab.spring.app.repository.PersonRepositoryImpl;
import mio68.lab.spring.app.service.PersonService;
import mio68.lab.spring.app.service.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test controller with security.
 * PersonServiceSecurityConfig defines security settings.
 */

// Use controller and mock for dependency components.
@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    @MockBean
    PersonService personService;

    @MockBean
    PersonMapper personMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void when_saveByAnonymous_thenUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"firstName\":\"John\", \"lastName\": \"Doe\"}")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    public void when_saveByUser_thenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"firstName\":\"John\", \"lastName\": \"Doe\"}")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void when_saveByAdmin_thenCreated() throws Exception {
        mockMvc.perform(post("/api/v1/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"firstName\":\"John\", \"lastName\": \"Doe\"}")
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

}
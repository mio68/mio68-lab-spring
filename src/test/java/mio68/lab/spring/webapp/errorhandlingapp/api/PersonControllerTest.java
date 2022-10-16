package mio68.lab.spring.webapp.errorhandlingapp.api;

import mio68.lab.spring.webapp.errorhandlingapp.configuration.ApplicationNoSecurity;
import mio68.lab.spring.webapp.errorhandlingapp.service.ExceptionalPersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Test controller with error handling.
 * Pay attention that security is off with ApplicationNoSecurity configuration.
 * It's a bit awkward. Try to find better solution.
 */

@WebMvcTest({
        PersonController.class,
        ExceptionalPersonServiceImpl.class,
        ApplicationNoSecurity.class})
class PersonControllerTest {

    public static final String API_V_1_PERSON = "/api/v1/person";
    public static final String JOHN_DOE_PERSON_JSON = "{ \"firstName\":\"John\", \"lastName\": \"Doe\"}";
    public static final String NOT_JOHN_DOE_PERSON_JSON = "{ \"firstName\":\"NotJohn\", \"lastName\": \"NotDoe\"}";
    public static final String EMPTY_FIRST_NAME_PERSON_JSON = "{ \"firstName\":\"\", \"lastName\": \"Doe\"}";

    @Autowired
    MockMvc mockMvc;

    // DTO validation test
    @Test
    public void when_saveEmptyFirstNamePerson_thenBadRequestWithValidationErrorMessage() throws Exception {
        mockMvc.perform(post(API_V_1_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(EMPTY_FIRST_NAME_PERSON_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void when_saveJohnDoe_thenConflict() throws Exception {
        mockMvc.perform(post(API_V_1_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JOHN_DOE_PERSON_JSON)
                        .with(csrf()))
                .andExpect(status().isConflict())
                .andExpect(content().string("John Doe is already exist!"));
    }

    @Test
    public void when_saveNotJohnDoe_thenServerError() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(API_V_1_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NOT_JOHN_DOE_PERSON_JSON)
                        .with(csrf()))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("John Doe is only supported!"));
    }

}
package mio68.lab.spring.rest.errorhandling.api;

import mio68.lab.spring.rest.errorhandling.exception.PersonAlreadyExistException;
import mio68.lab.spring.rest.errorhandling.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test exception handling with Handler.
 */
@WebMvcTest(controllers = {PersonController.class})
class PersonControllerTest {

    public static final String API_V_1_PERSON = "/api/v1/person";
    public static final String VALID_PERSON_JSON = "{ \"firstName\":\"John\", \"lastName\": \"Doe\"}";
    public static final String EMPTY_FIRST_NAME_PERSON_JSON = "{ \"firstName\":\"\", \"lastName\": \"Doe\"}";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    // DTO validation test
    @Test

    public void when_saveEmptyFirstNamePerson_thenBadRequestWithValidationErrorMessage() throws Exception {
        mockMvc.perform(post(API_V_1_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(EMPTY_FIRST_NAME_PERSON_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void when_serviceThrowsPersonAlreadyExistsException_thenConflict() throws Exception {
        doThrow(new PersonAlreadyExistException("Already exists")).when(personService).save(any());

        mockMvc.perform(post(API_V_1_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_PERSON_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void when_serviceThrowsIllegalArgumentException_thenServerError() throws Exception {
        doThrow(new IllegalArgumentException("Illegal argument")).when(personService).save(any());

        mockMvc.perform(post(API_V_1_PERSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_PERSON_JSON))
                .andExpect(status().is5xxServerError());
    }

}
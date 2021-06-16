package au.com.belong.customerservice.web.api;

import au.com.belong.customerservice.TestData;
import au.com.belong.customerservice.domain.PhoneNumberId;
import au.com.belong.customerservice.dto.PhoneNumberDto;
import au.com.belong.customerservice.dto.SimpleResultDto;
import au.com.belong.customerservice.service.PhoneNumberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class PhoneNumberControllerImplIT {

    @Autowired
    private MockMvc mockMvc;

    // I like to test controllers in isolation, hence this mock
    @MockBean
    private PhoneNumberService phoneNumberService;

    @Test
    public void shouldReturnAllPhoneNumbers() throws Exception {
        List<PhoneNumberDto> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(TestData.phoneNumberDto(61, 111111111L, 1L));
        phoneNumbers.add(TestData.phoneNumberDto(61, 222222222L, 2L));
        phoneNumbers.add(TestData.phoneNumberDto(44, 333333333L, 3L));
        when(phoneNumberService.getAllPhoneNumbers()).thenReturn(phoneNumbers);

        mockMvc.perform(get("/api/v1/phoneNumbers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].countryCode").value(61))
                .andExpect(jsonPath("$[0].number").value(111111111L))
                .andExpect(jsonPath("$[0].customerId").value(1L))
                .andExpect(jsonPath("$[1].countryCode").value(61))
                .andExpect(jsonPath("$[1].number").value(222222222L))
                .andExpect(jsonPath("$[1].customerId").value(2L))
                .andExpect(jsonPath("$[2].countryCode").value(44))
                .andExpect(jsonPath("$[2].number").value(333333333L))
                .andExpect(jsonPath("$[2].customerId").value(3L));
    }

    @Test
    public void shouldReturnCustomersPhoneNumbers() throws Exception {
        List<PhoneNumberDto> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(TestData.phoneNumberDto(61, 111111111L, 1L));
        phoneNumbers.add(TestData.phoneNumberDto(61, 222222222L, 1L));
        when(phoneNumberService.getAllPhoneNumbers(eq(1L))).thenReturn(phoneNumbers);

        mockMvc.perform(get("/api/v1/phoneNumbers?customerId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].countryCode").value(61))
                .andExpect(jsonPath("$[0].number").value(111111111L))
                .andExpect(jsonPath("$[0].customerId").value(1L))
                .andExpect(jsonPath("$[1].countryCode").value(61))
                .andExpect(jsonPath("$[1].number").value(222222222L))
                .andExpect(jsonPath("$[1].customerId").value(1L));
    }

    // TODO also test some corner cases for getPhoneNumbers, eg. invalid customerId

    @Test
    public void shouldActivatePhoneNumber() throws Exception {
        when(phoneNumberService.activate(any(PhoneNumberId.class))).thenReturn(new SimpleResultDto(true, "Successfully activated"));

        mockMvc.perform(put("/api/v1/phoneNumbers/{intNumber}/activate", "61123456789"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successfully activated")));
    }

    @Test
    public void shouldReturnBadRequestAfterException() throws Exception {
        doThrow(new IllegalArgumentException("Unknown phone number"))
                .when(phoneNumberService).activate(any(PhoneNumberId.class));

        mockMvc.perform(put("/api/v1/phoneNumbers/{intNumber}/activate", "61123456789"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Unknown phone number")));
    }
}

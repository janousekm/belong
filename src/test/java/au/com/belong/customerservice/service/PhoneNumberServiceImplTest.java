package au.com.belong.customerservice.service;

import au.com.belong.customerservice.TestData;
import au.com.belong.customerservice.domain.Customer;
import au.com.belong.customerservice.domain.PhoneNumber;
import au.com.belong.customerservice.domain.PhoneNumberId;
import au.com.belong.customerservice.dto.PhoneNumberDto;
import au.com.belong.customerservice.dto.SimpleResultDto;
import au.com.belong.customerservice.repository.PhoneNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberServiceImplTest {

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private PhoneNumberServiceImpl phoneNumberService;

    @Test
    public void shouldReturnAllPhoneNumbers() throws Exception {
        // given
        PhoneNumber pn1 = TestData.phoneNumber();
        pn1.setCountryCode(1);
        PhoneNumber pn2 = TestData.phoneNumber();
        pn2.setCountryCode(2);
        when(phoneNumberRepository.findAll()).thenReturn(Arrays.asList(pn1, pn2));

        // when
        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getAllPhoneNumbers();

        // then
        assertThat(phoneNumbers).hasSize(2);
        assertThat(phoneNumbers.get(0).getCountryCode()).isEqualTo(1);
        assertThat(phoneNumbers.get(1).getCountryCode()).isEqualTo(2);
    }

    @Test
    public void shouldReturnCustomersPhoneNumbers() throws Exception {
        // given
        PhoneNumber pn1 = TestData.phoneNumber();
        pn1.setCountryCode(1);
        PhoneNumber pn2 = TestData.phoneNumber();
        pn2.setCountryCode(2);
        when(phoneNumberRepository.findByCustomer(any(Customer.class)))
                .thenReturn(Arrays.asList(pn1, pn2));
        when(customerService.getCustomer(eq(1L)))
                .thenReturn(Optional.of(TestData.customer()));

        // when
        List<PhoneNumberDto> phoneNumbers = phoneNumberService.getAllPhoneNumbers(1L);

        // then
        assertThat(phoneNumbers).hasSize(2);
        assertThat(phoneNumbers.get(0).getCountryCode()).isEqualTo(1);
        assertThat(phoneNumbers.get(1).getCountryCode()).isEqualTo(2);
    }

    @Test
    public void shouldActivateAndSavePhoneNumber() throws Exception {
        // given
        PhoneNumber phoneNumber = TestData.phoneNumber();
        PhoneNumberId phoneNumberId = TestData.phoneNumberId();
        when(phoneNumberRepository.findById(eq(phoneNumberId))).thenReturn(Optional.of(phoneNumber));

        // when
        SimpleResultDto result = phoneNumberService.activate(phoneNumberId);

        // then
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getDescription()).contains(phoneNumber.toString(), "activated");
        assertThat(phoneNumber.isActive()).isTrue();
        verify(phoneNumberRepository).save(eq(phoneNumber));
    }

    @Test
    public void shouldNotActivateUnknownNumber() throws Exception {
        // given
        PhoneNumberId phoneNumberId = TestData.phoneNumberId();
        when(phoneNumberRepository.findById(eq(phoneNumberId))).thenReturn(Optional.empty());

        // then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // when
            phoneNumberService.activate(phoneNumberId);
        });
        assertThat(exception.getMessage()).containsIgnoringCase("Unknown Phone Number");
    }

    @Test
    public void shouldNotActivateAlreadyActivatedNumber() throws Exception {
        // given
        PhoneNumber phoneNumber = TestData.phoneNumber();
        phoneNumber.setActive(true);
        PhoneNumberId phoneNumberId = TestData.phoneNumberId();
        when(phoneNumberRepository.findById(eq(phoneNumberId))).thenReturn(Optional.of(phoneNumber));

        // then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // when
            phoneNumberService.activate(phoneNumberId);
        });
        assertThat(exception.getMessage()).containsIgnoringCase("is already activated");
    }
}

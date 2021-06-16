package au.com.belong.customerservice;

import au.com.belong.customerservice.domain.Customer;
import au.com.belong.customerservice.domain.PhoneNumber;
import au.com.belong.customerservice.dto.SimpleResultDto;
import au.com.belong.customerservice.repository.CustomerRepository;
import au.com.belong.customerservice.repository.PhoneNumberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneNumberActivationE2E {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void phoneNumberActivationE2eTest() {
        // given
        Customer customer = TestData.customer();
        customerRepository.save(customer);

        PhoneNumber phoneNumber = TestData.phoneNumber();
        phoneNumber.setActive(false);
        phoneNumber.setCustomer(customer);
        phoneNumberRepository.save(phoneNumber);

        StringBuilder url = new StringBuilder();
        url.append("http://localhost:");
        url.append(port);
        url.append("/api/v1/phoneNumbers/");
        url.append(phoneNumber.getCountryCode());
        url.append(phoneNumber.getNumber());
        url.append("/activate");

        // when
        HttpEntity<String> request = new HttpEntity<>("");
        ResponseEntity<SimpleResultDto> response = restTemplate.exchange(url.toString(), HttpMethod.PUT, request, SimpleResultDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Optional<PhoneNumber> dbPhoneNumber = phoneNumberRepository.findById(TestData.phoneNumberId());
        assertThat(dbPhoneNumber).isNotEmpty();
        assertThat(dbPhoneNumber.get().isActive()).isTrue();
    }
}

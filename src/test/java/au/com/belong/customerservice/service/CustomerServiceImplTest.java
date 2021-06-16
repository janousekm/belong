package au.com.belong.customerservice.service;

import au.com.belong.customerservice.TestData;
import au.com.belong.customerservice.domain.Customer;
import au.com.belong.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void shouldReturnCustomerFromDb() throws Exception {
        // given
        Customer customer = TestData.customer();
        when(customerRepository.findById(eq(1L))).thenReturn(Optional.of(customer));

        // when
        Optional<Customer> customerDb = customerService.getCustomer(1L);

        // then
        assertThat(customerDb).isNotEmpty();
        assertThat(customerDb.get()).isEqualTo(customer);
    }

    @Test
    public void shouldValidateCustomerId() throws Exception {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.getCustomer(null);
        });
        assertThat(exception.getMessage()).containsIgnoringCase("must not be null");
    }
}

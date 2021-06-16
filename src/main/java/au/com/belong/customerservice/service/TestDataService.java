package au.com.belong.customerservice.service;

import au.com.belong.customerservice.domain.Customer;
import au.com.belong.customerservice.domain.PhoneNumber;
import au.com.belong.customerservice.repository.CustomerRepository;
import au.com.belong.customerservice.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TestDataService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void createTestDataInDb() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Smith");
        customerRepository.save(customer1);

        PhoneNumber number1 = new PhoneNumber();
        number1.setCustomer(customer1);
        number1.setActive(true);
        number1.setCountryCode(61);
        number1.setNumber(111111111L);
        phoneNumberRepository.save(number1);

        PhoneNumber number2 = new PhoneNumber();
        number2.setCustomer(customer1);
        number2.setActive(false);
        number2.setCountryCode(44);
        number2.setNumber(111111111L);
        phoneNumberRepository.save(number2);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Smith");
        customerRepository.save(customer2);

        PhoneNumber number3 = new PhoneNumber();
        number3.setCustomer(customer2);
        number3.setActive(true);
        number3.setCountryCode(61);
        number3.setNumber(222222222L);
        phoneNumberRepository.save(number3);
    }
}

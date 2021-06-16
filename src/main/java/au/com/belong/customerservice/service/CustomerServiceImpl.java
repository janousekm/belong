package au.com.belong.customerservice.service;

import au.com.belong.customerservice.domain.Customer;
import au.com.belong.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Optional<Customer> getCustomer(Long customerId) {
        Assert.notNull(customerId, "Customer ID must not be null");
        return customerRepository.findById(customerId);
    }
}

package au.com.belong.customerservice.service;

import au.com.belong.customerservice.domain.Customer;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> getCustomer(Long customerId);
}

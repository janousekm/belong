package au.com.belong.customerservice.repository;

import au.com.belong.customerservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

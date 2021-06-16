package au.com.belong.customerservice.service;

import au.com.belong.customerservice.domain.Customer;
import au.com.belong.customerservice.domain.PhoneNumber;
import au.com.belong.customerservice.domain.PhoneNumberId;
import au.com.belong.customerservice.dto.PhoneNumberDto;
import au.com.belong.customerservice.dto.SimpleResultDto;
import au.com.belong.customerservice.repository.PhoneNumberRepository;
import au.com.belong.customerservice.util.PhoneNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    // TODO I would definitely use Page<PhoneNumber> and Pageable as this a backdoor for DDoS
    public List<PhoneNumberDto> getAllPhoneNumbers() {
        return phoneNumberRepository.findAll()
                .stream()
                .map(PhoneNumberUtils::toDto)
                .collect(Collectors.toList());
    }

    @Override
    // TODO I would definitely use Page<PhoneNumber> and Pageable as this a backdoor for DDoS
    public List<PhoneNumberDto> getAllPhoneNumbers(Long customerId) {
        Optional<Customer> customer = customerService.getCustomer(customerId);
        Assert.isTrue(customer.isPresent(), "Customer doesn't exist: " + customerId );
        return phoneNumberRepository.findByCustomer(customer.get())
                .stream()
                .map(PhoneNumberUtils::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SimpleResultDto activate(PhoneNumberId phoneNumberId) {
        Optional<PhoneNumber> phoneNumber = phoneNumberRepository.findById(phoneNumberId);
        Assert.isTrue(phoneNumber.isPresent(), "Unknown Phone Number");
        Assert.isTrue(!phoneNumber.get().isActive(), "Phone Number " + phoneNumberId + " is already activated");

        phoneNumber.get().setActive(true);
        phoneNumberRepository.save(phoneNumber.get());
        return new SimpleResultDto(true, "Phone number " + phoneNumberId + " has been activated");
    }
}

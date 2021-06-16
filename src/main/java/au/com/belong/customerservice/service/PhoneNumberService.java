package au.com.belong.customerservice.service;

import au.com.belong.customerservice.domain.PhoneNumberId;
import au.com.belong.customerservice.dto.PhoneNumberDto;
import au.com.belong.customerservice.dto.SimpleResultDto;

import java.util.List;

public interface PhoneNumberService {

    List<PhoneNumberDto> getAllPhoneNumbers();

    List<PhoneNumberDto> getAllPhoneNumbers(Long customerId);

    SimpleResultDto activate(PhoneNumberId phoneNumberId);
}

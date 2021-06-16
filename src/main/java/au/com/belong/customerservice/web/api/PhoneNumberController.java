package au.com.belong.customerservice.web.api;

import au.com.belong.customerservice.dto.PhoneNumberDto;
import au.com.belong.customerservice.dto.SimpleResultDto;

import java.util.List;

public interface PhoneNumberController {

    List<PhoneNumberDto> getAllPhoneNumbers(Long customerId);

    SimpleResultDto activate(String intNumber);
}

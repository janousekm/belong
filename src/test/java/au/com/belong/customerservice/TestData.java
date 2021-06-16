package au.com.belong.customerservice;

import au.com.belong.customerservice.domain.Customer;
import au.com.belong.customerservice.domain.PhoneNumber;
import au.com.belong.customerservice.domain.PhoneNumberId;
import au.com.belong.customerservice.dto.PhoneNumberDto;

public class TestData {
    public static final int COUNTRY_CODE = 61;
    public static final long NUMBER = 423123456L;

    public static PhoneNumberId phoneNumberId() {
        return new PhoneNumberId(COUNTRY_CODE, NUMBER);
    }

    public static PhoneNumber phoneNumber() {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountryCode(COUNTRY_CODE);
        phoneNumber.setNumber(NUMBER);
        phoneNumber.setActive(false);
        phoneNumber.setCustomer(customer());
        return phoneNumber;
    }

    public static PhoneNumberDto phoneNumberDto(int countryCode, long number, long customerId) {
        PhoneNumberDto dto = new PhoneNumberDto();
        dto.setCountryCode(countryCode);
        dto.setNumber(number);
        dto.setActive(false);
        dto.setCustomerId(customerId);
        return dto;
    }

    public static Customer customer() {
        Customer customer = new Customer();
        customer.setName("John Smith");
        return customer;
    }
}

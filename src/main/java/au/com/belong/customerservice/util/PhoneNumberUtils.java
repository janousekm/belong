package au.com.belong.customerservice.util;

import au.com.belong.customerservice.domain.PhoneNumber;
import au.com.belong.customerservice.domain.PhoneNumberId;
import au.com.belong.customerservice.dto.PhoneNumberDto;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

// TODO this class could be split into PhoneNumberValidator and PhoneNumberConvertor
public class PhoneNumberUtils {
    // TODO phone number validation could be more sophisticated
    private static final Pattern PATTERN = Pattern.compile("^\\d{10,13}$");

    public static boolean isValid(String intNumber) {
        return PATTERN.matcher(intNumber).matches();
    }

    public static PhoneNumberId parse(String intNumber) {
        Assert.isTrue(isValid(intNumber), "Invalid phone number: " + intNumber);

        String countryCodeStr = intNumber.substring(0, intNumber.length() - 9);
        String numberStr = intNumber.substring(countryCodeStr.length());

        Integer countryCode = Integer.parseInt(countryCodeStr);
        Long number = Long.parseLong(numberStr);
        return new PhoneNumberId(countryCode, number);
    }

    public static PhoneNumberDto toDto(PhoneNumber phoneNumber) {
        PhoneNumberDto dto = new PhoneNumberDto();
        dto.setCountryCode(phoneNumber.getCountryCode());
        dto.setNumber(phoneNumber.getNumber());
        dto.setActive(phoneNumber.isActive());
        dto.setCustomerId(phoneNumber.getCustomer().getId());
        return dto;
    }
}

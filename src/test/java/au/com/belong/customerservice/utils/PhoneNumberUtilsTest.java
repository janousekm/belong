package au.com.belong.customerservice.utils;

import au.com.belong.customerservice.TestData;
import au.com.belong.customerservice.domain.PhoneNumber;
import au.com.belong.customerservice.domain.PhoneNumberId;
import au.com.belong.customerservice.dto.PhoneNumberDto;
import au.com.belong.customerservice.util.PhoneNumberUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PhoneNumberUtilsTest {

    @Test
    public void shouldPassValidation() throws Exception {
        assertThat(PhoneNumberUtils.isValid("1123456789")).isTrue();
        assertThat(PhoneNumberUtils.isValid("61123456789")).isTrue();
        assertThat(PhoneNumberUtils.isValid("420123456789")).isTrue();
    }

    @Test
    public void shouldNotPassValidation() throws Exception {
        assertThat(PhoneNumberUtils.isValid("123456789")).isFalse();
        assertThat(PhoneNumberUtils.isValid("12345678901234")).isFalse();
        assertThat(PhoneNumberUtils.isValid("1234a6789")).isFalse();
    }

    @Test
    public void shouldParseInternationalNumber() throws Exception {
        assertThat(PhoneNumberUtils.parse("61423154789"))
                .isEqualTo(new PhoneNumberId(61, 423154789L));
    }

    @Test
    public void shouldNotParseInvalidInternationalNumber() throws Exception {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PhoneNumberUtils.parse("61789");
        });
        assertThat(exception.getMessage()).containsIgnoringCase("invalid phone number");
    }

    @Test
    public void shouldConvertEntityToDto() throws Exception {
        // given
        PhoneNumber phoneNumber = TestData.phoneNumber();

        // when
        PhoneNumberDto dto = PhoneNumberUtils.toDto(phoneNumber);

        // then
        assertThat(dto.getCountryCode()).isEqualTo(phoneNumber.getCountryCode());
        assertThat(dto.getNumber()).isEqualTo(phoneNumber.getNumber());
        assertThat(dto.isActive()).isEqualTo(phoneNumber.isActive());
        assertThat(dto.getCustomerId()).isEqualTo(phoneNumber.getCustomer().getId());
    }
}

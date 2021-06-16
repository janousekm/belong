package au.com.belong.customerservice.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class PhoneNumberId implements Serializable {

    private Integer countryCode;

    private Long number;

    public PhoneNumberId() {
        // default needed for JPA
    }

    public PhoneNumberId(Integer countryCode, Long number) {
        this.countryCode = countryCode;
        this.number = number;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public Long getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "+" + countryCode + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumberId that = (PhoneNumberId) o;

        return new EqualsBuilder().append(countryCode, that.countryCode).append(number, that.number).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(countryCode).append(number).toHashCode();
    }
}

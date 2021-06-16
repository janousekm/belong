package au.com.belong.customerservice.web.api;

import au.com.belong.customerservice.dto.PhoneNumberDto;
import au.com.belong.customerservice.dto.SimpleResultDto;
import au.com.belong.customerservice.service.PhoneNumberService;
import au.com.belong.customerservice.util.PhoneNumberUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO the class name might reflect API version eg. PhoneNumberControllerV1Impl
@RestController
@Api(value = "PhoneNumber")
public class PhoneNumberControllerImpl implements PhoneNumberController {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Override
    @GetMapping("/api/v1/phoneNumbers")
    @ApiOperation(response = PhoneNumberDto.class, value = "Get Phone Numbers",
            notes = "Returns all Phone numbers or specified Customer's phone numbers.")
    public List<PhoneNumberDto> getAllPhoneNumbers(@RequestParam(required = false) Long customerId) {
        if (customerId != null) {
            return phoneNumberService.getAllPhoneNumbers(customerId);
        }
        return phoneNumberService.getAllPhoneNumbers();
    }

    @Override
    @PutMapping("/api/v1/phoneNumbers/{intNumber}/activate")
    @ApiOperation(response = SimpleResultDto.class, value = "Activate Phone Number",
            notes = "Activates the given phone number and returns simple OK result if it succeed.")
    public SimpleResultDto activate(@PathVariable String intNumber) {
        return phoneNumberService.activate(PhoneNumberUtils.parse(intNumber));
    }
}

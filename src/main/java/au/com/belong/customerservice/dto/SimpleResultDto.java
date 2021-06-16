package au.com.belong.customerservice.dto;

public class SimpleResultDto {

    private final boolean success;
    private final String description;

    public SimpleResultDto(boolean success, String description) {
        this.success = success;
        this.description = description;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getDescription() {
        return description;
    }
}

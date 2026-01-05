package guru.springframework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;

public class CustomerDTO {

    @ApiModelProperty(value = "This is the first name", required = true)
    private String firstName;

    @ApiModelProperty(required = true)
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

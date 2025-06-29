package guru.springframework.api.v1.model;

public class CustomerDTOWithUrl extends CustomerDTO {

    private String customerUrl;

    public String getCustomerUrl() {
        return customerUrl;
    }

    public void setCustomerUrl(String customerUrl) {
        this.customerUrl = customerUrl;
    }

}

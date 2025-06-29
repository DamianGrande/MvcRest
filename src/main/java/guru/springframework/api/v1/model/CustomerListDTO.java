package guru.springframework.api.v1.model;

import java.util.List;

public class CustomerListDTO {

    private List<CustomerDTOWithUrl> customers;

    public CustomerListDTO() {
    }

    public CustomerListDTO(List<CustomerDTOWithUrl> customers) {
        this.customers = customers;
    }

    public List<CustomerDTOWithUrl> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerDTOWithUrl> customers) {
        this.customers = customers;
    }

}

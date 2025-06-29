package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerDTOWithUrl;
import guru.springframework.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerMapperTest {

    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Test
    public void fromCustomerToCustomerDTOWithUrl() {

        Customer customer = new Customer(69L, "John", "Doe");

        CustomerDTOWithUrl dto = mapper.fromCustomerToCustomerDTOWithUrl(customer);

        checkDTO(dto, "John", "Doe");

    }

    @Test
    public void fromCustomerToCustomerDTO() {

        Customer customer = new Customer(69L, "John", "Doe");

        CustomerDTOWithUrl dto = mapper.fromCustomerToCustomerDTOWithUrl(customer);

        checkDTOWithUrl(dto, "/shop/customers/69", "John", "Doe");

    }

    @Test
    public void fromCustomersToCustomerDTOs() {

        Customer customer1 = new Customer(70L, "Jack", "Jackson");
        Customer customer2 = new Customer(71L, "Bill", "Williamson");
        List<Customer> customers = Arrays.asList(customer1, customer2);

        List<CustomerDTOWithUrl> customerDTOs = mapper.fromCustomersToCustomerDTOs(customers);

        assertNotNull(customerDTOs);
        assertEquals(2, customerDTOs.size());

        checkDTOWithUrl(customerDTOs.get(0), "/shop/customers/70", "Jack", "Jackson");
        checkDTOWithUrl(customerDTOs.get(1), "/shop/customers/71", "Bill", "Williamson");

    }

    private void checkDTO(CustomerDTO dto, String expectedName, String expectedSurname) {

        assertNotNull(dto);
        assertEquals(expectedName, dto.getFirstName());
        assertEquals(expectedSurname, dto.getLastName());

    }

    private void checkDTOWithUrl(CustomerDTOWithUrl dto, String expectedUrl, String expectedName, String expectedSurname) {

        checkDTO(dto, expectedName, expectedSurname);
        assertEquals(expectedUrl, dto.getCustomerUrl());

    }


}
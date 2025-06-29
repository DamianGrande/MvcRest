package guru.springframework.service;

import guru.springframework.IntegrationTest;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class CustomerServiceIntegrationTest extends IntegrationTest {

    @Test
    public void patchCustomerUpdateFirstName() {

        String updatedName = "updatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getById(id);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        verify(spyCustomerRepository, times(1)).save(originalCustomer);

        Customer updatedCustomer = customerRepository.findById(id).orElseThrow();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstName());
        assertNotEquals(originalFirstName, updatedCustomer.getFirstName());
        assertEquals(originalLastName, updatedCustomer.getLastName());

    }

    @Test
    public void patchCustomerUpdateLastName() {

        String updatedName = "updatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getById(id);
        assertNotNull(originalCustomer);

        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).orElseThrow();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastName());
        assertNotEquals(originalLastName, updatedCustomer.getLastName());
        assertEquals(originalFirstName, updatedCustomer.getFirstName());

    }

    private Long getCustomerIdValue() {

        List<Customer> customers = customerRepository.findAll();

        System.out.println("Customers Found: " + customers.size());

        return customers.get(0).getId();

    }

}

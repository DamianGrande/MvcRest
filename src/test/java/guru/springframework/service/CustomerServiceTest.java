package guru.springframework.service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerDTOWithUrl;
import guru.springframework.domain.Customer;
import guru.springframework.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllCustomers() {


        List<Customer> customers = Collections.emptyList();
        List<CustomerDTOWithUrl> dtos = Collections.emptyList();

        when(repository.findAll()).thenReturn(customers);
        when(mapper.fromCustomersToCustomerDTOs(customers)).thenReturn(dtos);

        List<CustomerDTOWithUrl> returnedDtos = service.getAllCustomers();

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).fromCustomersToCustomerDTOs(anyList());
        verify(mapper, times(1)).fromCustomersToCustomerDTOs(customers);

        assertNotNull(returnedDtos);
        assertEquals(returnedDtos, dtos);

    }

    @Test
    public void getCustomer() {

        Customer customer = new Customer();
        CustomerDTO customerDTO = new CustomerDTO();

        when(repository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(mapper.fromCustomerToCustomerDTO(customer)).thenReturn(customerDTO);

        CustomerDTO returnedDto = service.getCustomerById(69L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).findById(69L);
        verify(mapper, times(1)).fromCustomerToCustomerDTO(any(Customer.class));
        verify(mapper, times(1)).fromCustomerToCustomerDTO(customer);

        assertNotNull(returnedDto);
        assertEquals(returnedDto, customerDTO);

    }

    @Test
    public void getNullCustomer() {

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getCustomerById(69L));

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).findById(69L);
        verify(mapper, times(0)).fromCustomerToCustomerDTO(any(Customer.class));

    }

    @Test
    public void createNewCustomer() {

        CustomerDTO customerDTO = new CustomerDTO();
        CustomerDTOWithUrl customerDTOWithUrl = new CustomerDTOWithUrl();

        createSaveSetUp(customerDTO, customerDTOWithUrl);

        CustomerDTOWithUrl savedDTO = service.createNewCustomer(customerDTO);

        assertEquals(customerDTOWithUrl, savedDTO);

    }

    @Test
    public void saveCustomerByDTO() {

        CustomerDTO customerDTO = new CustomerDTO();
        CustomerDTOWithUrl customerDTOWithUrl = new CustomerDTOWithUrl();

        createSaveSetUp(customerDTO, customerDTOWithUrl);

        CustomerDTOWithUrl savedDTO = service.saveCustomerByDTO(1L, customerDTO);

        assertEquals(customerDTOWithUrl, savedDTO);

    }

    private void createSaveSetUp(CustomerDTO customerDTO, CustomerDTOWithUrl customerDTOWithUrl) {

        Customer customer = new Customer();
        Customer savedCustomer = new Customer();

        when(mapper.fromCustomerDTOtoCustomer(customerDTO)).thenReturn(customer);
        when(repository.save(customer)).thenReturn(savedCustomer);
        when(mapper.fromCustomerToCustomerDTOWithUrl(savedCustomer)).thenReturn(customerDTOWithUrl);

    }

    @Test
    public void deleteCustomerById() {

        Long id = 1L;

        repository.deleteById(id);

        verify(repository, times(1)).deleteById(anyLong());

    }

}
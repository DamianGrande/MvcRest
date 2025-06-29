package guru.springframework.service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerDTOWithUrl;
import guru.springframework.domain.Customer;
import guru.springframework.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Autowired
    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CustomerDTOWithUrl> getAllCustomers() {
        return mapper.fromCustomersToCustomerDTOs(repository.findAll());
    }

    public CustomerDTO getCustomerById(Long id) {
        return repository.findById(id).map(mapper::fromCustomerToCustomerDTO).orElseThrow(ResourceNotFoundException::new);
    }

    public CustomerDTOWithUrl createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(mapper.fromCustomerDTOtoCustomer(customerDTO));
    }

    public CustomerDTOWithUrl saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = mapper.fromCustomerDTOtoCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }

    public CustomerDTOWithUrl patchCustomer(Long id, CustomerDTO customerDTO) {
        return repository.findById(id).map(customer -> {
            if (customerDTO.getFirstName() != null)
                customer.setFirstName(customerDTO.getFirstName());
            if (customerDTO.getLastName() != null)
                customer.setLastName(customerDTO.getLastName());
            return saveAndReturnDTO(customer);
        }).orElseThrow(RuntimeException::new);
    }

    private CustomerDTOWithUrl saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = repository.save(customer);
        return mapper.fromCustomerToCustomerDTOWithUrl(savedCustomer);
    }

    public void deleteCustomerById(Long id) {
        repository.deleteById(id);
    }

}

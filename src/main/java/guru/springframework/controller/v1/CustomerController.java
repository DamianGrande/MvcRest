package guru.springframework.controller.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerDTOWithUrl;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.domain.Customer;
import guru.springframework.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        return new ResponseEntity<>(new CustomerListDTO(service.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTOWithUrl> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(service.createNewCustomer(customerDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTOWithUrl> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(service.saveCustomerByDTO(id, customerDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTOWithUrl> patcCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(service.patchCustomer(id, customerDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {

        service.deleteCustomerById(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}

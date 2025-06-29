package guru.springframework;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.repository.CategoryRepository;
import guru.springframework.repository.CustomerRepository;
import guru.springframework.repository.VendorRepository;
import guru.springframework.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class IntegrationTest {

    protected CustomerRepository spyCustomerRepository;

    protected CustomerService customerService;

    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected VendorRepository vendorRepository;

    @BeforeEach
    public void setUp() throws Exception {

        System.out.println("Loading Customer Data");
        System.out.println(categoryRepository.findAll().size());

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        spyCustomerRepository = Mockito.mock(CustomerRepository.class, AdditionalAnswers.delegatesTo(customerRepository));
        customerService = new CustomerService(spyCustomerRepository, CustomerMapper.INSTANCE);

    }

}

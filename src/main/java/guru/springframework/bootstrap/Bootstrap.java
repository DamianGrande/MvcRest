package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repository.CategoryRepository;
import guru.springframework.repository.CustomerRepository;
import guru.springframework.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;


    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dries");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        Customer customer1 = new Customer(70L, "Jack", "Jackson");
        Customer customer2 = new Customer(71L, "Bill", "Williamson");
        Customer customer3 = new Customer(72L, "Mia", "Lovefield");

        Vendor vendor1 = new Vendor("Fresh");
        Vendor vendor2 = new Vendor("AppleJuice");
        Vendor vendor3 = new Vendor("SuperFruit");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);
        vendorRepository.save(vendor3);

        System.out.println("Categories Loaded = " + categoryRepository.count());
        System.out.println("Customers Loaded = " + customerRepository.count());
        System.out.println("Vendors Loaded = " + vendorRepository.count());

    }

}

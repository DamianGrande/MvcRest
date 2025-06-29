package guru.springframework.repository;

import guru.springframework.IntegrationTest;
import guru.springframework.domain.Vendor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class VendorRepositoryTest extends IntegrationTest {

    @Test
    public void getVendorById() {

        String vendorName = "AppleJuice";

        Vendor returnedVendor = vendorRepository.getById(vendorRepository.findAll().stream().filter(vendor -> vendor.getName().equals(vendorName)).map(Vendor::getId).collect(Collectors.toList()).get(0));

        checkVendor(returnedVendor, vendorName);

    }

    @Test
    public void getAllVendors() {

        List<Vendor> vendors = vendorRepository.findAll();

        assertNotNull(vendors);
        assertEquals(3, vendors.size());
        checkVendor(vendors.get(0), "Fresh");
        checkVendor(vendors.get(1), "AppleJuice");
        checkVendor(vendors.get(2), "SuperFruit");

    }

    @Test
    public void saveVendor() {

        String vendorName = "Whatever";

        Vendor vendorToSave = new Vendor(vendorName);

        assertNull(vendorToSave.getId());
        assertFalse(vendorRepository.findAll().stream().anyMatch(vendor -> vendor.getName().equals(vendorName)));

        Vendor savedVendor = vendorRepository.save(vendorToSave);
        Long savedVendorId = savedVendor.getId();

        assertNotNull(savedVendorId);
        assertTrue(vendorRepository.findAll().stream().anyMatch(vendor -> vendor.getName().equals(vendorName)));

        Optional<Vendor> vendorOptional = vendorRepository.findById(savedVendorId);

        assertTrue(vendorOptional.isPresent());

        Vendor retrievedVendor = vendorOptional.get();

        checkVendor(retrievedVendor, vendorName);
        assertNotNull(vendorOptional.get().getId());

    }

    @Test
    public void deleteVendorById() {

        Long vendorId = vendorRepository.findAll().get(0).getId();

        assertTrue(vendorRepository.findById(vendorId).isPresent());

        vendorRepository.deleteById(vendorId);

        assertFalse(vendorRepository.findById(vendorId).isPresent());

    }

    private void checkVendor(Vendor vendor, String expectedName) {

        assertNotNull(vendor);
        assertEquals(expectedName, vendor.getName());

    }

}
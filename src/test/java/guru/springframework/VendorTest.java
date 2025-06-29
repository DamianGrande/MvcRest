package guru.springframework;

import guru.springframework.api.v1.model.VendorDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class VendorTest {

    protected void checkDTO(VendorDTO dto, String expectedName, Long id) {
        checkDTO(dto, expectedName);
        assertEquals("/shop/vendors/" + id, dto.getUrl());
    }

    protected void checkDTO(VendorDTO dto, String expectedName, String expectedUrl) {
        checkDTO(dto, expectedName);
        assertEquals(expectedUrl, dto.getUrl());
    }

    private void checkDTO(VendorDTO dto, String expectedName) {
        assertNotNull(dto);
        assertEquals(expectedName, dto.getName());
    }


}

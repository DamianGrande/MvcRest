package guru.springframework.api.v1.mapper;

import guru.springframework.VendorTest;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VendorMapperTest extends VendorTest {

    private final VendorMapper mapper = Mappers.getMapper(VendorMapper.class);

    @Test
    public void fromEntityToDTO() {

        Long vendorId = 3L;
        String vendorName = "fromJeremy";

        Vendor vendor = new Vendor(vendorId, vendorName);

        VendorDTO vendorDTO = mapper.fromEntityToDTO(vendor);

        checkDTO(vendorDTO, vendorName, vendorId);

    }

    @Test
    public void fromEntityListToDTOList() {

        Long vendor1Id = 3L;
        String vendor1Name = "fromJeremy";

        Vendor vendor1 = new Vendor(vendor1Id, vendor1Name);

        Long vendor2Id = 6L;
        String vendor2Name = "AppleWorm";

        Vendor vendor2 = new Vendor(vendor2Id, vendor2Name);

        List<VendorDTO> dtos = mapper.fromEntityListToDTOList(Arrays.asList(vendor1, vendor2));

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        checkDTO(dtos.get(0), vendor1Name, vendor1Id);
        checkDTO(dtos.get(1), vendor2Name, vendor2Id);

    }

    @Test
    public void fromDTOToEntity() {

        String vendorName = "Some name";

        VendorDTO dto = new VendorDTO();
        dto.setName(vendorName);

        Vendor vendor = mapper.fromDTOToEntity(dto);

        assertNotNull(vendor);
        assertNull(vendor.getId());
        assertEquals(vendorName, vendor.getName());

    }

}
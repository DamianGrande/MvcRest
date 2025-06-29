package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface VendorMapper {

    @Mapping(target = "url", expression = "java(\"/shop/vendors/\" + vendor.getId())")
    VendorDTO fromEntityToDTO(Vendor vendor);

    List<VendorDTO> fromEntityListToDTOList(List<Vendor> vendors);

    Vendor fromDTOToEntity(VendorDTO vendorDTO);

}

package guru.springframework.service;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repository.VendorRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendorService {

    private final VendorRepository repository;
    private final VendorMapper mapper;

    @Autowired
    public VendorService(VendorRepository repository, VendorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public VendorDTO getVendorById(Long id) {
        return mapper.fromEntityToDTO(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public List<VendorDTO> getAllVendors() {
        List<Vendor> vendors = repository.findAll();
        return vendors.isEmpty() ? new ArrayList<>() : mapper.fromEntityListToDTOList(vendors);
    }

    public VendorDTO saveVendor(VendorDTO dto) {
        return mapper.fromEntityToDTO(repository.save(mapper.fromDTOToEntity(dto)));
    }

    public VendorDTO updateVendor(Long id, VendorDTO dto) {

        if (!repository.findById(id).isPresent())
            throw new ResourceNotFoundException();

        Vendor vendor = mapper.fromDTOToEntity(dto);
        vendor.setId(id);

        return mapper.fromEntityToDTO(repository.save(vendor));

    }

    public void deleteVendorById(Long id) {
        if (repository.findById(id).isPresent())
            repository.deleteById(id);
        else
            throw new ResourceNotFoundException();
    }

}

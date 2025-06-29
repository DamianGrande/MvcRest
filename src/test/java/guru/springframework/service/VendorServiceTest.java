package guru.springframework.service;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VendorServiceTest {

    @InjectMocks
    private VendorService service;

    @Mock
    private VendorRepository repository;

    @Mock
    private VendorMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getVendorById() {

        Long vendorId = 3L;
        String vendorName = "VegetablesAndFruits";

        Vendor vendor = new Vendor(vendorId, vendorName);
        VendorDTO vendorDTO = new VendorDTO();

        when(repository.findById(vendorId)).thenReturn(Optional.of(vendor));
        when(mapper.fromEntityToDTO(vendor)).thenReturn(vendorDTO);

        VendorDTO returnedDTO = service.getVendorById(vendorId);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).findById(vendorId);
        verify(mapper, times(1)).fromEntityToDTO(any(Vendor.class));
        verify(mapper, times(1)).fromEntityToDTO(vendor);

        assertNotNull(returnedDTO);
        assertEquals(vendorDTO, returnedDTO);

    }

    @Test
    public void getVendorByIdNotFound() {

        Long vendorId = 3L;

        when(repository.findById(vendorId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getVendorById(vendorId));

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).findById(vendorId);
        verify(mapper, times(0)).fromEntityToDTO(any(Vendor.class));

    }

    @Test
    public void geAllVendors() {

        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());
        List<VendorDTO> dtos = Arrays.asList(new VendorDTO(), new VendorDTO());

        when(repository.findAll()).thenReturn(vendors);
        when(mapper.fromEntityListToDTOList(vendors)).thenReturn(dtos);

        List<VendorDTO> returnedDTOs = service.getAllVendors();

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).fromEntityListToDTOList(anyList());
        verify(mapper, times(1)).fromEntityListToDTOList(vendors);

        assertNotNull(returnedDTOs);
        assertEquals(2, returnedDTOs.size());
        assertEquals(dtos, returnedDTOs);

    }

    @Test
    public void geAllVendorsEmptyList() {

        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<VendorDTO> returnedDTOs = service.getAllVendors();

        verify(repository, times(1)).findAll();
        verify(mapper, times(0)).fromEntityListToDTOList(anyList());

        assertNotNull(returnedDTOs);
        assertTrue(returnedDTOs.isEmpty());

    }

    @Test
    public void saveVendor() {

        VendorDTO dto = new VendorDTO();
        Vendor vendor = new Vendor();
        VendorDTO savedDTO = new VendorDTO();
        Vendor savedVendor = new Vendor();

        when(mapper.fromDTOToEntity(dto)).thenReturn(vendor);
        when(repository.save(vendor)).thenReturn(savedVendor);
        when(mapper.fromEntityToDTO(savedVendor)).thenReturn(savedDTO);

        VendorDTO returnedDTO = service.saveVendor(dto);

        verify(mapper, times(1)).fromDTOToEntity(any(VendorDTO.class));
        verify(mapper, times(1)).fromDTOToEntity(dto);
        verify(repository, times(1)).save(any(Vendor.class));
        verify(repository, times(1)).save(vendor);
        verify(mapper, times(1)).fromEntityToDTO(any(Vendor.class));
        verify(mapper, times(1)).fromEntityToDTO(savedVendor);

        assertNotNull(returnedDTO);
        assertEquals(returnedDTO, savedDTO);

    }


    @Test
    public void updateVendor() {

        Long vendorId = 69L;

        VendorDTO dto = new VendorDTO();
        Vendor vendor = new Vendor();
        VendorDTO savedDTO = new VendorDTO();
        Vendor savedVendor = new Vendor();

        when(repository.findById(vendorId)).thenReturn(Optional.of(new Vendor()));
        when(mapper.fromDTOToEntity(dto)).thenReturn(vendor);
        when(repository.save(vendor)).thenReturn(savedVendor);
        when(mapper.fromEntityToDTO(savedVendor)).thenReturn(savedDTO);

        VendorDTO returnedDTO = service.updateVendor(vendorId, dto);

        ArgumentCaptor<Vendor> vendorArgumentCaptor = ArgumentCaptor.forClass(Vendor.class);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).findById(vendorId);
        verify(mapper, times(1)).fromDTOToEntity(any(VendorDTO.class));
        verify(mapper, times(1)).fromDTOToEntity(dto);
        verify(repository, times(1)).save(vendorArgumentCaptor.capture());
        verify(mapper, times(1)).fromEntityToDTO(any(Vendor.class));
        verify(mapper, times(1)).fromEntityToDTO(savedVendor);

        Vendor capturedVendor = vendorArgumentCaptor.getValue();

        assertNotNull(capturedVendor);
        assertEquals(capturedVendor, vendor);
        assertEquals(vendorId, capturedVendor.getId());

        assertNotNull(returnedDTO);
        assertEquals(returnedDTO, savedDTO);

    }

    @Test
    public void updateVendorNotFound() {

        Long vendorId = 69L;

        when(repository.findById(vendorId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateVendor(vendorId, new VendorDTO()));

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).findById(vendorId);
        verify(mapper, times(0)).fromDTOToEntity(any(VendorDTO.class));
        verify(repository, times(0)).save(any(Vendor.class));
        verify(mapper, times(0)).fromEntityToDTO(any(Vendor.class));

    }

    @Test
    public void deleteVendorById() {

        Long vendorId = 69L;

        when(repository.findById(vendorId)).thenReturn(Optional.of(new Vendor()));

        service.deleteVendorById(vendorId);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).findById(vendorId);
        verify(repository, times(1)).deleteById(anyLong());
        verify(repository, times(1)).deleteById(vendorId);

    }

    @Test
    public void deleteVendorByIdNotFound() {

        Long vendorId = 69L;

        when(repository.findById(vendorId)).thenThrow(new ResourceNotFoundException());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteVendorById(vendorId));

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).findById(vendorId);
        verify(repository, times(0)).deleteById(anyLong());

    }

}
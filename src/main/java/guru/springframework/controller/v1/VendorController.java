package guru.springframework.controller.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService service;

    @Autowired
    public VendorController(VendorService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public VendorDTO getVendor(@PathVariable(name = "id") Long id) {
        return service.getVendorById(id);
    }

    @GetMapping
    public List<VendorDTO> getAllVendors() {
        return service.getAllVendors();
    }

    @PostMapping
    public VendorDTO saveVendor(@RequestBody VendorDTO dto) {
        return service.saveVendor(dto);
    }

    @PutMapping("/{id}")
    public VendorDTO updateVendor(@PathVariable(name = "id") Long id, @RequestBody VendorDTO dto) {
        return service.updateVendor(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteVendor(@PathVariable(name = "id") Long id) {
        service.deleteVendorById(id);
    }

}
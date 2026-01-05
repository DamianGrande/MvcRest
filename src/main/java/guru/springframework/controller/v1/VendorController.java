package guru.springframework.controller.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.service.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static guru.springframework.config.SwaggerConfig.VENDOR_TAG;

@RestController
@Api(tags = VENDOR_TAG)
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";
    private static final String VENDOR_NOT_FOUND_NOTE = "If the vendor is not found, status code 404 is returned.";

    private final VendorService service;

    @Autowired
    public VendorController(VendorService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve the vendor having the given id.", notes = VENDOR_NOT_FOUND_NOTE)
    public VendorDTO getVendor(@PathVariable(name = "id") Long id) {
        return service.getVendorById(id);
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all the vendors.")
    public List<VendorDTO> getAllVendors() {
        return service.getAllVendors();
    }

    @PostMapping
    @ApiOperation(value = "Save a new vendor.")
    public VendorDTO saveVendor(@RequestBody VendorDTO dto) {
        return service.saveVendor(dto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update the vendor with the given id.", notes = VENDOR_NOT_FOUND_NOTE)
    public VendorDTO updateVendor(@PathVariable(name = "id") Long id, @RequestBody VendorDTO dto) {
        return service.updateVendor(id, dto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete the vendor with the given id.", notes = VENDOR_NOT_FOUND_NOTE)
    public void deleteVendor(@PathVariable(name = "id") Long id) {
        service.deleteVendorById(id);
    }

}
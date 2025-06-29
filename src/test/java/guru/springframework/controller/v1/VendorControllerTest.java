package guru.springframework.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.VendorTest;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.service.ResourceNotFoundException;
import guru.springframework.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;


public class VendorControllerTest extends VendorTest {

    private MockMvc mvc;

    private ObjectMapper mapper;

    @InjectMocks
    private VendorController controller;

    @Mock
    private VendorService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void getVendorById() throws Exception {

        String vendorName = "AllFresh";
        String url = "someUrl";
        Long vendorId = 3L;

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(vendorName);
        vendorDTO.setUrl(url);

        when(service.getVendorById(vendorId)).thenReturn(vendorDTO);

        mvc.perform(get(VendorController.BASE_URL + "/" + vendorId)).andExpect(status().isOk()).andExpect(jsonPath("$.name", equalTo(vendorName))).andExpect(jsonPath("$.url", equalTo(url)));

        verify(service, times(1)).getVendorById(anyLong());
        verify(service, times(1)).getVendorById(vendorId);

    }

    @Test
    public void getVendorByIdNotFound() throws Exception {

        Long vendorId = 3L;

        when(service.getVendorById(vendorId)).thenThrow(new ResourceNotFoundException());

        MvcResult result = mvc.perform(get(VendorController.BASE_URL + "/" + vendorId)).andExpect(status().isNotFound()).andReturn();


        verify(service, times(1)).getVendorById(anyLong());
        verify(service, times(1)).getVendorById(vendorId);

        checkNotFoundResponse(result);

    }

    @Test
    public void getAllVendors() throws Exception {

        String vendor1Name = "JuicyFruits";
        String url1 = "vendor1url";

        VendorDTO dto1 = new VendorDTO();
        dto1.setName(vendor1Name);
        dto1.setUrl(url1);

        String vendor2Name = "JuicyHerbs";
        String url2 = "vendor2url";

        VendorDTO dto2 = new VendorDTO();
        dto2.setName(vendor2Name);
        dto2.setUrl(url2);

        when(service.getAllVendors()).thenReturn(Arrays.asList(dto1, dto2));

        MvcResult result = mvc.perform(get(VendorController.BASE_URL)).andExpect(status().isOk()).andReturn();

        verify(service, times(1)).getAllVendors();

        assertNotNull(result);

        byte[] responseAsBites = result.getResponse().getContentAsByteArray();

        assertNotNull(responseAsBites);

        @SuppressWarnings("unchecked")
        List<VendorDTO> dtos = mapper.readValue(responseAsBites, List.class);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        checkDTO(mapper.convertValue(dtos.get(0), VendorDTO.class), vendor1Name, url1);
        checkDTO(mapper.convertValue(dtos.get(1), VendorDTO.class), vendor2Name, url2);

    }

    @Test
    public void getAllVendorsEmptyList() throws Exception {

        when(service.getAllVendors()).thenReturn(new ArrayList<>());

        MvcResult result = mvc.perform(get(VendorController.BASE_URL)).andExpect(status().isOk()).andReturn();

        verify(service, times(1)).getAllVendors();

        assertNotNull(result);

        byte[] responseAsBites = result.getResponse().getContentAsByteArray();

        assertNotNull(responseAsBites);

        @SuppressWarnings("unchecked")
        List<VendorDTO> dtos = mapper.readValue(responseAsBites, List.class);

        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());

    }

    @Test
    public void saveVendor() throws Exception {

        String vendorName = "Some name";
        String savedVendorName = "Some saved name";
        String savedVendorUrl = "Some saved url";

        VendorDTO dto = new VendorDTO();
        dto.setName(vendorName);
        String jsonInput = mapper.writeValueAsString(dto);

        VendorDTO savedDTO = new VendorDTO();
        savedDTO.setName(savedVendorName);
        savedDTO.setUrl(savedVendorUrl);

        when(service.saveVendor(any(VendorDTO.class))).thenReturn(savedDTO);

        mvc.perform(post(VendorController.BASE_URL).contentType(MediaType.APPLICATION_JSON).content(jsonInput)).andExpect(status().isOk()).andExpect(jsonPath("$.name", equalTo(savedVendorName))).andExpect(jsonPath("$.url", equalTo(savedVendorUrl)));

        ArgumentCaptor<VendorDTO> vendorDTOArgumentCaptor = ArgumentCaptor.forClass(VendorDTO.class);

        verify(service, times(1)).saveVendor(vendorDTOArgumentCaptor.capture());

        VendorDTO capturedDTO = vendorDTOArgumentCaptor.getValue();

        assertNotNull(capturedDTO);
        assertEquals(vendorName, capturedDTO.getName());

    }

    @Test
    public void updateVendor() throws Exception {

        Long vendorId = 69L;
        String vendorName = "Some name";
        String savedVendorName = "Some saved name";
        String savedVendorUrl = "Some saved url";

        VendorDTO dto = new VendorDTO();
        dto.setName(vendorName);
        String jsonInput = mapper.writeValueAsString(dto);

        VendorDTO savedDTO = new VendorDTO();
        savedDTO.setName(savedVendorName);
        savedDTO.setUrl(savedVendorUrl);

        when(service.updateVendor(anyLong(), any(VendorDTO.class))).thenReturn(savedDTO);

        mvc.perform(put(VendorController.BASE_URL + "/" + vendorId).contentType(MediaType.APPLICATION_JSON).content(jsonInput)).andExpect(status().isOk()).andExpect(jsonPath("$.name", equalTo(savedVendorName))).andExpect(jsonPath("$.url", equalTo(savedVendorUrl)));

        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<VendorDTO> vendorDTOArgumentCaptor = ArgumentCaptor.forClass(VendorDTO.class);

        verify(service, times(1)).updateVendor(longArgumentCaptor.capture(), vendorDTOArgumentCaptor.capture());

        Long capturedId = longArgumentCaptor.getValue();
        VendorDTO capturedDTO = vendorDTOArgumentCaptor.getValue();

        assertNotNull(capturedId);
        assertEquals(vendorId, capturedId);
        assertNotNull(capturedDTO);
        assertEquals(vendorName, capturedDTO.getName());

    }

    @Test
    public void updateVendorNotFound() throws Exception {


        VendorDTO dto = new VendorDTO();
        String jsonInput = mapper.writeValueAsString(dto);


        when(service.updateVendor(anyLong(), any(VendorDTO.class))).thenThrow(new ResourceNotFoundException());

        MvcResult result = mvc.perform(put(VendorController.BASE_URL + "/" + 69).contentType(MediaType.APPLICATION_JSON).content(jsonInput)).andExpect(status().isNotFound()).andReturn();

        verify(service, times(1)).updateVendor(anyLong(), any(VendorDTO.class));

        checkNotFoundResponse(result);

    }

    @Test
    public void deleteVendor() throws Exception {

        Long vendorId = 69L;

        mvc.perform(delete(VendorController.BASE_URL + "/" + vendorId)).andExpect(status().isOk());

        verify(service, times(1)).deleteVendorById(anyLong());
        verify(service, times(1)).deleteVendorById(vendorId);

    }

    @Test
    public void deleteVendorNotFound() throws Exception {

        Long vendorId = 69L;

        doThrow(new ResourceNotFoundException()).when(service).deleteVendorById(vendorId);

        MvcResult result = mvc.perform(delete(VendorController.BASE_URL + "/" + vendorId)).andExpect(status().isNotFound()).andReturn();

        verify(service, times(1)).deleteVendorById(anyLong());
        verify(service, times(1)).deleteVendorById(vendorId);

        checkNotFoundResponse(result);

    }

    private void checkNotFoundResponse(MvcResult result) {

        assertNotNull(result);

        byte[] responseAsBites = result.getResponse().getContentAsByteArray();

        assertNotNull(responseAsBites);

        assertEquals("Resource Not Found", new String(responseAsBites));

    }


}
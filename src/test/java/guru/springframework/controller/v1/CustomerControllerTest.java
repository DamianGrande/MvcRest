package guru.springframework.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerDTOWithUrl;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    @Mock
    private CustomerService service;

    @InjectMocks
    private CustomerController controller;

    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllCustomers() throws Exception {

        CustomerDTOWithUrl dto = new CustomerDTOWithUrl();
        dto.setCustomerUrl("some url");
        dto.setFirstName("Mia");
        dto.setLastName("Lovefield");

        List<CustomerDTOWithUrl> customers = Collections.singletonList(dto);

        when(service.getAllCustomers()).thenReturn(customers);

        MvcResult mvcResult = mvc.perform(get("/api/v1/customers/")).andExpect(status().isOk()).andReturn();

        verify(service, times(1)).getAllCustomers();

        CustomerListDTO returnedListDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), CustomerListDTO.class);

        assertNotNull(returnedListDTO);

        List<CustomerDTOWithUrl> returnedCustomers = returnedListDTO.getCustomers();

        assertNotNull(returnedCustomers);
        assertEquals(1, returnedCustomers.size());

        CustomerDTOWithUrl returnedDTO = returnedCustomers.get(0);

        assertNotNull(returnedDTO);
        assertEquals("some url", returnedDTO.getCustomerUrl());
        assertEquals("Mia", returnedDTO.getFirstName());
        assertEquals("Lovefield", returnedDTO.getLastName());

    }

    @Test
    public void getCustomer() throws Exception {

        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName("Mia");
        dto.setLastName("Lovefield");

        when(service.getCustomerById(anyLong())).thenReturn(dto);

        MvcResult mvcResult = mvc.perform(get("/api/v1/customers/69")).andExpect(status().isOk()).andReturn();

        verify(service, times(1)).getCustomerById(anyLong());
        verify(service, times(1)).getCustomerById(69L);

        CustomerDTO returnedDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsByteArray(), CustomerDTO.class);

        assertNotNull(returnedDTO);
        assertEquals("Mia", returnedDTO.getFirstName());
        assertEquals("Lovefield", returnedDTO.getLastName());

    }

    @Test
    public void createCustomer() throws Exception {

        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");
        customer.setLastName("Flintstone");

        CustomerDTOWithUrl returnedDTO = new CustomerDTOWithUrl();
        returnedDTO.setFirstName(customer.getFirstName());
        returnedDTO.setLastName(customer.getLastName());
        returnedDTO.setCustomerUrl("/api/v1/customers/1");

        when(service.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnedDTO);

        mvc.perform(post("/api/v1/customers/").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(customer))).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", equalTo("Fred"))).andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));

    }

    @Test
    public void updateCustomer() throws Exception {

        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");
        customer.setLastName("Flintstone");

        CustomerDTOWithUrl returnedDTO = new CustomerDTOWithUrl();
        returnedDTO.setFirstName(customer.getFirstName());
        returnedDTO.setLastName(customer.getLastName());
        returnedDTO.setCustomerUrl("/api/v1/customers/1");

        when(service.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnedDTO);

        mvc.perform(put("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(customer))).andExpect(status().isOk()).andExpect(jsonPath("$.firstName", equalTo("Fred"))).andExpect(jsonPath("$.lastName", equalTo("Flintstone"))).andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));

    }

    @Test
    public void patchCustomer() throws Exception {

        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");

        CustomerDTOWithUrl returnedDTO = new CustomerDTOWithUrl();
        returnedDTO.setFirstName(customer.getFirstName());
        returnedDTO.setLastName("Flintstone");
        returnedDTO.setCustomerUrl("/api/v1/customers/1");

        when(service.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnedDTO);

        mvc.perform(patch("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(customer))).andExpect(status().isOk()).andExpect(jsonPath("$.firstName", equalTo("Fred"))).andExpect(jsonPath("$.lastName", equalTo("Flintstone"))).andExpect(jsonPath("$.customerUrl", equalTo("/api/v1/customers/1")));

    }

    @Test
    public void deleteCustomer() throws Exception {

        mvc.perform(delete("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        verify(service, times(1)).deleteCustomerById(anyLong());
        verify(service, times(1)).deleteCustomerById(1L);

    }

}
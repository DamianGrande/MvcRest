package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerDTOWithUrl;
import guru.springframework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerUrl", expression = "java(\"/shop/customers/\" + customer.getId())")
    CustomerDTOWithUrl fromCustomerToCustomerDTOWithUrl(Customer customer);

    CustomerDTO fromCustomerToCustomerDTO(Customer customer);

    Customer fromCustomerDTOtoCustomer(CustomerDTO customerDTO);

    List<CustomerDTOWithUrl> fromCustomersToCustomerDTOs(List<Customer> customers);

}

package com.ing.fundtransfer.controllertest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.fundtransfer.controller.CustomerController;
import com.ing.fundtransfer.dto.CustomerRequestDto;
import com.ing.fundtransfer.dto.CustomerResponseDto;
import com.ing.fundtransfer.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class CustomerControllerTest {

	@InjectMocks
	CustomerController customerController;

	@Mock
	CustomerService customerService;

	CustomerResponseDto customerResponseDto;

	CustomerRequestDto customerRequestDto;

	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
		customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setFirstName("Krishna");
	}

	@Test
	public void testRegister() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/customer/save").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(customerRequestDto)))
				.andExpect(status().isCreated());
	}

	public static String asJsonString(final Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}

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
import com.ing.fundtransfer.controller.BeneficiaryController;
import com.ing.fundtransfer.dto.BeneficiaryRequestDto;
import com.ing.fundtransfer.service.BeneficiaryService;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class BenefeciaryControllerTest {

	@InjectMocks
	BeneficiaryController beneficiaryController;

	@Mock
	BeneficiaryService beneficiaryService;

	BeneficiaryRequestDto beneficiaryRequestDto;

	MockMvc mockMvc;

	Long customerId;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(beneficiaryController).build();
		beneficiaryRequestDto = new BeneficiaryRequestDto();
		beneficiaryRequestDto.setBeneficiaryAccountNumber("16536700449235");
		beneficiaryRequestDto.setBeneficiaryName("Jyoshna");
		beneficiaryRequestDto.setCustomerId(1L);
		customerId = 1L;
	}

	@Test
	public void testAddBeneficiary() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beneficiary/save").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(beneficiaryRequestDto)))
				.andExpect(status().isCreated());
	}

	@Test
	public void testGetAllBeneficiary() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/beneficiary/view/{customerId}", 1L)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	public static String asJsonString(final Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}

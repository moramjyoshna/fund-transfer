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
import com.ing.fundtransfer.controller.AccountController;
import com.ing.fundtransfer.dto.AccountSummaryResponseDto;
import com.ing.fundtransfer.service.AccountSummaryService;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class AccountControllerTest {

	@InjectMocks
	AccountController accountController;

	@Mock
	AccountSummaryService accountSummaryService;

	AccountSummaryResponseDto accountSummaryResponseDto;

	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc=MockMvcBuilders.standaloneSetup(accountController).build();
		accountSummaryResponseDto = new AccountSummaryResponseDto();
		accountSummaryResponseDto.setAccountId(1L);
		accountSummaryResponseDto.setAccountNumber("84053768334955");
		accountSummaryResponseDto.setAccountType("savings");
		accountSummaryResponseDto.setBalance(2000D);

	}

	@Test
	public void testGetAccountSummary() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/customer/{accountNumber}", "84053768334955")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

//	public static String asJsonString(final Object object) {
//		try {
//			return new ObjectMapper().writeValueAsString(object);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//
//	}
}

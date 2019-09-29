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
import com.ing.fundtransfer.controller.TransactionController;
import com.ing.fundtransfer.dto.TransferAmountRequestDto;
import com.ing.fundtransfer.service.TransactionService;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class TransactionControllerTest {

	@InjectMocks
	TransactionController transactionCOntroller;

	@Mock
	TransactionService transactionService;

	TransferAmountRequestDto transferAmountRequestDto;

	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionCOntroller).build();
		transferAmountRequestDto = new TransferAmountRequestDto();
		transferAmountRequestDto.setCreditTo("16536700449235");
		transferAmountRequestDto.setCustomerId(1L);
		transferAmountRequestDto.setTransferAmount(1000D);
	}

	@Test
	public void testAmountTransfer() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/customer/transfer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(transferAmountRequestDto)))
				.andExpect(status().isOk());
	}

	@Test
	public void testViewWeekTransaction() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/customer/transaction/{accountNumber}", "84053768334955")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void testViewMonthTransaction() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/customer/transaction/{accountNumber}/{month}/{year}", "84053768334955", "SEPTEMBER", 2019)
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

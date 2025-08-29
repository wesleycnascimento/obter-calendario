package com.example.obtercalendario;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ObterCalendarioController.class)
public class ObterCalendarioControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObterCalendarioController controller;

	private SecretClient secretClient;

	@BeforeEach
	void setUp() throws Exception {
		secretClient = Mockito.mock(SecretClient.class);
		Field field = ObterCalendarioController.class.getDeclaredField("secretClient");
		field.setAccessible(true);
		field.set(controller, secretClient);
	}

	@Test
	void testGetCalendarioSuccess() throws Exception {
		KeyVaultSecret mockSecret = Mockito.mock(KeyVaultSecret.class);
		when(secretClient.getSecret("PersonName")).thenReturn(mockSecret);
		when(mockSecret.getValue()).thenReturn("TestUser");

		String expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		String expectedResponse = "TestUser, today is " + expectedDate;

		mockMvc.perform(get("/obterCalendario"))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedResponse));
	}

	@Test
	void testGetCalendarioSecretNotFound() throws Exception {
		doThrow(new RuntimeException("Secret not found")).when(secretClient).getSecret(anyString());

		String expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		String expectedResponse = "Unknown user (secret not found), today is " + expectedDate;

		mockMvc.perform(get("/obterCalendario"))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedResponse));
	}

	@Test
	void testExposeGetSecretClient() throws Exception {
		// Set the field to null to force getSecretClient() to run its logic
		Field field = ObterCalendarioController.class.getDeclaredField("secretClient");
		field.setAccessible(true);
		field.set(controller, null);

		SecretClient client = controller.exposeGetSecretClient();
		assertNotNull(client, "SecretClient should not be null");
	}
}
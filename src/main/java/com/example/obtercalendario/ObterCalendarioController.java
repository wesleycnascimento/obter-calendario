package com.example.obtercalendario;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class ObterCalendarioController {

    private final SecretClient secretClient;

    @Value("${azure.keyvault.uri}")
    private String keyVaultUri;

    public ObterCalendarioController() {
        this.secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }

    @GetMapping("/obterCalendario")
    public String getCalendario() {
        String name;
        try {
            KeyVaultSecret secret = secretClient.getSecret("PersonName");

            name = secret.getValue();
        } catch (Exception e) {
            name = "Unknown user (secret not found)";
        }

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return String.format("%s, today is %s", name, date);
    }
}
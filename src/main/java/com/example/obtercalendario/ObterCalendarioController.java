package com.example.obtercalendario;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class ObterCalendarioController {

    // Lazy initialization
    private SecretClient secretClient;

    private SecretClient getSecretClient() {
        if (secretClient == null) {
            secretClient = new SecretClientBuilder()
                    .vaultUrl("https://kv-obtercalendario.vault.azure.net") // your vault
                    .credential(new DefaultAzureCredentialBuilder().build())
                    .buildClient();
        }
        return secretClient;
    }

    @GetMapping("/obterCalendario")
    public String getCalendario() {
        String name;

        try {
            KeyVaultSecret secret = getSecretClient().getSecret("PersonName");

            name = secret.getValue();
        } catch (Exception e) {
            name = "Unknown user (secret not found)";
        }

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return String.format("%s, today is %s", name, date);
    }

    SecretClient exposeGetSecretClient() {
        return getSecretClient();
    }
}
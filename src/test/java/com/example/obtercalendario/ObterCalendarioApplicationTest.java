package com.example.obtercalendario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ObterCalendarioApplicationTest {

    @Test
    void contextLoads() {
        // Verifies that the Spring context loads successfully
    }

    @Test
    void testMainMethod() {
        // Calls the main method to cover SpringApplication.run(...)
        ObterCalendarioApplication.main(new String[] {});
    }
}
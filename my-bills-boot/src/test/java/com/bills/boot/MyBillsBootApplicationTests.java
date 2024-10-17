package com.bills.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyBillsBootApplicationTests {

    @DynamicPropertySource
    @Description("Dinamik olarak rastgele bir port kullanmak için server.port değerini ayarlar.")
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> 0);
    }

	@Test
    @Description("Spring context'in sorunsuz bir şekilde yüklendiğini doğrular.")
    void contextLoads() {
	}

    @Test
    @Description("MyBillsBootApplication ana methodunu çalıştırarak uygulamanın sorunsuz başlatılabildiğini doğrular.")
    void testMain() {
        MyBillsBootApplication.main(new String[] {});
    }

}

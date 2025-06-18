package com.joga.app.configserver;

import static org.mockito.Mockito.mockStatic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@ExtendWith(MockitoExtension.class)
class ConfigServerApplicationTests {

    @Test
    void test(){
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {

            mocked.when(() -> { SpringApplication.run(ConfigServerApplication.class,
                    "foo", "bar"); })
                .thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

            ConfigServerApplication.main(new String[] { "foo", "bar" });

            mocked.verify(() -> { SpringApplication.run(ConfigServerApplication.class,
                "foo", "bar"); });

        }
    }
}

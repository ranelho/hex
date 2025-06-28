package com.rlti.hex.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "com.rlti.hex.adapters.mapper")
public class TestMapperConfig {
    // Esta configuração garante que os mappers do MapStruct sejam encontrados durante os testes
}
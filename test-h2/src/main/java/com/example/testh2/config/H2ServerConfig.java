package com.example.testh2.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@Profile("local")
public class H2ServerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server H2DatabaseServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
    /*
        만약 위 코드에서 오류가 발생한다면 다음과 같이 시도 해볼 것
        @Bean
        @ConfigurationProperties("spring.datasource.hikari")
        public DataSource dataSource() throws SQLException {
            Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
            return new HikariDataSource();
        }
    */
}

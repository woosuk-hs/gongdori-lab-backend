package hs.woosuk.gongdorilab.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DotenvConfig {

    @Bean
    fun dotenv() = io.github.cdimascio.dotenv.dotenv()
}
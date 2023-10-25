package ru.netology.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:8081")
                .allowedMethods("*");
    }




 //   @Bean
 //   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 //       HttpSecurity httpSecurity = http
 //               .authorizeHttpRequests(
 //                       (authorize) -> authorize
 //                               .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
 //                               .requestMatchers().permitAll())
 //               .httpBasic(Customizer.withDefaults())
 //               .formLogin(Customizer.withDefaults());
 //       return http.build();
 //   }
}





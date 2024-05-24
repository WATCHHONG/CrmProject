package org.crm.crmproject.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.crm.crmproject.config.handler.Custom403Handler;
import org.crm.crmproject.security.CrmUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.io.IOException;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class CustomSecurityConfig {

    private final DataSource dataSource;
    private final CrmUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("--------------------configure-------------------");

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login", "/service", "/resources/**", "/ceo/join", "/customer/join").permitAll()
                        .requestMatchers("/ceo/**").hasRole("CEO")
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
            )
            .formLogin(form ->{form.loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    // 사용자 권한을 로그로 출력
                    authentication.getAuthorities().forEach(authority -> {
                        System.out.println("Authority: " + authority.getAuthority());
                    });
                    // 권한을 String 으로 비교
                    boolean isCustomer = authentication.getAuthorities().stream()
                            .anyMatch(authority -> authority.getAuthority().equals("ROLE_CUSTOMER"));
                    boolean isCeo = authentication.getAuthorities().stream()
                            .anyMatch(authority -> authority.getAuthority().equals("ROLE_CEO"));

                    if (isCustomer) {
                        System.out.println("authentication: " + authentication.getName());
                        response.sendRedirect("/customer/update");
                    } else if (isCeo) {
                        System.out.println("authentication: " + authentication.getName());
                        response.sendRedirect("/ceo/update");
                    }
                }).permitAll();
            })
            .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/index")
                        .permitAll()
            );

//        http
//                .authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/", "/login", "/service", "/resources/**", "/create").permitAll()
//                .requestMatchers("/ceo").hasRole("CEO")
//                .requestMatchers("/customer").hasRole("CUSTOMER")
//                .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/ceo/update", true)
//                        .failureHandler(failureHandler)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                        .permitAll()
//                );


        http.csrf(AbstractHttpConfigurer::disable);  // CSRF 토큰 비활성화

        //remember-me 설정
        http.rememberMe(httpSecurityRememberMeConfigurer -> {
            httpSecurityRememberMeConfigurer.key("123456789")
                    .tokenRepository(persistentTokenRepository())
                    .userDetailsService(customUserDetailsService)
                    .tokenValiditySeconds(60*5);     // 5분
        });

        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler());
        });


        return http.build();
    }


    @Bean   // 자동로그인 관련
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);

        return repo;
    }

    @Bean   // 정적 리소스 필터링 제외
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("-------------------- web configure  -------------------");

        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources()
                .atCommonLocations()));
    }

    @Bean   // 엑세스 디나이드 핸들러
    public AccessDeniedHandler accessDeniedHandler() {

        return new Custom403Handler();
    }

}

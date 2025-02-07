package com.test.zoom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.zoom.data.Db;
import com.test.zoom.entity.Auth;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//spring boot의 주요원칙인 컴포넌트 기반 설정을 유지하기위해 extands WebSecurityConfigurerAdapter 구조는 deprecated 하고, @Bean 방식을 사용한다.
//Spring Security 5.7 이후로 @EnableWebSecurity 는 더 이상 필수적이지 않다.
@Configuration
public class SecurityConfig {

    /** B. formLogin 미사용시, Controller 에서 AuthenticationProvider 를 사용하기위해 빈 등록함. **/
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //------------------------------------------------------------------------------------------------------- 빈 등록.

    //서버메모리에 임의의 사용자를 등록함
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername(Db.user.getUserName())
//                .password("{noop}"+Db.user.getAuthName()) // {noop} -> 암호화 안 함 (테스트용)
                .password(passwordEncoder().encode(Db.user.getAuthName().toString())) // BCrypt로 암호화
                .roles(Db.user.getAuthName().name())
                .build();
        UserDetails me = User.withUsername(Db.me.getUserName())
                .password(passwordEncoder().encode(Db.me.getAuthName().toString()))
                .roles(Db.me.getAuthName().name())
                .build();

        return new InMemoryUserDetailsManager(user, me);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 허용
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                // 2. H2 Console은 CSRF 보호를 비활성화해야 정상 작동합니다.
                .csrf(AbstractHttpConfigurer::disable)
                // 3. enable h2-console
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                //1. 요청 수준에서 권한을 모델링함.
                .authorizeHttpRequests((authorize) -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll() // request 외 디스패처 유형 인증 불필요
                        .requestMatchers("/h2-console/**").permitAll() // 브라우저에서 사용하는 DB인 h2에 콘솔로 접근할 때 인증 불필요 (기본 경로는 /h2-console)
                        .requestMatchers("/resources/**").permitAll()
                        .requestMatchers("/favicon.ico").permitAll() // 페이지를 대표하는 아이콘
//                        .requestMatchers("/db/read").hasRole("USER")// 권한 인증이 필요
                        .requestMatchers("/auth/login").permitAll()// 지정 엔드포인트 인증 불필요
                        .anyRequest().authenticated() // 그외 모든 엔드포인트 인증필요
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 유지 (STATELESS가 아님)
                )

        //A. formLogin 사용 : 시큐리티에서 사용자 확인하도록 구성. x-www-form-urlencoded 타입의 requestParam 이 있어야 검증 가능
                .formLogin(login -> login
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication)
                                -> onAuthenticationSuccess(response,authentication)).permitAll()
                );

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:5173");  // Vue 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setMaxAge(3600L);
        configuration.addAllowedOriginPattern("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    /** A. formLogin 사용시, 성공 후 리다이렉트 없이 JSON 데이터를 전달하기 위해 성공핸들러를 재 정의함.
     * 주의 - 리다이렉트 하지 않기! **/
    public void onAuthenticationSuccess(HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        System.out.println("onAuthenticationSuccess: " + authentication);

        // 인증된 사용자 정보을 User 엔티티로 변환한 뒤 JSON 으로 반환함.
        User securityUser = (User) authentication.getPrincipal();
        String securityAuth = securityUser.getAuthorities().toArray()[0].toString().replace("ROLE_","");

        com.test.zoom.entity.User user = new com.test.zoom.entity.User();
        user.setUserName(securityUser.getUsername());
        user.setPassword(securityUser.getPassword());
        user.setAuthName(Auth.valueOf(securityAuth));

        String jsonResponse = new ObjectMapper().writeValueAsString(user);
        System.out.println("onAuthenticationSuccess: " + jsonResponse);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
    //-------------------------------------------------------------------------------------- 로그인 성공후 리다이렉트 안함.
}

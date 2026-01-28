package in.tech_camp.chat_app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizeRequest -> authorizeRequest
          //ログアウト状態でも実行できるGETリクエスト
          .requestMatchers("/css/**", "/users/sign_up", "/users/login").permitAll()
          //ログアウト状態でも実行できるPOSTメソッド
          .requestMatchers(HttpMethod.POST, "/user").permitAll()
          //上記以外は要ログイン
          .anyRequest().authenticated())

        .formLogin(login -> login
          //ログインフォームのボタンクリック時の送信パス
          .loginProcessingUrl("/login")
          //ログインページのパス設定
          .loginPage("/users/login")
          //ログイン成功時のリダイレクト先
          .defaultSuccessUrl("/", true)
          //ログイン失敗時のリダイレクト先
          .failureUrl("/login?error")
          .usernameParameter("email")
          .permitAll())

        .logout(logout -> logout
          .logoutUrl("/logout")
          //ログアウト成功時のリダイレクト先
          .logoutSuccessUrl("/users/login"));

    return http.build();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}

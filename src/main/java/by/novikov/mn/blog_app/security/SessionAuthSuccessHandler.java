package by.novikov.mn.blog_app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Optional;

public class SessionAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public SessionAuthSuccessHandler() {
        setDefaultTargetUrl("/"); // Аналог defaultSuccessUrl из SecurityConfig
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        HttpSession session = request.getSession();

        // Получаем данные пользователя
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // Сохраняем ID пользователя
            session.setAttribute("CURRENT_USER_ID", userDetails.getId());


            String role = userDetails.getAuthorities().stream()
                            .findFirst()
                            .map(grantedAuthority -> grantedAuthority.getAuthority())
                            .get();

            // Сохраняем роли как коллекцию строк (у нас м.б. одна роль)
            session.setAttribute("CURRENT_USER_ROLE", role);
        }

        // Вызываем стандартную обработку (редирект на сохранённый URL или "/")
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

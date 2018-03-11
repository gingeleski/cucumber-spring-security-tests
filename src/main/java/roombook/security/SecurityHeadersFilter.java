package roombook.security;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityHeadersFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        // POST requests should have a Content-Type header
        if (request.getMethod() == HttpMethod.POST.name() && request.getHeader("Content-Type") == null)
        {
            response.sendError(HttpStatus.BAD_REQUEST.value());
        }

        response.addHeader("Content-Security-Policy", "default-src: 'self'");
        filterChain.doFilter(request, response);
    }
}

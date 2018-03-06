package roombook.filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public class UrlSessionFilter implements Filter
{
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException
    {
        if (false == request instanceof HttpServletRequest)
        {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.isRequestedSessionIdFromURL())
        {
            /*
            HttpSession session = httpRequest.getSession();
            if (session != null) session.invalidate();
            */
        }

        HttpServletResponseWrapper wrappedResponse
                = new HttpServletResponseWrapper(httpResponse)
        {
            public String encodeRedirectUrl(String url) { return url; }
            public String encodeRedirectURL(String url) { return url; }
            public String encodeUrl(String url) { return url; }
            public String encodeURL(String url) { return url; }
        };

        chain.doFilter(request, wrappedResponse);
    }

    public void init(FilterConfig config)
            throws ServletException {}

    public void destroy() {}
}

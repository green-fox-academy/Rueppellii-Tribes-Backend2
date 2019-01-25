package rueppellii.backend2.tribes.security.jwt;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import rueppellii.backend2.tribes.message.response.ErrorResponse;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e)
            throws IOException, ServletException {

        ErrorResponse errorResponse = new ErrorResponse();
        ObjectMapper mapper = new ObjectMapper();
        errorResponse.setStatus("error");
        errorResponse.setMessage("Wrong Password!");
        String responseMsg = mapper.writeValueAsString(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(responseMsg);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

package br.com.RestauranteRioBranco.security.jwt;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
	
	@Autowired
    private JwtUtils jwtUtils;

	@Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest req = servletRequest.getServletRequest();
            String token = req.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.replace("Bearer ", "");

                try {
                    String email = jwtUtils.getUsernameToken(jwt);
                    attributes.put("email", email);
                    return true;
                } catch (Exception e) {
                	System.out.println("Erro");
                    return false;
                }
            }
        }

        return false;
    }

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}

}

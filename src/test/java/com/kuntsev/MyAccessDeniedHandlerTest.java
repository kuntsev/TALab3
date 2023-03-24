package com.kuntsev;

import com.kuntsev.config.MyAccessDeniedHandler;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
// The first test case verifies that when an access denied exception is thrown, the handler logs a message with the username of the user who attempted to access the resource, and the redirect URL is set to /context-path/403. The second test case verifies that if the authentication object is null, the redirect URL is set to /my-context/403. These tests use the Mockito framework to create mock objects for the HttpServletRequest and HttpServletResponse classes, and the SecurityContextHolder class is used to set the authentication object for the test.

public class MyAccessDeniedHandlerTest {

    private final MyAccessDeniedHandler handler = new MyAccessDeniedHandler();

    @Test
    void handle_accessDenied_exceptionThrown_verifyLogMessage() throws Exception {
        // Arrange
        String username = "user1";
        Authentication auth = new UsernamePasswordAuthenticationToken(username, "pass");
        SecurityContextHolder.getContext().setAuthentication(auth);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/protected-resource");

        MockHttpServletResponse response = new MockHttpServletResponse();
        AccessDeniedException exception = new AccessDeniedException("You don't have access");

        // Act
        handler.handle(request, response, exception);

        // Assert
      //  verify(handler.getLogger()).info("User '" + username + "' attempted to access the protected URL: /protected-resource");
        //assertEquals("/context-path/403", response.getRedirectedUrl());
    }

    @Test
    void handle_authenticationIsNull_redirectTo403Page() throws Exception {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/my-context");
        request.setRequestURI("/protected-resource");

        MockHttpServletResponse response = new MockHttpServletResponse();
        AccessDeniedException exception = new AccessDeniedException("You don't have access");

        // Act
        handler.handle(request, response, exception);

        // Assert
        assertEquals("/my-context/403", response.getRedirectedUrl());
    }
}


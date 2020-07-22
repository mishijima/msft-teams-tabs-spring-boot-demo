package com.example.teams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SessionController {
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    public SessionController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/oauth_login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("oauth_login");
    }

    @GetMapping("/oauth_loggedIn")
    public ModelAndView showLoggedInPage() {
        return new ModelAndView("oauth_loggedIn");
    }

    @GetMapping("/secured")
    public ModelAndView showSecuredPage(OAuth2AuthenticationToken authentication) {
        final Map<String, String> map = new HashMap<>();
        final OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        map.put("name", client.getPrincipalName());

        return new ModelAndView("secured", map);
    }
}

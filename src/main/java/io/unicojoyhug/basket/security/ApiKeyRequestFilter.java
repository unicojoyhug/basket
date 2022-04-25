package io.unicojoyhug.basket.security;

import io.swagger.v3.oas.models.parameters.Parameter;
import io.unicojoyhug.basket.repositories.ApiKey;
import io.unicojoyhug.basket.repositories.ApiKeyRepository;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class ApiKeyRequestFilter extends GenericFilterBean {

    private ApiKeyRepository apiKeyRepository;

    public ApiKeyRequestFilter(ApiKeyRepository apiKeyRepository){
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String getPath = req.getRequestURI();

        if(!getPath.startsWith("/api")){
            chain.doFilter(request, response);
            return;
        }

        String key = req.getHeader("Key") == null ? "" : req.getHeader("Key");

        Optional<ApiKey> apiKeyOptional = this.apiKeyRepository.findOneByKey(key);
        if(apiKeyOptional.isPresent()){
            chain.doFilter(request, response);
        }else{
            HttpServletResponse resp = (HttpServletResponse) response;
            String error = "Invalid API KEY";

            resp.reset();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentLength(error .length());
            response.getWriter().write(error);
        }

    }

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> operation.addParametersItem(
                new Parameter()
                        .in("header")
                        .required(true)
                        .description("apiKey")
                        .name("Key"));
    }
}
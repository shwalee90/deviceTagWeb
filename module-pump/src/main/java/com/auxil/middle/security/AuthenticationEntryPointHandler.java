package com.auxil.middle.security;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException ,ServletException{

        String exception = (String) request.getAttribute("exception");
        ErrorCode errorCode;

        if(exception == null){
            errorCode = ErrorCode.UNAUTHORIZEDException;
            setResponse(response,errorCode);
            return;
        }

        if(exception.equals("ExpiredJwtException")){
            errorCode = ErrorCode.ExpiredJwtException;
            setResponse(response , errorCode);
            return;
        }



    }


    private void setResponse( HttpServletResponse response, ErrorCode errorCode) throws IOException  {
        JsonObject json = new JsonObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.addProperty("code" , errorCode.getCode());
        json.addProperty("message" , errorCode.getMessage());

        response.getWriter().print(json);

    }
}
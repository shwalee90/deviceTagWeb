package com.auxil.tagweb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class AuthDTO {
    /*
      로그인시 사용하는 dto
     */

    @Getter
    @Setter
    public static class LOGINDTO {
        @ApiModelProperty(value = "아이디" ,  example =  "shwalee" , required = true)
        private String name;

        @ApiModelProperty(value = "비밀번호" ,example = "12345" , required = true)
        private String password;


    }


    //refresh token 을 사용해 새로운 accesstoken 발급 받을 때 사용하는 dto
    @Getter
    @Setter
    public static class GetNewAccessTokenDTO {
        @ApiModelProperty(value = "refresh token index" ,  example =  "1" , required = true)
        private long refreshIdx;


    }



}

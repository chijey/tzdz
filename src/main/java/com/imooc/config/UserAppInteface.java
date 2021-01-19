package com.imooc.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
public class UserAppInteface {

    public static final String PROGRAM_ID="wxb3467842ead8b165";

    public static final String PROGRAM_SECRET="5510af4163da6541a2cd961dbbe71f03";

    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    public static final String WX_LOGIN_GRANT_TYPE = "authorization_code";

}

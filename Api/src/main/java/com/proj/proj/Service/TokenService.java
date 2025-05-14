package com.proj.proj.Service;

import com.proj.proj.Model.AccessToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    private final Map<String, AccessToken> tokensMap = new HashMap<>();

    public void storeToken(String token, AccessToken accessToken) {
        tokensMap.put(token, accessToken);
    }



    public AccessToken getToken(String token) {
        return tokensMap.get(token);
    }
}

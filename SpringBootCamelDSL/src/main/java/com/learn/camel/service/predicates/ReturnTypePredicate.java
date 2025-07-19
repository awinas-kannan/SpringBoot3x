package com.learn.camel.service.predicates;

import org.springframework.stereotype.Component;

@Component
public class ReturnTypePredicate {

    public boolean isDamaged(String body) {
        return body != null && body.contains("type=damaged");
    }

    public boolean isExpired(String body) {
        return body != null && body.contains("type=expired");
    }
}

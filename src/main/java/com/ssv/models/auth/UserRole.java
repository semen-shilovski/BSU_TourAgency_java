package com.ssv.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum UserRole {
    GUEST,
    USER,
    ADMIN
}

package com.aiken.pos.admin.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-May-31
 */

public class BlockedUserException extends AuthenticationException {
    public BlockedUserException(String msg) {
        super(msg);
    }
}

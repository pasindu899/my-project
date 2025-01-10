package com.aiken.pos.admin.util.response;

import com.aiken.common.util.validation.StringUtil;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-May-29
 */

    public class GenericResponse {
        private Integer code;
        private String response;
        private String description;
        private String type = "default";

    public Integer getCode() {
        return code;
    }

    public String getResponse() {
        return response;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public static class Builder {
            private final GenericResponse genericResponse = new GenericResponse();

            public Builder withCode(Integer code) {
                if (code == null) {
                    throw new BadCredentialsException("HttpStatus code should not be null");
                }
                genericResponse.code = code;
                return this;
            }

            public Builder withResponse(String response) {
                if (StringUtil.isNullOrWhiteSpace(response)) {
                    throw new BadCredentialsException("Response should not be null or empty");
                }
                genericResponse.response = response;
                return this;
            }

            public Builder withDescription(String description) {
                if (StringUtil.isNullOrWhiteSpace(description)) {
                    throw new BadCredentialsException("Description should not be null or empty");
                }
                genericResponse.description = description;
                return this;
            }

            public Builder withType(String type) {
                if (StringUtil.isNullOrWhiteSpace(type)) {
                    throw new BadCredentialsException("Type should not be null or empty");
                }
                genericResponse.type = type;
                return this;
            }

            //build new object
            public GenericResponse build() {
                return genericResponse;
            }
        }
    }




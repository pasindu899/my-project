package com.aiken.pos.admin.exception;

import com.aiken.common.util.validation.StringUtil;
import com.aiken.pos.admin.constant.Endpoint;
import com.aiken.pos.admin.constant.ErrorCode;
import com.aiken.pos.admin.constant.EventMessage;
import com.aiken.pos.admin.util.response.GenericResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

/**
 * Global System Exception Handler
 *
 * @author Asela Liyanage
 * @version 1.0
 * @since 2021-02-10
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Object illegalArgumentException(final Exception ex, final Model model, HttpServletRequest request) {
        logger.error(EventMessage.ERROR_MSG_BAD_REQUEST + ex);
        if (isWebRequest(request)) {
            model.addAttribute(ErrorCode.SYSTEM_MESSAGE_TAG, EventMessage.ERROR_MSG_BAD_REQUEST);
            return Endpoint.PAGE_ERROR_BAD_REQUEST;
        } else {
            return new ResponseEntity<>(new GenericResponse.Builder()
                    .withCode(BAD_REQUEST.value()).withResponse(BAD_REQUEST.getReasonPhrase())
                    .withDescription(ex.getMessage())
                    .build(), BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Object usernameNotFoundException(final Exception ex, final Model model, HttpServletRequest request) {
        logger.error(EventMessage.ERROR_MSG_UNAUTHORIZED + ex);
        if (isWebRequest(request)) {
            model.addAttribute(ErrorCode.SYSTEM_MESSAGE_TAG, EventMessage.ERROR_MSG_UNAUTHORIZED);
            return Endpoint.PAGE_ERROR_UNAUTHORIZED;
        } else {
            return new ResponseEntity<>(new GenericResponse.Builder()
                    .withCode(UNAUTHORIZED.value()).withResponse(UNAUTHORIZED.getReasonPhrase())
                    .withDescription(ex.getMessage())
                    .build(), UNAUTHORIZED);
        }
    }


    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Object accessDeniedException(final Exception ex, final Model model, HttpServletRequest request) {
        logger.error(EventMessage.ERROR_MSG_ACCESS_DENIED + ex);
        if (isWebRequest(request)) {
            model.addAttribute(ErrorCode.SYSTEM_MESSAGE_TAG, EventMessage.ERROR_MSG_ACCESS_DENIED);
            return Endpoint.PAGE_ERROR_FORBIDDEN;
        } else {
            return new ResponseEntity<>(new GenericResponse.Builder()
                    .withCode(FORBIDDEN.value()).withResponse(FORBIDDEN.getReasonPhrase())
                    .withDescription(ex.getMessage())
                    .build(), FORBIDDEN);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Object resourceNotFoundException(final Exception ex, final Model model, HttpServletRequest request) {
        logger.error(EventMessage.ERROR_MSG_NOT_FOUND + ex);
        if (isWebRequest(request)) {
            model.addAttribute(ErrorCode.SYSTEM_MESSAGE_TAG, EventMessage.ERROR_MSG_NOT_FOUND);
            return Endpoint.PAGE_ERROR_NOT_FOUND;
        } else {
            return new ResponseEntity<>(new GenericResponse.Builder()
                    .withCode(NOT_FOUND.value()).withResponse(NOT_FOUND.getReasonPhrase())
                    .withDescription(ex.getMessage())
                    .build(), NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(org.springframework.boot.context.config.ConfigDataResourceNotFoundException.class)
    public Object configDataResourceNotFoundException(final Exception ex, final Model model, HttpServletRequest request) {
        logger.error(EventMessage.ERROR_MSG_NOT_FOUND + ex);
        if (isWebRequest(request)) {
            model.addAttribute(ErrorCode.SYSTEM_MESSAGE_TAG, EventMessage.ERROR_MSG_NOT_FOUND);
            return Endpoint.PAGE_ERROR_NOT_FOUND;
        } else {
            return new ResponseEntity<>(new GenericResponse.Builder()
                    .withCode(NOT_FOUND.value()).withResponse(NOT_FOUND.getReasonPhrase())
                    .withDescription(ex.getMessage())
                    .build(), NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public Object constraintViolationException(final Exception ex, final Model model, HttpServletRequest request) {
        logger.error(EventMessage.ERROR_MSG_INTERNAL_SERVER_ERROR_1 + ex);
        if (isWebRequest(request)) {
            model.addAttribute(ErrorCode.SYSTEM_MESSAGE_TAG, EventMessage.ERROR_MSG_INTERNAL_SERVER_ERROR_1);
            return Endpoint.PAGE_ERROR_INTERNAL_SERVER_ERROR;
        } else {
            return new ResponseEntity<>(new GenericResponse.Builder()
                    .withCode(INTERNAL_SERVER_ERROR.value()).withResponse(INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .withDescription(ex.getMessage())
                    .build(), INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
    public Object ConstraintViolationException(final Exception ex, final Model model, HttpServletRequest request) {
        logger.error(EventMessage.ERROR_MSG_INTERNAL_SERVER_ERROR_2 + ex.getMessage());
        if (isWebRequest(request)) {
            model.addAttribute(ErrorCode.SYSTEM_MESSAGE_TAG, EventMessage.ERROR_MSG_INTERNAL_SERVER_ERROR_2 + ". \n" + StringUtils.substringBetween(ex.getMessage(), "Detail:", ".;"));
            return Endpoint.PAGE_ERROR_INTERNAL_SERVER_ERROR;
        } else {
            return new ResponseEntity<>(new GenericResponse.Builder()
                    .withCode(INTERNAL_SERVER_ERROR.value()).withResponse(INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .withDescription(ex.getMessage())
                    .build(), INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Object exception(final Exception ex, final Model model, HttpServletRequest request) {
        logger.error(EventMessage.ERROR_MSG_INTERNAL_SERVER_ERROR_3 + ex);
        if (isWebRequest(request)) {
            model.addAttribute(ErrorCode.SYSTEM_MESSAGE_TAG, EventMessage.ERROR_MSG_INTERNAL_SERVER_ERROR_3);
            return Endpoint.PAGE_ERROR_INTERNAL_SERVER_ERROR;
        } else {
            return new ResponseEntity<>(new GenericResponse.Builder()
                    .withCode(INTERNAL_SERVER_ERROR.value()).withResponse(INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .withDescription(ex.getMessage())
                    .build(), INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Object exception(final DataIntegrityViolationException ex, final Model model, HttpServletRequest request) {
        logger.error(EventMessage.ERROR_MSG_INTERNAL_SERVER_ERROR_3 + ex);
        if (isWebRequest(request)) {
            model.addAttribute(ErrorCode.SYSTEM_MESSAGE_TAG, EventMessage.ERROR_MSG_INTERNAL_SERVER_ERROR_3);
            return Endpoint.PAGE_ERROR_INTERNAL_SERVER_ERROR;
        } else {
            return new ResponseEntity<>(new GenericResponse.Builder()
                    .withCode(INTERNAL_SERVER_ERROR.value()).withResponse(INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .withDescription(ex.getMessage())
                    .build(), INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<GenericResponse> handleAuthenticationException(AuthenticationException ex) {
        GenericResponse genericResponse = new GenericResponse.Builder()
                .withCode(UNAUTHORIZED.value())
                .withResponse(UNAUTHORIZED.getReasonPhrase())
                .withDescription(ex.getMessage())
                .withType("authentication_failure")
                .build();
        return new ResponseEntity<>(genericResponse, UNAUTHORIZED);
    }

    /**
     * handle bad credential exceptions
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Object handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request, Model model) {
        logger.error("Bad credentials exception caught by GEH :- ", ex);

        if (isWebRequest(request)) {
            model.addAttribute(ErrorCode.SYSTEM_MESSAGE_TAG, ex.getMessage());
            return Endpoint.PAGE_ERROR_INTERNAL_SERVER_ERROR;
        }
        //return response to the client
        return new ResponseEntity<>(new GenericResponse.Builder()
                .withCode(BAD_REQUEST.value()).withResponse(BAD_REQUEST.getReasonPhrase())
                .withDescription(ex.getMessage())
                .withType("default").build(), BAD_REQUEST);
    }

//	// ERROR : 500, Generic system | most of the time occur this error
//	@ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
//	public final ResponseEntity<Object> handleCommonExceptions(org.springframework.dao.DuplicateKeyException ex, WebRequest request) {
//		logger.error("ERROR : 500 | " + ex);
//		List<String> details = new ArrayList<>();
//		details.add(EventMessage.ERROR_MSG_RECORD_ALREADY_EXISTS);
//		ErrorResponse error = new ErrorResponse(ErrorCode.SYSTEM_ERROR, details);
//		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//	// ERROR : 500, Generic system | most of the time occur this error
//	@ExceptionHandler(Exception.class)
//	public final ResponseEntity<Object> handleCommonExceptions(Exception ex, WebRequest request) {
//		logger.error("ERROR : 500 | " + ex);
//		List<String> details = new ArrayList<>();
//		details.add(ErrorCode.ERROR_CODE_OO1);
//		ErrorResponse error = new ErrorResponse(ErrorCode.SYSTEM_ERROR, details);
//		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//	// ERROR : 500, Generic system | most of the time occur this error
//	@ExceptionHandler(GenericException.class)
//	public final ResponseEntity<Object> handleGenericException(GenericException ex, WebRequest request) {
//		logger.error("ERROR : 500 | " + ex);
//		List<String> details = new ArrayList<>();
//		details.add(ErrorCode.ERROR_CODE_OO2);
//		ErrorResponse error = new ErrorResponse(ErrorCode.SYSTEM_ERROR, details);
//		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//	// ERROR : 500, Generic system | most of the time occur this error
//	@ExceptionHandler(CannotCreateTransactionException.class)
//	public final ResponseEntity<Object> handleCannotCreateTransactionException(CannotCreateTransactionException ex,
//			WebRequest request) {
//		logger.error("ERROR : 500 | " + ex);
//		List<String> details = new ArrayList<>();
//		details.add(ErrorCode.ERROR_CODE_OO3 + "Could not open JDBC Connection for transactio");
//		ErrorResponse error = new ErrorResponse(ErrorCode.SYSTEM_ERROR, details);
//		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//	// ERROR : 404, Generic Resource Not Found Exception
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public final ResponseEntity<Object> handleUserNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//		logger.error("ERROR : 404, Generic Resource Not Found Exception:" + ex);
//		List<String> details = new ArrayList<>();
//		details.add(EventMessage.ERROR_MSG_RECORD_NOT_FOUND);
//		ErrorResponse error = new ErrorResponse(ErrorCode.ERROR_CODE_NOT_FOUND, details);
//		return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
//	}
//
//	// ERROR : 403, Generic Access Denied Exception
//	@ExceptionHandler(AccessDeniedException.class)
//	public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
//		logger.error("ERROR : 403, Generic Access Denied Exception:" + ex);
//		List<String> details = new ArrayList<>();
//		details.add(EventMessage.ERROR_MSG_ACCESS_DENIED);
//		ErrorResponse error = new ErrorResponse(ErrorCode.ERROR_CODE_FORBIDDEN, details);
//		return new ResponseEntity<Object>(error, HttpStatus.FORBIDDEN);
//	}
//
//	// ERROR : 401, Generic Authentication Exception
//	@ExceptionHandler(UsernameNotFoundException.class)
//	public final ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex,
//			WebRequest request) {
//		logger.error("ERROR : 401, Generic Authentication Exception:" + ex);
//		List<String> details = new ArrayList<>();
//		details.add(EventMessage.ERROR_MSG_UNAUTHORIZED);
//		details.add(ex.getMessage());
//		ErrorResponse error = new ErrorResponse(ErrorCode.ERROR_CODE_UNAUTHORIZED, details);
//		return new ResponseEntity<Object>(error, HttpStatus.UNAUTHORIZED);
//	}
//
//	// ERROR : 400, Generic Bad Request Exception
//	@ExceptionHandler(IllegalArgumentException.class)
//	public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
//			WebRequest request) {
//		logger.error("ERROR : 400, Generic Bad Request Exception:" + ex);
//		List<String> details = new ArrayList<>();
//		details.add(EventMessage.ERROR_MSG_BAD_REQUEST);
//		details.add(ex.getMessage());
//		ErrorResponse error = new ErrorResponse(ErrorCode.ERROR_CODE_BAD_REQUEST, details);
//		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
//	}

    public boolean isWebRequest(HttpServletRequest request) {
        return (request != null && !StringUtil.isNullOrWhiteSpace(request.getRequestURI()) &&
                !request.getRequestURI().toLowerCase().contains(Endpoint.URL_API_V1.toLowerCase()));
    }
}
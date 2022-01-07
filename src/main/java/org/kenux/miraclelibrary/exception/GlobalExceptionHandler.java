package org.kenux.miraclelibrary.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataException() {
//        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(ErrorCode.RENT_INFO_DUPLICATION);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {
        // TODO : Refactoring - skyun 2022-01-07
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        final ErrorResponse errorResponse = makeErrorResponse(ex.getBindingResult());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse makeErrorResponse(BindingResult bindingResult) {
        String code = "";
        String message = "";
        String detail = "";
        if (bindingResult.hasErrors()) {
            final FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                if (fieldError.getDefaultMessage() != null) {
                    detail = fieldError.getDefaultMessage();
                }
                String bindResultCode = fieldError.getCode();
                if (bindResultCode != null) {
                    switch (bindResultCode) {
                        case "NotNull":
                        case "NotBlank":
                            code = ErrorCode.NOT_NULL.name();
                            message = ErrorCode.NOT_NULL.getMessage();
                            break;
                        case "NotEmpty":
                            code = ErrorCode.NOT_EMPTY.name();
                            message = ErrorCode.NOT_EMPTY.getMessage();
                            break;
                        case "Min":
                            code = ErrorCode.MIN_VALUE.name();
                            message = ErrorCode.MIN_VALUE.getMessage();
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(code)
                .message(message)
                .detail(detail)
                .build();
    }
}
package com.example.restapi.global.error;

import com.example.restapi.global.error.exception.ConflictDataException;
import com.example.restapi.global.error.exception.InvalidParameterException;
import com.example.restapi.global.error.exception.NotFoundDataException;
import com.example.restapi.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 공통 예외 처리
 * @author kdh
 * @see <a href="https://devwithus.com/exception-handling-spring-boot-rest-api/">reference page</a>
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /*
        리소스는 존재하지만 데이터가 없는 상태는 204 응답코드로 응답
     */
    @ExceptionHandler(NotFoundDataException.class)
    public ResponseEntity<String> handlerNotFoundDataException(NotFoundDataException e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*
        중복 리소스 요청 오류
     */
    @ExceptionHandler(ConflictDataException.class)
    public ResponseEntity<ErrorResponse> handlerConflictDataException(ConflictDataException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder()
                .httpStatusCode(ErrorCode.CONFLICT_REQUEST.getHttpStatusCode())
                .errorMessage(e.getMessage())
                .errorCode(ErrorCode.CONFLICT_REQUEST.getCode())
                .build());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError != null
                ? String.format("field: %s, message: %s", fieldError.getField(), fieldError.getDefaultMessage())
                : e.getMessage();

        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .httpStatusCode(ErrorCode.INVALID_PARAMETER.getHttpStatusCode())
                .errorMessage(errorMessage)
                .errorCode(ErrorCode.INVALID_PARAMETER.getCode())
                .build());
    }

    /*
        올바르지 않는 파라메터 요청 오류
     */
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidParameterException(InvalidParameterException e) {
        FieldError fieldError = e.getErrors().getFieldError();
        String errorMessage = fieldError != null
                ? String.format("field: %s, message: %s", fieldError.getField(), fieldError.getDefaultMessage())
                : e.getMessage();

        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .httpStatusCode(ErrorCode.INVALID_PARAMETER.getHttpStatusCode())
                .errorMessage(errorMessage)
                .errorCode(ErrorCode.INVALID_PARAMETER.getCode())
                .build());
    }

    /*
        올바르지 않는 파라메터 요청 오류
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .httpStatusCode(ErrorCode.INVALID_PARAMETER.getHttpStatusCode())
                .errorMessage(e.getMessage())
                .errorCode(ErrorCode.INVALID_PARAMETER.getCode())
                .build());
    }

    /*
        존재하지 않는 리소스 요청 오류
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException e){
        return ResponseEntity.notFound().build();
    }

    /*
        존재하지 않는 메서드 호출
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    /*
        지원하지 않는 미디어 타입의 Accept 요청
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<String> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ErrorCode.NOT_ACCEPTABLE.getMessage());
    }

    /*
        기타 Runtime 오류
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> all(RuntimeException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .httpStatusCode(ErrorCode.SERVER_INTERNAL_ERROR.getHttpStatusCode())
                .errorMessage(e.getMessage())
                .errorCode(ErrorCode.SERVER_INTERNAL_ERROR.getCode())
                .build());
    }

    /*
        기타 Exception 오류
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> all(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .httpStatusCode(ErrorCode.SERVER_INTERNAL_ERROR.getHttpStatusCode())
                .errorMessage(e.getMessage())
                .errorCode(ErrorCode.SERVER_INTERNAL_ERROR.getCode())
                .build());
    }

}

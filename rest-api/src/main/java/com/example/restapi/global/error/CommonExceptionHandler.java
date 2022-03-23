package com.example.restapi.global.error;

import com.example.restapi.global.error.exception.ConflictDataException;
import com.example.restapi.global.error.exception.InvalidParameterException;
import com.example.restapi.global.error.exception.NotFoundDataException;
import com.example.restapi.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

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
                .errorMessage(e.getMessage())
                .errorCode(ErrorCode.CONFLICT_REQUEST.getCode())
                .build());
    }

    /*
        올바르지 않는 파라메터 요청 오류
     */
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidParameterException(InvalidParameterException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
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
        지원하지 않는 미디어 타입의 Accept 요청입니다.
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<String> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ErrorCode.NOT_ACCEPTABLE.getMessage());
    }

    /*
        기타 Runtime 오류
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> all(RuntimeException e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /*
        기타 Exception 오류
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> all(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}

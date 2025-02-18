package com.example.testproject.common.exception;

import com.example.testproject.common.Constants;
import org.springframework.http.HttpStatus;

public class AroundHubException extends Exception{ //커스텀 Exception.

    private static final long serialVersionUID = 4663380430591151694L;

    private Constants.ExceptionClass exceptionClass; //Constants 클래스의 ExceptionClass Enum중 한개.

    private HttpStatus httpStatus;

    public AroundHubException(Constants.ExceptionClass /**ExceptionClass의 enum 상수중 한개.*/ exceptionClass, HttpStatus httpStatus, String message){
        super(exceptionClass.toString() + message);
        //Exception에 super로 메세지 넘겨주면 저장하고 있다가 e.getmessage하면 갖고와짐.
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
    }

    public Constants.ExceptionClass getExceptionClass() {
        return exceptionClass;
    }

    public int getHttpStatusCode() {
        return httpStatus.value(); //status 번호 리턴
    }

    public String getHttpStatusType() {
        return httpStatus.getReasonPhrase(); //status 번호 이유 리턴
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
}
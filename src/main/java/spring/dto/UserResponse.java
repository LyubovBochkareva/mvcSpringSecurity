package spring.dto;

import org.springframework.http.HttpStatus;

public class UserResponse {

    private HttpStatus codeStatus;
    private String messageStatus;

    public HttpStatus getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(HttpStatus codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }
}

package com.example.smartPos.controllers.responses;

import lombok.Data;
import lombok.NoArgsConstructor;


public class CommonResponse {
    private String statusCode;
    private String desc;

    public CommonResponse() {
    }

    public CommonResponse(String statusCode, String desc) {
        this.statusCode = statusCode;
        this.desc = desc;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

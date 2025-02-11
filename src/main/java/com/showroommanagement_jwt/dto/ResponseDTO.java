package com.showroommanagement_jwt.dto;
public record ResponseDTO(int statusCode,String message,Object data){}

//public class ResponseDTO {
//    private Integer statusCode;
//    private String message;
//    private Object data;
//
//    public Integer getStatusCode() {
//        return statusCode;
//    }
//
//    public void setStatusCode(Integer statusCode) {
//        this.statusCode = statusCode;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
//}

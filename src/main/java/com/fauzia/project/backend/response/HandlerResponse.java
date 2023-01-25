package com.fauzia.project.backend.response;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_OK;

public class HandlerResponse {
    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_FAILED = "FAILED";

    private static void responseWriter(HttpServletResponse response, Object data, Integer code){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String finalResponse = objectMapper.writeValueAsString(data);

            response.setStatus(code);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(finalResponse);
            response.getWriter().flush();
        } catch (Exception e) {
            responseInternalServerError(response, e.getMessage().toUpperCase());
            throw new RuntimeException(e);
        }
    }

    public static void responseSuccessOK(HttpServletResponse response, String message){
        if (message.isEmpty()){
            message = "SUCCESS";
        }

        SuccessResponse<String> successResponse = new SuccessResponse<>();
        successResponse.setStatus(true);
        successResponse.setMessage(message);

        responseWriter(response, successResponse, SC_OK);
    }

    public static void responseSuccessWithData(HttpServletResponse response, DataResponse<?> data, String message){
        data.setStatus(true);
        data.setMessage(message);
        responseWriter(response, data, SC_OK);
    }

    public static void responseSuccessCreated(HttpServletResponse response, String message){
        if (message.isEmpty()){
            message = "CREATED";
        }

        SuccessResponse<String> successResponse = new SuccessResponse<>();
        successResponse.setStatus(true);
        successResponse.setMessage(message);

        responseWriter(response, successResponse, HttpServletResponse.SC_CREATED);
    }

    public static void responseBadRequest(HttpServletResponse response, String errorCode, String error){
        if (error.isEmpty()){
            error = "BAD REQUEST";
        }

        ErrorResponse<String> errorResponse = new ErrorResponse<>();
        errorResponse.setStatus(false);
        errorResponse.setErrorCode(errorCode);
        errorResponse.setMessage(error);

        responseWriter(response, errorResponse, HttpServletResponse.SC_BAD_REQUEST);
    }

    public static void responseUnauthorized(HttpServletResponse response, String error){
        if (error.isEmpty()){
            error = "UNAUTHORIZED";
        }
        ErrorResponse<String> errorResponse = new ErrorResponse<>();

        errorResponse.setStatus(false);
        errorResponse.setErrorCode("401");
        errorResponse.setMessage(error);

        responseWriter(response, errorResponse, HttpServletResponse.SC_BAD_REQUEST);
    }

    public static void responseInternalServerError(HttpServletResponse response, String error) {
        if (error.isEmpty()){
            error = "INTERNAL SERVER ERROR";
        }

        ErrorResponse<String> errorResponse = new ErrorResponse<>();

        errorResponse.setStatus(false);
        errorResponse.setMessage(error);

        responseWriter(response, errorResponse, HttpServletResponse.SC_BAD_REQUEST);
    }

    public static void responseInternalServerErrorWithData(HttpServletResponse response, DataResponse<?> data,
                                                           String message){
        data.setStatus(true);
        data.setMessage(message);
        responseWriter(response, data, SC_OK);
    }

    public static void responseGateway(HttpServletResponse response, String error){
        if (error.isEmpty()){
            error = "BAD GATEWAY";
        }

        ErrorResponse<String> errorResponse = new ErrorResponse<>();

        errorResponse.setStatus(false);
        errorResponse.setMessage(error);

        responseWriter(response, errorResponse, HttpServletResponse.SC_BAD_REQUEST);
    }
}

package es.mxcircuit.mxcircuit.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gashe on 15/5/17.
 */

public class Response implements Serializable {

    @SerializedName("code-error")
    private int code_error;
    @SerializedName("msg-error")
    private String message_error;

    public static final int OK = 1;
    public static final int ERROR = 0;
    public static final String ERROR_GENERAL = "Ha ocurrido un problema";

    public Response(int code_error, String message_error) {
        this.code_error = code_error;
        this.message_error = message_error;
    }

    public int getCode_error() {
        return code_error;
    }

    public void setCode_error(int code_error) {
        this.code_error = code_error;
    }

    public String getMessage_error() {
        return message_error;
    }

    public void setMessage_error(String message_error) {
        this.message_error = message_error;
    }

}

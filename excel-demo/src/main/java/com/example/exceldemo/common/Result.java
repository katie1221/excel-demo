package com.example.exceldemo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应数据
 * @author qzz
 */
@ApiModel("Result响应")
public class Result<T> implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 编码 0：成功 其他值代表失败
     */
    private int code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    @JsonIgnore
    private static final Map EMPTY_MAP = new HashMap();

    public Result(){

    }
    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result success(Object data){
        return data==null?success():new Result(0,"success",data);
    }

    public static Result success(){
        return new Result(0,"success",EMPTY_MAP);
    }

    public static Result fail(){
        return new Result(400,"请求失败",EMPTY_MAP);
    }
    public static Result fail(String msg){
        return new Result(400,"请求失败",msg);
    }

    public static Result fail(int code,String msg){
        return new Result(code,msg,EMPTY_MAP);
    }

    public static Result fail(int code,String msg,Object data){
        return new Result(code,msg,data);
    }
}

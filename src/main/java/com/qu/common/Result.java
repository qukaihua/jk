package com.qu.common;

/**
 * Created by 瞿凯华 on 2018/6/6 0006.
 */
public class Result {
    int flag;
    String msg;
    Object data;


    public Result(int flag) {
        this.flag = flag;
    }

    public Result(int flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public Result(int flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public Result(String msg, Object data, int flag) {
        this.msg = msg;
        this.data = data;
        this.flag = flag;
    }

    public static Result getsuccessful(){

        return  new Result(signal.SUCCESS.getCode());
    }
    public static Result getsuccessful(String msg){
        return new Result(signal.SUCCESS.getCode(),msg);
    }
    public static Result getsuccessful(Object data){
        return new Result(signal.SUCCESS.getCode(),data);
    }
    public static Result getsuccessful(String msg,Object data){
        return new Result(msg,data,signal.SUCCESS.getCode());
    }
    public static Result getfailed(){
        return new Result(signal.ERROR.getCode());
    }
    public static Result getfailed(String msg){
       return new Result(signal.ERROR.getCode(),msg);
    }
    public static Result getfailed(String msg,Object data){
        return new Result(msg,data,signal.ERROR.getCode());
    }
    public static Result getfailed(Object data){
        return new Result(signal.ERROR.getCode(),data);
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}


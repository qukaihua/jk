package com.qu.common;

/**
 * Created by 瞿凯华 on 2018/6/7 0007.
 */
public class Const {
    public  static final  String CurrentUser = "currentuser";
    public static final String T_Email = "email";
    public static final String T_UserName = "username";
    public static final String  Wait_Pay = "WAIT_BUYER_PAY";
    public static final  String Success_Pay = "TRADE_SUCCESS";
    public interface ROLE{
        public static final int Normal = 1;
        public static final int Manager = 0;
    }

    public interface CK{
        public static final int Check = 1;
        public static final int Uncheck = 0;
    }
 public enum pay_status{
        not_pay("未支付","0"),
        wait_pay("待支付","10"),
        have_pay("已支付","20");
      String msg,code;
     pay_status(String msg,String code){
            this.code = code;
            this.msg = msg;
        }

     public String getCode() {
         return code;
     }

     public void setCode(String code) {
         this.code = code;
     }

     public String getMsg() {
         return msg;
     }

     public void setMsg(String msg) {
         this.msg = msg;
     }
 }
}

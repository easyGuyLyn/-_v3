package com.dawoo.gamebox.bean;

/**
 * Created by b on 18-1-17.
 * 取款结果
 */

public class WithdrawSubmitResult {

    /**
     * error : 0
     * code : 105
     * msg : 取款失败
     * data : {"msg":"系统忙，请稍后再试！","type":null,"state":false,"token":"d67ed3c8-c10a-498c-859f-4becc664038c"}
     * version : app_01
     */

    private int error;
    private int code;
    private DataBean data;
    private String version;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class DataBean {
        /**
         * msg : 系统忙，请稍后再试！
         * type : null
         * state : false
         * token : d67ed3c8-c10a-498c-859f-4becc664038c
         */

        private String msg;
        private Object type;
        private boolean state;
        private String token;
        private String btcNumber;

        public String getBtcNumber() {
            return btcNumber;
        }

        public void setBtcNumber(String btcNumber) {
            this.btcNumber = btcNumber;
        }



        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

package com.dawoo.gamebox.bean;

/**
 * Created by b on 18-2-1.
 */

public class WithdrawFee {

    /**
     * error : 0
     * code : 0
     * msg : 请求成功
     * data : {"actualWithdraw":"-297.88","deductFavorable":302.88,"administrativeFee":0,"counterFee":"0.00"}
     * version : app_01
     */

    private int error;
    private int code;
    private String msg;
    private DataBean data;
    private String version;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        /**
         * actualWithdraw : -297.88
         * deductFavorable : 302.88
         * administrativeFee : 0
         * counterFee : 0.00
         */
        private String msg;
        private double actualWithdraw;
        private double deductFavorable;
        private double administrativeFee;
        private double counterFee;

        public double getActualWithdraw() {
            return actualWithdraw;
        }

        public void setActualWithdraw(double actualWithdraw) {
            this.actualWithdraw = actualWithdraw;
        }

        public double getDeductFavorable() {
            return deductFavorable;
        }

        public void setDeductFavorable(double deductFavorable) {
            this.deductFavorable = deductFavorable;
        }

        public double getAdministrativeFee() {
            return administrativeFee;
        }

        public void setAdministrativeFee(double administrativeFee) {
            this.administrativeFee = administrativeFee;
        }

        public double getCounterFee() {
            return counterFee;
        }

        public void setCounterFee(double counterFee) {
            this.counterFee = counterFee;
        }
    }
}

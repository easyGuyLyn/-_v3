package com.dawoo.gamebox.bean;

/**
 * Created by b on 18-1-18.
 */

public class Bitcoin {

    /**
     * msg : 展示比特币信息
     * code : 0
     * error : 0
     * data : {"id":10232,"userId":11218,"bankcardMasterName":null,"bankcardNumber":"abcdefghijklmnopqrstuvwxyz","createTime":1515133683045,"useCount":0,"useStauts":true,"isDefault":true,"bankName":"bitcoin","bankDeposit":null,"customBankName":null,"type":"2"}
     * version : app_01
     */

    private String msg;
    private int code;
    private int error;
    private DataBean data;
    private String version;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
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
         * id : 10232
         * userId : 11218
         * bankcardMasterName : null
         * bankcardNumber : abcdefghijklmnopqrstuvwxyz
         * createTime : 1515133683045
         * useCount : 0
         * useStauts : true
         * isDefault : true
         * bankName : bitcoin
         * bankDeposit : null
         * customBankName : null
         * type : 2
         */

        private int id;
        private int userId;
        private String bankcardMasterName;
        private String bankcardNumber;
        private long createTime;
        private int useCount;
        private boolean useStauts;
        private boolean isDefault;
        private String bankName;
        private String bankDeposit;
        private String customBankName;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getBankcardMasterName() {
            return bankcardMasterName;
        }

        public void setBankcardMasterName(String bankcardMasterName) {
            this.bankcardMasterName = bankcardMasterName;
        }

        public String getBankcardNumber() {
            return bankcardNumber;
        }

        public void setBankcardNumber(String bankcardNumber) {
            this.bankcardNumber = bankcardNumber;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getUseCount() {
            return useCount;
        }

        public void setUseCount(int useCount) {
            this.useCount = useCount;
        }

        public boolean isUseStauts() {
            return useStauts;
        }

        public void setUseStauts(boolean useStauts) {
            this.useStauts = useStauts;
        }

        public boolean isIsDefault() {
            return isDefault;
        }

        public void setIsDefault(boolean isDefault) {
            this.isDefault = isDefault;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankDeposit() {
            return bankDeposit;
        }

        public void setBankDeposit(String bankDeposit) {
            this.bankDeposit = bankDeposit;
        }

        public Object getCustomBankName() {
            return customBankName;
        }

        public void setCustomBankName(String customBankName) {
            this.customBankName = customBankName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

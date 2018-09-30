package com.dawoo.gamebox.bean;

/**
 * Created by b on 18-1-25.
 */

public class VerifyRealNameBean {

    /**
     * success : true
     * code : 0
     * title : null
     * message : 请求成功
     * data : {"playerAccount":"ps4","importResult":true,"nameSame":true,"conflict":false}
     * version : app_01
     */

    private boolean success;
    private String code;
    private Object title;
    private String message;
    private DataBean data;
    private String version;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
         * playerAccount : ps4
         * importResult : true
         * nameSame : true
         * conflict : false
         */

        private String playerAccount;
        private boolean importResult;
        private boolean nameSame;
        private boolean conflict;

        public String getPlayerAccount() {
            return playerAccount;
        }

        public void setPlayerAccount(String playerAccount) {
            this.playerAccount = playerAccount;
        }

        public boolean isImportResult() {
            return importResult;
        }

        public void setImportResult(boolean importResult) {
            this.importResult = importResult;
        }

        public boolean isNameSame() {
            return nameSame;
        }

        public void setNameSame(boolean nameSame) {
            this.nameSame = nameSame;
        }

        public boolean isConflict() {
            return conflict;
        }

        public void setConflict(boolean conflict) {
            this.conflict = conflict;
        }
    }
}

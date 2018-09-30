package com.dawoo.gamebox.bean;

public class IsBindUserPhoneBean {

    private boolean success;
    private int code;
    private String message;
    private String version;
    private Data data;
    private String title;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static final class Data{

        private String encryptedId;
        private String phone;


        public String getEncryptedId() {
            return encryptedId;
        }

        public void setEncryptedId(String encryptedId) {
            this.encryptedId = encryptedId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}

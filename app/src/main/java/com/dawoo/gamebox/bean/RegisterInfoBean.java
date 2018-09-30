package com.dawoo.gamebox.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alex on 18-3-22.
 */

public class RegisterInfoBean {


    /**
     * success : true
     * code : 0
     * title : null
     * message : 请求成功
     * data : {"requiredJson":["username","password","verificationCode","regCode","304","110","201","realName","301","paymentPassword","defaultTimezone","birthday","sex","mainCurrency","defaultLocale","securityIssues","serviceTerms"],"field":[{"id":1,"name":"username","isRequired":"0","status":"1","isRegField":"0","bulitIn":true,"sort":1,"derail":false,"isOnly":"2","i18nName":null},{"id":2,"name":"password","isRequired":"0","status":"1","isRegField":"0","bulitIn":true,"sort":2,"derail":false,"isOnly":"2","i18nName":null},{"id":3,"name":"verificationCode","isRequired":"0","status":"1","isRegField":"0","bulitIn":true,"sort":3,"derail":false,"isOnly":"2","i18nName":null},{"id":4,"name":"regCode","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":4,"derail":false,"isOnly":"2","i18nName":null},{"id":5,"name":"304","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":5,"derail":false,"isOnly":"1","i18nName":null},{"id":6,"name":"110","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":6,"derail":false,"isOnly":"1","i18nName":null},{"id":7,"name":"201","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":7,"derail":false,"isOnly":"1","i18nName":null},{"id":8,"name":"realName","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":8,"derail":false,"isOnly":"1","i18nName":null},{"id":9,"name":"301","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":9,"derail":false,"isOnly":"1","i18nName":null},{"id":10,"name":"paymentPassword","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":10,"derail":false,"isOnly":"2","i18nName":null},{"id":11,"name":"defaultTimezone","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":11,"derail":false,"isOnly":"2","i18nName":null},{"id":12,"name":"birthday","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":12,"derail":false,"isOnly":"2","i18nName":null},{"id":13,"name":"sex","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":13,"derail":false,"isOnly":"2","i18nName":null},{"id":14,"name":"mainCurrency","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":14,"derail":false,"isOnly":"2","i18nName":null},{"id":15,"name":"defaultLocale","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":15,"derail":false,"isOnly":"2","i18nName":null},{"id":16,"name":"securityIssues","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":16,"derail":false,"isOnly":"2","i18nName":null},{"id":17,"name":"serviceTerms","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":17,"derail":false,"isOnly":"2","i18nName":null}],"isRequiredForRegisterCode":true,"isPhone":true,"isEmail":false,"selectOption":{"defaultLocale":[{"text":"简体中文","value":"zh_CN"}],"mainCurrency":[{"text":"人民币","value":"CNY"}],"securityIssues":[{"text":"您小时候最喜欢哪一本书？","value":"1"},{"text":"您的理想工作是什么？","value":"2"},{"text":"您童年时代的绰号是什么？","value":"3"},{"text":"您拥有的第一辆车是什么型号？","value":"4"},{"text":"您在学生时代最喜欢哪个歌手或乐队？","value":"5"},{"text":"您在学生时代最喜欢哪个电影明星或角色？","value":"6"},{"text":"您的第一个上司叫什么名字？","value":"7"},{"text":"您的父母是在哪里认识的？","value":"8"},{"text":"您的第一个宠物叫什么名字？","value":"9"},{"text":"您少年时代最好的朋友叫什么名字？","value":"10"},{"text":"您第一次去电影院看的是哪一部电影？","value":"11"},{"text":"您学会做的第一道菜是什么？","value":"12"},{"text":"您上小学时最喜欢的老师姓什么？","value":"13"},{"text":"您第一次坐飞机是去哪里？","value":"14"},{"text":"您从小长大的那条街叫什么？","value":"15"},{"text":"您去过的第一个海滨浴场是哪一个？","value":"16"},{"text":"您购买的第一张专辑是什么？","value":"17"},{"text":"您最喜欢哪个球队？","value":"18"}],"sex":[{"text":"男","value":"male"},{"text":"女","value":"female"},{"text":"不限","value":"secret"}]},"registCodeField":true,"params":{"ipLocale":{"region":"","country":"RV","city":""},"minDate":-1634112000000,"timezone":"GMT+08:00","registCode":null,"currency":"CNY","maxDate":953654400000},"signUpDataMap":{"birthday":"sysUser.birthday","sex":"sysUser.sex","paymentPassword":"sysUser.permissionPwd","defaultTimezone":"sysUser.defaultTimezone","defaultLocale":"sysUser.defaultLocale","110":"phone.contactValue","realName":"sysUser.realName","mainCurrency":"sysUser.defaultCurrency","password":"sysUser.password","securityIssues":"sysUserProtection.question1","201":"email.contactValue","301":"qq.contactValue","304":"weixin.contactValue","username":"sysUser.username"}}
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
         * requiredJson : ["username","password","verificationCode","regCode","304","110","201","realName","301","paymentPassword","defaultTimezone","birthday","sex","mainCurrency","defaultLocale","securityIssues","serviceTerms"]
         * field : [{"id":1,"name":"username","isRequired":"0","status":"1","isRegField":"0","bulitIn":true,"sort":1,"derail":false,"isOnly":"2","i18nName":null},{"id":2,"name":"password","isRequired":"0","status":"1","isRegField":"0","bulitIn":true,"sort":2,"derail":false,"isOnly":"2","i18nName":null},{"id":3,"name":"verificationCode","isRequired":"0","status":"1","isRegField":"0","bulitIn":true,"sort":3,"derail":false,"isOnly":"2","i18nName":null},{"id":4,"name":"regCode","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":4,"derail":false,"isOnly":"2","i18nName":null},{"id":5,"name":"304","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":5,"derail":false,"isOnly":"1","i18nName":null},{"id":6,"name":"110","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":6,"derail":false,"isOnly":"1","i18nName":null},{"id":7,"name":"201","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":7,"derail":false,"isOnly":"1","i18nName":null},{"id":8,"name":"realName","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":8,"derail":false,"isOnly":"1","i18nName":null},{"id":9,"name":"301","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":9,"derail":false,"isOnly":"1","i18nName":null},{"id":10,"name":"paymentPassword","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":10,"derail":false,"isOnly":"2","i18nName":null},{"id":11,"name":"defaultTimezone","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":11,"derail":false,"isOnly":"2","i18nName":null},{"id":12,"name":"birthday","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":12,"derail":false,"isOnly":"2","i18nName":null},{"id":13,"name":"sex","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":13,"derail":false,"isOnly":"2","i18nName":null},{"id":14,"name":"mainCurrency","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":14,"derail":false,"isOnly":"2","i18nName":null},{"id":15,"name":"defaultLocale","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":15,"derail":false,"isOnly":"2","i18nName":null},{"id":16,"name":"securityIssues","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":16,"derail":false,"isOnly":"2","i18nName":null},{"id":17,"name":"serviceTerms","isRequired":"1","status":"1","isRegField":"1","bulitIn":false,"sort":17,"derail":false,"isOnly":"2","i18nName":null}]
         * isRequiredForRegisterCode : true
         * isPhone : true
         * isEmail : false
         * selectOption : {"defaultLocale":[{"text":"简体中文","value":"zh_CN"}],"mainCurrency":[{"text":"人民币","value":"CNY"}],"securityIssues":[{"text":"您小时候最喜欢哪一本书？","value":"1"},{"text":"您的理想工作是什么？","value":"2"},{"text":"您童年时代的绰号是什么？","value":"3"},{"text":"您拥有的第一辆车是什么型号？","value":"4"},{"text":"您在学生时代最喜欢哪个歌手或乐队？","value":"5"},{"text":"您在学生时代最喜欢哪个电影明星或角色？","value":"6"},{"text":"您的第一个上司叫什么名字？","value":"7"},{"text":"您的父母是在哪里认识的？","value":"8"},{"text":"您的第一个宠物叫什么名字？","value":"9"},{"text":"您少年时代最好的朋友叫什么名字？","value":"10"},{"text":"您第一次去电影院看的是哪一部电影？","value":"11"},{"text":"您学会做的第一道菜是什么？","value":"12"},{"text":"您上小学时最喜欢的老师姓什么？","value":"13"},{"text":"您第一次坐飞机是去哪里？","value":"14"},{"text":"您从小长大的那条街叫什么？","value":"15"},{"text":"您去过的第一个海滨浴场是哪一个？","value":"16"},{"text":"您购买的第一张专辑是什么？","value":"17"},{"text":"您最喜欢哪个球队？","value":"18"}],"sex":[{"text":"男","value":"male"},{"text":"女","value":"female"},{"text":"不限","value":"secret"}]}
         * registCodeField : true
         * params : {"ipLocale":{"region":"","country":"RV","city":""},"minDate":-1634112000000,"timezone":"GMT+08:00","registCode":null,"currency":"CNY","maxDate":953654400000}
         * signUpDataMap : {"birthday":"sysUser.birthday","sex":"sysUser.sex","paymentPassword":"sysUser.permissionPwd","defaultTimezone":"sysUser.defaultTimezone","defaultLocale":"sysUser.defaultLocale","110":"phone.contactValue","realName":"sysUser.realName","mainCurrency":"sysUser.defaultCurrency","password":"sysUser.password","securityIssues":"sysUserProtection.question1","201":"email.contactValue","301":"qq.contactValue","304":"weixin.contactValue","username":"sysUser.username"}
         */

        private boolean isRequiredForRegisterCode;
        private boolean isPhone;
        private boolean isEmail;
        private SelectOptionBean selectOption;
        private boolean registCodeField;
        private ParamsBean params;
        private SignUpDataMapBean signUpDataMap;
        private List<String> requiredJson;
        private List<FieldBean> field;

        public boolean isIsRequiredForRegisterCode() {
            return isRequiredForRegisterCode;
        }

        public void setIsRequiredForRegisterCode(boolean isRequiredForRegisterCode) {
            this.isRequiredForRegisterCode = isRequiredForRegisterCode;
        }

        public boolean isIsPhone() {
            return isPhone;
        }

        public void setIsPhone(boolean isPhone) {
            this.isPhone = isPhone;
        }

        public boolean isIsEmail() {
            return isEmail;
        }

        public void setIsEmail(boolean isEmail) {
            this.isEmail = isEmail;
        }

        public SelectOptionBean getSelectOption() {
            return selectOption;
        }

        public void setSelectOption(SelectOptionBean selectOption) {
            this.selectOption = selectOption;
        }

        public boolean isRegistCodeField() {
            return registCodeField;
        }

        public void setRegistCodeField(boolean registCodeField) {
            this.registCodeField = registCodeField;
        }

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public SignUpDataMapBean getSignUpDataMap() {
            return signUpDataMap;
        }

        public void setSignUpDataMap(SignUpDataMapBean signUpDataMap) {
            this.signUpDataMap = signUpDataMap;
        }

        public List<String> getRequiredJson() {
            return requiredJson;
        }

        public void setRequiredJson(List<String> requiredJson) {
            this.requiredJson = requiredJson;
        }

        public List<FieldBean> getField() {
            return field;
        }

        public void setField(List<FieldBean> field) {
            this.field = field;
        }

        public static class SelectOptionBean {
            private List<DefaultLocaleBean> defaultLocale;
            private List<MainCurrencyBean> mainCurrency;
            private List<SecurityIssuesBean> securityIssues;
            private List<SexBean> sex;

            public List<DefaultLocaleBean> getDefaultLocale() {
                return defaultLocale;
            }

            public void setDefaultLocale(List<DefaultLocaleBean> defaultLocale) {
                this.defaultLocale = defaultLocale;
            }

            public List<MainCurrencyBean> getMainCurrency() {
                return mainCurrency;
            }

            public void setMainCurrency(List<MainCurrencyBean> mainCurrency) {
                this.mainCurrency = mainCurrency;
            }

            public List<SecurityIssuesBean> getSecurityIssues() {
                return securityIssues;
            }

            public void setSecurityIssues(List<SecurityIssuesBean> securityIssues) {
                this.securityIssues = securityIssues;
            }

            public List<SexBean> getSex() {
                return sex;
            }

            public void setSex(List<SexBean> sex) {
                this.sex = sex;
            }

            public static class DefaultLocaleBean {
                /**
                 * text : 简体中文
                 * value : zh_CN
                 */

                private String text;
                private String value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class MainCurrencyBean {
                /**
                 * text : 人民币
                 * value : CNY
                 */

                private String text;
                private String value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class SecurityIssuesBean {
                /**
                 * text : 您小时候最喜欢哪一本书？
                 * value : 1
                 */

                private String text;
                private String value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class SexBean {
                /**
                 * text : 男
                 * value : male
                 */

                private String text;
                private String value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }

        public static class ParamsBean {
            /**
             * ipLocale : {"region":"","country":"RV","city":""}
             * minDate : -1634112000000
             * timezone : GMT+08:00
             * registCode : null
             * currency : CNY
             * maxDate : 953654400000
             */

            private IpLocaleBean ipLocale;
            private long minDate;
            private String timezone;
            private Object registCode;
            private String currency;
            private long maxDate;

            public IpLocaleBean getIpLocale() {
                return ipLocale;
            }

            public void setIpLocale(IpLocaleBean ipLocale) {
                this.ipLocale = ipLocale;
            }

            public long getMinDate() {
                return minDate;
            }

            public void setMinDate(long minDate) {
                this.minDate = minDate;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public Object getRegistCode() {
                return registCode;
            }

            public void setRegistCode(Object registCode) {
                this.registCode = registCode;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public long getMaxDate() {
                return maxDate;
            }

            public void setMaxDate(long maxDate) {
                this.maxDate = maxDate;
            }

            public static class IpLocaleBean {
                /**
                 * region :
                 * country : RV
                 * city :
                 */

                private String region;
                private String country;
                private String city;

                public String getRegion() {
                    return region;
                }

                public void setRegion(String region) {
                    this.region = region;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }
            }
        }

        public static class SignUpDataMapBean {
            /**
             * birthday : sysUser.birthday
             * sex : sysUser.sex
             * paymentPassword : sysUser.permissionPwd
             * defaultTimezone : sysUser.defaultTimezone
             * defaultLocale : sysUser.defaultLocale
             * 110 : phone.contactValue
             * realName : sysUser.realName
             * mainCurrency : sysUser.defaultCurrency
             * password : sysUser.password
             * securityIssues : sysUserProtection.question1
             * 201 : email.contactValue
             * 301 : qq.contactValue
             * 304 : weixin.contactValue
             * username : sysUser.username
             */

            private String birthday;
            private String sex;
            private String paymentPassword;
            private String defaultTimezone;
            private String defaultLocale;
            @SerializedName("110")
            private String _$110;
            private String realName;
            private String mainCurrency;
            private String password;
            private String securityIssues;
            @SerializedName("201")
            private String _$201;
            @SerializedName("301")
            private String _$301;
            @SerializedName("304")
            private String _$304;
            private String username;

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getPaymentPassword() {
                return paymentPassword;
            }

            public void setPaymentPassword(String paymentPassword) {
                this.paymentPassword = paymentPassword;
            }

            public String getDefaultTimezone() {
                return defaultTimezone;
            }

            public void setDefaultTimezone(String defaultTimezone) {
                this.defaultTimezone = defaultTimezone;
            }

            public String getDefaultLocale() {
                return defaultLocale;
            }

            public void setDefaultLocale(String defaultLocale) {
                this.defaultLocale = defaultLocale;
            }

            public String get_$110() {
                return _$110;
            }

            public void set_$110(String _$110) {
                this._$110 = _$110;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getMainCurrency() {
                return mainCurrency;
            }

            public void setMainCurrency(String mainCurrency) {
                this.mainCurrency = mainCurrency;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getSecurityIssues() {
                return securityIssues;
            }

            public void setSecurityIssues(String securityIssues) {
                this.securityIssues = securityIssues;
            }

            public String get_$201() {
                return _$201;
            }

            public void set_$201(String _$201) {
                this._$201 = _$201;
            }

            public String get_$301() {
                return _$301;
            }

            public void set_$301(String _$301) {
                this._$301 = _$301;
            }

            public String get_$304() {
                return _$304;
            }

            public void set_$304(String _$304) {
                this._$304 = _$304;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class FieldBean {
            /**
             * id : 1
             * name : username
             * isRequired : 0
             * status : 1
             * isRegField : 0
             * bulitIn : true
             * sort : 1
             * derail : false
             * isOnly : 2
             * i18nName : null
             */

            private int id;
            private String name;
            private String isRequired;
            private String status;
            private String isRegField;
            private boolean bulitIn;
            private int sort;
            private boolean derail;
            private String isOnly;
            private Object i18nName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIsRequired() {
                return isRequired;
            }

            public void setIsRequired(String isRequired) {
                this.isRequired = isRequired;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIsRegField() {
                return isRegField;
            }

            public void setIsRegField(String isRegField) {
                this.isRegField = isRegField;
            }

            public boolean isBulitIn() {
                return bulitIn;
            }

            public void setBulitIn(boolean bulitIn) {
                this.bulitIn = bulitIn;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public boolean isDerail() {
                return derail;
            }

            public void setDerail(boolean derail) {
                this.derail = derail;
            }

            public String getIsOnly() {
                return isOnly;
            }

            public void setIsOnly(String isOnly) {
                this.isOnly = isOnly;
            }

            public Object getI18nName() {
                return i18nName;
            }

            public void setI18nName(Object i18nName) {
                this.i18nName = i18nName;
            }
        }
    }
}

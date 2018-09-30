package com.dawoo.gamebox.bean;

/**
 * Created by jack on 18-3-22.
 */

public class ProblemBean {

    /**
     * name : 常见问题
     * ids : null
     * id : ca80812f9d392b44dbccd7ea0cab3f09
     */

    private String name;
    private Object ids;
    private String id;

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    private String problem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getIds() {
        return ids;
    }

    public void setIds(Object ids) {
        this.ids = ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

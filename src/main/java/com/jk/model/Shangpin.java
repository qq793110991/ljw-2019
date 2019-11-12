package com.jk.model;

import java.io.Serializable;

public class Shangpin implements Serializable{

    private static final long serialVersionUID = 8218878443728777178L;

    private Integer sid;

    private String sname;

    private String stype;

    private Integer sprice;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public Integer getSprice() {
        return sprice;
    }

    public void setSprice(Integer sprice) {
        this.sprice = sprice;
    }

    @Override
    public String toString() {
        return "Shangpin{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", stype='" + stype + '\'' +
                ", sprice=" + sprice +
                '}';
    }
}

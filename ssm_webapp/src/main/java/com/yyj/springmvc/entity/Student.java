package com.yyj.springmvc.entity;

import java.io.Serializable;

/**
 * student
 *
 * @author yan yinjie
 * @date 2026/03/10
 */
public class Student implements Serializable {
    public String sid;

    public String sname;

    public Integer sage;

    public String ssex;

    private static final long serialVersionUID = 1L;

    public Student() {
    }

    public Student(String sid, String sname, Integer sage, String ssex) {
        this.sid = sid;
        this.sname = sname;
        this.sage = sage;
        this.ssex = ssex;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getSage() {
        return sage;
    }

    public void setSage(Integer sage) {
        this.sage = sage;
    }

    public String getSsex() {
        return ssex;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }
}

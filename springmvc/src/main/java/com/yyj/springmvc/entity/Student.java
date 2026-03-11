package com.yyj.springmvc.entity;

import java.io.Serializable;

/**
 * student
 *
 * @author yan yinjie
 * @date 2026/03/10
 */
public class Student implements Serializable {
    private String sid;
    private String sname;
    private Integer sage;
    private String ssex;

    private static final long serialVersionUID = 1L;

    /**
     * 无参构造方法
     */
    public Student() {
    }

    /**
     * 全参构造方法
     * @param sid 学号
     * @param sname 姓名
     * @param sage 年龄
     * @param ssex 性别
     */
    public Student(String sid, String sname, Integer sage, String ssex) {
        this.sid = sid;
        this.sname = sname;
        this.sage = sage;
        this.ssex = ssex;
    }

    /**
     * 获取学号
     * @return 学号
     */
    public String getSid() {
        return sid;
    }

    /**
     * 设置学号
     * @param sid 学号
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * 获取姓名
     * @return 姓名
     */
    public String getSname() {
        return sname;
    }

    /**
     * 设置姓名
     * @param sname 姓名
     */
    public void setSname(String sname) {
        this.sname = sname;
    }

    /**
     * 获取年龄
     * @return 年龄
     */
    public Integer getSage() {
        return sage;
    }

    /**
     * 设置年龄
     * @param sage 年龄
     */
    public void setSage(Integer sage) {
        this.sage = sage;
    }

    /**
     * 获取性别
     * @return 性别
     */
    public String getSsex() {
        return ssex;
    }

    /**
     * 设置性别
     * @param ssex 性别
     */
    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid='" + sid + '\'' +
                ", sname='" + sname + '\'' +
                ", sage=" + sage +
                ", ssex='" + ssex + '\'' +
                '}';
    }

}
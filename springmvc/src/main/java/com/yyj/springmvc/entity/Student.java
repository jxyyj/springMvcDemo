package com.yyj.springmvc.entity;

import java.io.Serializable;

import lombok.*;

/**
 * student
 *
 * @author yan yinjie
 * @date 2026/03/10
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    private String sid;

    private String sname;

    private Integer sage;

    private String ssex;

    private static final long serialVersionUID = 1L;
}

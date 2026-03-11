package com.yyj.springmvc.service;


import com.yyj.springmvc.entity.Student;

import java.util.List;

/**
 * 学生服务接口
 *
 * @author yan yinjie
 * @date 2026/03/10
 */
public interface StudentService {
    /**
     * 获取所有学生
     *
     * @return {@link List }<{@link Student }>
     */
    List<Student> getAllStudents();

    /**
     * 通过id获取学生
     *
     * @param sid sid
     * @return {@link Student }
     */
    Student getStudentById(String sid);

    /**
     * 添加学生
     *
     * @param student 学生
     * @return int
     */
    int addStudent(Student student);

    /**
     * 更新学生
     *
     * @param student 学生
     * @return int
     */
    int updateStudent(Student student);

    /**
     * 删除学生
     *
     * @param sid sid
     * @return int
     */
    int deleteStudent(String sid);
}

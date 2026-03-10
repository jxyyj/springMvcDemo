package com.yyj.springmvc.service.impl;

import com.yyj.springmvc.entity.Student;
import com.yyj.springmvc.mapper.StudentMapper;
import com.yyj.springmvc.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.selectAll();
    }

    @Override
    public Student getStudentById(String sid) {
        return studentMapper.selectById(sid);
    }

    @Override
    public int addStudent(Student student) {
        // 检查学生是否已存在
        if (studentMapper.selectById(student.getSid()) != null) {
            log.error("学生 {} 已存在", student.getSid());
            return 0;
        }
        return studentMapper.insert(student);
    }

    @Override
    public int updateStudent(Student student) {
        return studentMapper.updateById(student);
    }

    @Override
    public int deleteStudent(String sid) {
        return studentMapper.deleteById(sid);
    }
}

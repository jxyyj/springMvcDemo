package com.yyj.springmvc.service.impl;

import com.yyj.springmvc.entity.Student;
import com.yyj.springmvc.mapper.StudentMapper;
import com.yyj.springmvc.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
    
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
    public String addStudent(Student student) {
        // 检查学生是否已存在
        if (studentMapper.selectById(student.getSid()) != null) {
            log.error("学生 {} 已存在", student.getSid());
            return "学生已存在";
        }
        // 插入学生
        int result = studentMapper.insert(student);
        if (result > 0) {
            log.info("成功添加学生 {}", student.getSid());
            return null;
        }
        log.error("添加学生 {} 失败", student.getSid());
        return "添加学生失败";
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

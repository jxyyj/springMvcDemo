package com.yyj.springmvc.controller;

import com.yyj.springmvc.common.Result;
import com.yyj.springmvc.entity.Student;
import com.yyj.springmvc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/getAll")
    public Result<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return Result.success(students);
    }

    @GetMapping("/getById/{sid}")
    public Result<Student> getStudentById(@PathVariable String sid) {
        Student student = studentService.getStudentById(sid);
        if (student == null) {
            return Result.error(404, "学生不存在");
        }
        return Result.success(student);
    }

    @PostMapping("/add")
    public Result<Integer> addStudent(@RequestBody Student student) {
        int result = studentService.addStudent(student);
        if (result > 0) {
            return Result.success(result, "添加学生成功");
        }
        return Result.error("添加学生失败");
    }

    @PostMapping("/update")
    public Result<Integer> updateStudent(@RequestBody Student student) {
        int result = studentService.updateStudent(student);
        if (result > 0) {
            return Result.success(result, "更新学生成功");
        }
        return Result.error("更新学生失败");
    }

    @DeleteMapping("/delete")
    public Result<Integer> deleteStudent(@RequestParam String sid) {
        int result = studentService.deleteStudent(sid);
        if (result > 0) {
            return Result.success(result, "删除学生成功");
        }
        return Result.error("删除学生失败");
    }
}

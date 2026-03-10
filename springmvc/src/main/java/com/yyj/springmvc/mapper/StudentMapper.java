package com.yyj.springmvc.mapper;

import com.yyj.springmvc.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学生映射器
 *
 * @author yan yinjie
 * @date 2026/03/10
 */
@Mapper
public interface StudentMapper {
    /**
     * 插入学生
     *
     * @param student 学生
     * @return int
     */
    int insert(Student student);

    /**
     * 查询所有学生
     *
     * @return {@link List }<{@link Student }>
     */
    List<Student> selectAll();
    /**
     * 根据id查询学生
     *
     * @param sid sid
     * @return {@link Student }
     */
    Student selectById(String sid);

    /**
     * 根据id更新学生
     *
     * @param student 学生
     * @return int
     */
    int updateById(Student student);

    /**
     * 根据id删除学生
     *
     * @param sid sid
     * @return int
     */
    int deleteById(String sid);
}
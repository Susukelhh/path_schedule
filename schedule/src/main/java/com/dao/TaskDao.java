package com.dao;

import com.domain.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TaskDao {
    //查询所有任务
    @Select("select * from task")
    List<Task> selectAllTasks();

    //查询第一个机器人的任务
    @Select("select * from task where robot_id = 1")
    List<Task> selectFirstTasks();

    //查询第二个机器人的任务
    @Select("select * from task where robot_id = 2")
    List<Task> selectSecondTasks();

    //根据id查询任务
    @Select("select * from task where id = #{id}")
    Task getById(Integer id);

    //更改任务属性
    @Update("update task set next_task = #{next_task},empty_price = #{empty_price},robot_id=#{robot_id} where id = #{id}")
    boolean updateTasks(Task task);


}

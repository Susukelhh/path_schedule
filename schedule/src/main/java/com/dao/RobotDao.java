package com.dao;

import com.domain.Robot;
import com.domain.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RobotDao {
    //查询所有机器人
    @Select("select * from robot")
    List<Robot> selectAllRobots();

    //查询一号机器人
    @Select("select * from robot where id = 1")
    Robot selectFirstRobots();

    //查询二号机器人
    @Select("select * from robot where id = 2")
    Robot selectSecondRobots();

    //更改机器人属性
    @Update("update robot set tasks=#{tasks},price=#{price} where id = #{id}")
    boolean updateRobots(Robot robot);
}

package com.dao;

import com.domain.Schedule;
import com.domain.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ScheduleDao {
    //查询所有任务分配情况
    @Select("select * from schedule")
    List<Schedule> selectAllSchedule();

    //根据id查询任务分配情况
    @Select("select * from schedule where id = #{id}")
    Schedule selectScheduleById(Integer id);

    //插入新的任务分配情况
    @Insert("insert into schedule(id,firstTasks,firstPrice) values (#{id},#{firstTasks},#{firstPrice})")
    void insertSchedule(Schedule schedule);

    //更改任务分配情况
    @Update("update schedule set id=#{id},firstTasks = #{firstTasks},secondTasks = #{secondTasks},firstPrice = #{firstPrice},secondPrice = #{secondPrice},allPrice=#{allPrice} where id = #{id}")
    boolean updateSchedule(Schedule schedule);

    //查询是否存在某个数据
    @Select("select count(*) from schedule where id = #{id}")
    Integer selectIfContainById(Integer id);
}

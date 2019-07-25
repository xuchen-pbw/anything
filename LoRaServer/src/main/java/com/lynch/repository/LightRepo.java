package com.lynch.repository;

import com.lynch.domain.Light;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by lynch on 2019/1/7. <br>
 **/
@Repository
public interface LightRepo extends JpaRepository<Light, Long> {

    /**
     * 获取灯最近状态
     *
     * @param deviceAddr
     * @return
     */
    @Query(value = "SELECT * FROM t_light l JOIN t_device_light dl ON l.light_id = dl.`light_id` JOIN t_device d ON d.device_id= dl.`device_id` WHERE d.device_addr = ?1", nativeQuery = true)
    Light getLightStatus(@Param("deviceAddr") String deviceAddr);

    /**
     * 获取灯最近开关状态
     *
     * @param deviceAddr
     * @return
     */
    @Query(value = "SELECT l.light_switch FROM t_light l JOIN t_device_light dl ON l.light_id = dl.`light_id` JOIN t_device d ON d.device_id= dl.`device_id` WHERE d.device_addr = ?1", nativeQuery = true)
    String getLightSwitch(@Param("deviceAddr") String deviceAddr);

    /**
     * 获取灯的最新亮度
     *
     * @param deviceAddr
     * @return
     */
    @Query(value = "SELECT l.brightness FROM t_light l JOIN t_device_light dl ON l.light_id = dl.`light_id` JOIN t_device d ON d.device_id= dl.`device_id` WHERE d.device_addr = ?1", nativeQuery = true)
    String getLightBrightness(@Param("deviceAddr") String deviceAddr);

    /**
     * 获取灯的最新阈值
     *
     * @param deviceAddr
     * @return
     */
    @Query(value = "SELECT l.threshold FROM t_light l JOIN t_device_light dl ON l.light_id = dl.`light_id` JOIN t_device d ON d.device_id= dl.`device_id` WHERE d.device_addr = ?1", nativeQuery = true)
    String getLightThreshold(@Param("deviceAddr") String deviceAddr);

    /**
     * 获取最新前端更新标志位
     *
     * @param deviceAddr
     * @return
     */
    @Query(value = "SELECT l.web_contrl_flag FROM t_light l JOIN t_device_light dl ON l.light_id = dl.`light_id` JOIN t_device d ON d.device_id= dl.`device_id` WHERE d.device_addr = ?1 order by l.light_id DESC limit 1", nativeQuery = true)
    String getLightwebContrlFlag(@Param("deviceAddr") String deviceAddr);

    @Query("SELECT l FROM Light l where l.deviceAddr=:deviceAddr")
    List<Light> getAllDataByDeviceAddr(@Param("deviceAddr") String deviceAddr);
}

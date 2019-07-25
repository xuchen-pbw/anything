package com.lynch.repository;

import com.lynch.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lynch on 2019/3/11. <br>
 **/
@Repository
public interface DeviceRepo extends JpaRepository<Device, Long> {
    /**
     * 查找终端
     *
     * @param deviceAddr
     * @return
     */
    @Query("select d from Device d where d.deviceAddr=:deviceAddr")
    Device findByDeviceAddr(@Param("deviceAddr") String deviceAddr);


    /**
     * 查找下行Fcnt
     *
     * @param deviceAddr
     * @return
     */
    @Query("select d.downFcnt from Device d where d.deviceAddr=:deviceAddr")
    Integer findDownFcntByDeviceAddr(@Param("deviceAddr") String deviceAddr);

    /**
     * 查找上行Fcnt
     *
     * @param deviceAddr
     * @return
     */
    @Query("select d.upFcnt from Device d where d.deviceAddr=:deviceAddr")
    Integer findUpFcntByDeviceAddr(@Param("deviceAddr") String deviceAddr);


    /**
     * 查找网关地址
     *
     * @param deviceAddr
     * @return
     */
    @Query("select d.gatewayHost from Device d where d.deviceAddr=:deviceAddr")
    String findGatewayHostByDeviceAddr(@Param("deviceAddr") String deviceAddr);

    /**
     * 更新网关MAC
     *
     * @param gatewayHost
     * @param deviceAddr
     */
    @Transactional
    @Modifying
    @Query("update  Device d set d.gatewayHost=:gatewayHost where d.deviceAddr=:deviceAddr")
    void updateGatewayHost(@Param("gatewayHost") String gatewayHost, @Param("deviceAddr") String deviceAddr);

    /**
     * 下行Fcnt更新
     *
     * @param downFcnt
     * @param deviceAddr
     */
    @Transactional
    @Modifying
    @Query("update  Device d set d.downFcnt=:downFcnt where d.deviceAddr=:deviceAddr")
    void updateDownFcnt(@Param("downFcnt") Integer downFcnt, @Param("deviceAddr") String deviceAddr);

    /**
     * 上行Fcnt更新
     *
     * @param upFcnt
     * @param deviceAddr
     */
    @Transactional
    @Modifying
    @Query("update  Device d set d.upFcnt=:upFcnt where d.deviceAddr=:deviceAddr")
    void updateUpFcnt(@Param("upFcnt") Integer upFcnt, @Param("deviceAddr") String deviceAddr);


}

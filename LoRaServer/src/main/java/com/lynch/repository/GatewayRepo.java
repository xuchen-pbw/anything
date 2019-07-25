package com.lynch.repository;

import com.lynch.domain.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by lynch on 2019-05-17. <br>
 **/
@Repository
public interface GatewayRepo extends JpaRepository<Gateway, Long> {
    /**
     * 查找网关
     *
     * @param gatewayAddr
     * @param gatewayHost
     * @return
     */
    @Query("select g from Gateway g where g.gatewayAddr=:gatewayAddr or g.gatewayHost=:gatewayHost")
    Gateway findByGatewayAddr(@Param("gatewayAddr") String gatewayAddr,@Param("gatewayHost") String gatewayHost);

    /**
     * 查找网关地址
     *
     * @param gatewayHost
     * @return
     */
    @Query("select g.gatewayAddr from Gateway g where g.gatewayHost=:gatewayHost")
    String findAddrByGatewayHost(@Param("gatewayHost") String gatewayHost);
}

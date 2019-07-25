package com.lynch.repository;

import com.lynch.domain.InfoGateWayStatForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lynch on 2019/1/7. <br>
 **/
@Repository
public interface GateWayStatRepo extends JpaRepository<InfoGateWayStatForm, Long> {
}

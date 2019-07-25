package com.lynch.repository;

import com.lynch.domain.InfoFSKModEndForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by lynch on 2019/1/7. <br>
 **/
@Repository
public interface FSKModEndRepo extends JpaRepository<InfoFSKModEndForm, Long> {
    List<InfoFSKModEndForm> findByModu(String modu);

    List<InfoFSKModEndForm> findByTimestampBetween(Date start, Date end);
}

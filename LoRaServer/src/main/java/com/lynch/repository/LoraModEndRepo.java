package com.lynch.repository;

import com.lynch.domain.InfoLoraModEndForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lynch on 2019/1/7. <br>
 **/
@Repository
public interface LoraModEndRepo extends JpaRepository<InfoLoraModEndForm, Long> {
}

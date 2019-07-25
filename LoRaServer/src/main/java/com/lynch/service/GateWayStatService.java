package com.lynch.service;

import com.lynch.domain.InfoGateWayStatForm;
import com.lynch.repository.GateWayStatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lynch on 2019/1/7. <br>
 **/
@Service
public class GateWayStatService {

    @Autowired
    GateWayStatRepo gateWayStatRepo;

    public void save(InfoGateWayStatForm infoGateWayStat) {
        gateWayStatRepo.save(infoGateWayStat);
    }
}
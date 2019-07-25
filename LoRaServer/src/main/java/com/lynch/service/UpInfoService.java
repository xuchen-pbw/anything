package com.lynch.service;

import com.lynch.domain.InfoFSKModEndForm;
import com.lynch.domain.InfoGateWayStatForm;
import com.lynch.domain.InfoLoraModEndForm;
import com.lynch.domain.UpInfoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lynch on 2019/1/7. <br>
 **/
@Service
public class UpInfoService {
    @Autowired
    FSKModEndService fskModEndService;
    @Autowired
    GateWayStatService gateWayStatService;
    @Autowired
    LoraModEndService loraModEndService;


    public void save(UpInfoForm upInfo) {
        if (upInfo instanceof InfoFSKModEndForm) {
            InfoFSKModEndForm infoFSKModEnd = (InfoFSKModEndForm) upInfo;
            fskModEndService.save(infoFSKModEnd);
        }else if (upInfo instanceof InfoLoraModEndForm) {
            loraModEndService.save((InfoLoraModEndForm) upInfo);
        }else if (upInfo instanceof InfoGateWayStatForm) {
            gateWayStatService.save((InfoGateWayStatForm) upInfo);
        }
    }
}


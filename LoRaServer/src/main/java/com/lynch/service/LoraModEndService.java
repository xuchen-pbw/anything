package com.lynch.service;

import com.lynch.domain.InfoLoraModEndForm;
import com.lynch.repository.LoraModEndRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lynch on 2019/1/7. <br>
 **/
@Service
public class LoraModEndService {
    @Autowired
    LoraModEndRepo loraModEndRepo;

    public void save(InfoLoraModEndForm infoLoraModEnd) {
        loraModEndRepo.save(infoLoraModEnd);
    }
}

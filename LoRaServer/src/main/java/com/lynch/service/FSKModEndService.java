package com.lynch.service;

import com.lynch.domain.InfoFSKModEndForm;
import com.lynch.repository.FSKModEndRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lynch on 2019/1/7. <br>
 **/
@Service
public class FSKModEndService {
    @Autowired
    private FSKModEndRepo fskModEndRepo;

    public String save(InfoFSKModEndForm infoFSKModEnd) {
        fskModEndRepo.save(infoFSKModEnd);
        return "infoFSKModEnd saved";
    }
}

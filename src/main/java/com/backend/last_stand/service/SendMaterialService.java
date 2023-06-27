package com.backend.last_stand.service;

import com.backend.last_stand.entity.GetMaterial;
import com.backend.last_stand.entity.SendMaterial;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SendMaterialService  extends IService<SendMaterial> {
    void declare(Long mid, Long uid);

    void declareAll();
}

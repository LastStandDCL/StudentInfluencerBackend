package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.GetMaterial;
import com.backend.last_stand.entity.SendMaterial;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SendMaterialMapper extends BaseMapper<SendMaterial> {

    void declare(Long mid);

    Integer getRemainInteger(Long mid);
}

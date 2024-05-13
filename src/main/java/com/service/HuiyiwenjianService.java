package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.HuiyiwenjianEntity;
import java.util.Map;

/**
 * 会议文件管理 服务类
 * @author 
 * @since 2021-03-16
 */
public interface HuiyiwenjianService extends IService<HuiyiwenjianEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);

}
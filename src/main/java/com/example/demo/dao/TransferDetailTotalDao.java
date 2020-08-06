package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.TransferDetailTotal;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 */
public interface TransferDetailTotalDao extends BaseMapper<TransferDetailTotal> {

    TransferDetailTotal queryObj(Map<String, Object> map);

    List<TransferDetailTotal> queryTranList();
}

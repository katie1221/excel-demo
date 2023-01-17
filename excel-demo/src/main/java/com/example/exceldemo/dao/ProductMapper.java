package com.example.exceldemo.dao;

import com.example.exceldemo.entity.Product;
import com.example.exceldemo.entity.User;
import com.example.exceldemo.mybatis.MyMapper;
import org.springframework.stereotype.Repository;

/**
 * 商品
 * @author qzz
 */
@Repository
public interface ProductMapper extends MyMapper<Product> {

}

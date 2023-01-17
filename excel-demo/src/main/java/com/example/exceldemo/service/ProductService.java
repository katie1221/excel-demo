package com.example.exceldemo.service;

import com.example.exceldemo.entity.Product;
import com.example.exceldemo.entity.User;
import com.example.exceldemo.excel.ProductExcel;

import java.util.List;

/**
 * 业务层
 * @author qzz
 */
public interface ProductService {


    /**
     * 批量新增商品信息
     * @param productList
     * @return
     */
    Integer addBatchProduct(List<Product> productList);

    /**
     * 获取商品信息
     * @return
     */
    List<ProductExcel> findProductList();
}

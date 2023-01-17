package com.example.exceldemo.service.impl;

import com.example.exceldemo.dao.ProductMapper;
import com.example.exceldemo.dao.UserMapper;
import com.example.exceldemo.entity.Product;
import com.example.exceldemo.entity.User;
import com.example.exceldemo.excel.ProductExcel;
import com.example.exceldemo.service.BaseService;
import com.example.exceldemo.service.ProductService;
import com.example.exceldemo.service.UserService;
import com.example.exceldemo.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qzz
 */
@Service("ProductService")
public class ProductServiceImpl extends BaseService implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 批量新增公司信息
     * @param productList
     * @return
     */
    @Override
    public Integer addBatchProduct(List<Product> productList) {
        for(Product product : productList){
            fillCommonField(product);
        }
        return productMapper.insertList(productList);
    }

    /**
     * 获取商品信息
     * @return
     */
    @Override
    public List<ProductExcel> findProductList() {
        List<Product> productList = productMapper.selectAll();
        List<ProductExcel> productExcelList = new ListUtils<ProductExcel>().copyProperties(productList, ProductExcel.class);
        return productExcelList;
    }

}

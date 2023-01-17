package com.example.exceldemo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import java.io.Serializable;

/**
 * 商品
 * @author qzz
 */
@Data
public class ProductExcel implements Serializable {

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 副标题
     */
    @Excel(name = "副标题")
    private String subTitle;

    /**
     * 售价
     * type：导出类型 1 是文本 2 是图片,3 是函数,10 是数字 默认是文本
     */
    @Excel(name = "售价")
    private String salePrice;
}

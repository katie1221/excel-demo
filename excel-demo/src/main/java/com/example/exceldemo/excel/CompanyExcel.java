package com.example.exceldemo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import java.io.Serializable;

/**
 * 公司
 * @author qzz
 */
@Data
public class CompanyExcel implements Serializable {

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String name;

    /**
     * width和height是导出是图片的宽度和高度
     * type：导出类型 1 是文本 2 是图片,3 是函数,10 是数字 默认是文本
     * imageType 导出类型;1:从file读取;2:是从数据库中读取,默认是文件;同样导入也是一样的
     * savePath：保存的路径（右击保存图片的文件夹，选Copy Path就行，这里是存在static下的imgs中）
     */
    @Excel(name = "公司Logo", width = 40, height = 20, type = 2, savePath = "F:\\company")
    private String logo;

    /**
     * 公司地址
     */
    @Excel(name = "公司地址", width = 60)
    private String address;
}

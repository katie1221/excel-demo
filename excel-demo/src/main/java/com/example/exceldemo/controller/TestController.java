package com.example.exceldemo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.bean.BeanUtil;
import com.example.exceldemo.common.Result;
import com.example.exceldemo.entity.Company;
import com.example.exceldemo.entity.Product;
import com.example.exceldemo.entity.User;
import com.example.exceldemo.excel.CompanyExcel;
import com.example.exceldemo.excel.ProductExcel;
import com.example.exceldemo.excel.UserExcel;
import com.example.exceldemo.service.ProductService;
import com.example.exceldemo.service.UserService;
import com.example.exceldemo.utils.ExcelUtils;
import com.example.exceldemo.utils.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 教师
 * @author qzz
 */
@RestController
@RequestMapping("/teacher")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    /**
     * 批量导入 多sheet
     * @param multipartFile
     * @return
     */
    @RequestMapping("/upload")
    public Result importExcel(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request){
        try{
            //标题占几行
            Integer titleRows =1;
            //表头占几行
            Integer headerRows =1;
            String originalFileName = multipartFile.getOriginalFilename();
            String suffix = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf(".") + 1);
            File file = File.createTempFile(suffix, String.valueOf(System.currentTimeMillis()));
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            Workbook workBook = ExcelUtils.getWorkBook(file);
            for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
                String sheetName = workBook.getSheetAt(numSheet).getSheetName();
                System.out.println("sheet名："+sheetName);
                if(numSheet==0){
                    //用户信息列表导入
                    List<User> userList = new ArrayList<>();
                    List<UserExcel> userExcelList = ExcelUtils.importExcelMore(file, titleRows, headerRows, numSheet, UserExcel.class);
                    for(UserExcel userExcel:userExcelList){
                        User user = new User();
                        BeanUtil.copyProperties(userExcel,user);
                        user.setSex(userExcel.getSexStr().equals("男")?0:1);
                        user.setStatus(0);
                        userList.add(user);
                    }
                    //批量新增用户
                    userService.addBatchUser(userList);
                }else if(numSheet==1){
                    //商品信息列表导入
                    List<ProductExcel> productExcelList = ExcelUtils.importExcelMore(file, titleRows, headerRows, numSheet, ProductExcel.class);
                    List<Product> productList = new ListUtils<Product>().copyProperties(productExcelList, Product.class);
                    //批量新增用户
                    productService.addBatchProduct(productList);
                }
            }

            return Result.success("上传成功:"+originalFileName);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("上传失败");
        }
    }

    /**
     * 导出excel 多sheet
     * @param response
     */
    @RequestMapping("/exportExcelSheets")
    public void exportExcel(HttpServletResponse response) {
        //excel多sheet导出
        Workbook workbook = null;
        try {
            //创建参数对象（用来设定excel的sheet1内容等信息）
            ExportParams userExportParams = new ExportParams();
            //设置sheet的名称
            userExportParams.setSheetName("用户表信息");
            //设置sheet表头名称
            userExportParams.setTitle("用户列表");
            //创建sheet1使用map
            Map<String, Object> userExportMap = new HashMap<>();
            //title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
            userExportMap.put("title", userExportParams);
            //模板导出对应的实体类型
            userExportMap.put("entity", UserExcel.class);
            //获取要导出的用户列表
            List<UserExcel> users = userService.findUserList();
            //sheet1中要填充的数据
            userExportMap.put("data", users);

            //创建参数对象（用来设定excel的sheet2内容等信息）
            ExportParams productExportParams = new ExportParams();
            //设置sheet的名称
            productExportParams.setSheetName("商品表信息");
            //设置sheet表头名称
            productExportParams.setTitle("商品列表");
            //创建sheet2使用map
            Map<String, Object> productExportMap = new HashMap<>();
            //title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
            productExportMap.put("title", productExportParams);
            //模板导出对应的实体类型
            productExportMap.put("entity", ProductExcel.class);
            //获取要导出的用户列表
            List<ProductExcel> productExcelList = productService.findProductList();
            //sheet1中要填充的数据
            productExportMap.put("data", productExcelList);

            //将sheet1、sheet2使用Map进行包装
            List<Map<String, Object>> sheetsList = new ArrayList<>();
            //后续增加sheet组，则后面继续追加即可;
            sheetsList.add(userExportMap);
            sheetsList.add(productExportMap);

            //执行方法
            workbook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
            //设置编码格式
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            //设置内容类型
            response.setContentType("application/octet-stream");
            //设置头及文件命名
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户、商品信息导出", StandardCharsets.UTF_8.name()));

            //写出流
            workbook.write(response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(workbook != null){
                try {
                    //强行关流
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

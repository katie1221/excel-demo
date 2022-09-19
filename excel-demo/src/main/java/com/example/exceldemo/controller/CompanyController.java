package com.example.exceldemo.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.example.exceldemo.common.Result;
import com.example.exceldemo.entity.Company;
import com.example.exceldemo.excel.CompanyExcel;
import com.example.exceldemo.service.CompanyService;
import com.example.exceldemo.utils.ExcelUtils;
import com.example.exceldemo.utils.ListUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 公司
 * @author qzz
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Value("${upload.dir}")
    private String filePath;

    /**
     * 批量导入,含图片
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    public Result importExcel(@RequestParam("file") MultipartFile file,HttpServletRequest request){
       try{
           String originalFileName = file.getOriginalFilename();
           //excel导入
           List<CompanyExcel> companyExcelList = ExcelUtils.importExcel(file,1,1,CompanyExcel.class);
           //调用getImgName方法获取图片名
           List<Company> companyList = new ListUtils<Company>().copyProperties(companyExcelList,Company.class);
           //图片转存
           saveImage(request,companyList);
           //调用方法将数据保存到数据库
           companyService.addBatchCompany(companyList);
           return Result.success("上传成功:"+originalFileName);
       }catch (Exception e){
           e.printStackTrace();
           return Result.fail("上传失败");
       }
    }

    /**
     * 导出excel,含图片
     * @param response
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response){
        try {
            //1.查询出要导出的数据
            List<Company> companyList = companyService.findAll();
            List<CompanyExcel> companyExcelList = new ListUtils<CompanyExcel>().copyProperties(companyList, CompanyExcel.class);
            //2.导出时的excel名称
            String dateString = String.valueOf(System.currentTimeMillis());
            String fileName = "公司信息" + dateString;
            //3.工具类导出Excel
            ExportParams params = new ExportParams();
            params.setTitle("公司信息列表");
            params.setSheetName("公司信息");
            //需要设置type=ExcelType.HSSF，不然图片不显示
            params.setType(ExcelType.HSSF);
            ExcelUtils.exportExcel(companyExcelList, CompanyExcel.class,fileName,params,response);
        }catch (Exception e){

        }
    }

    /**
     * 文件转存
     * @param companyList
     */
    private void saveImage(HttpServletRequest request, List<Company> companyList) {
        //遍历每一条数据
        for (Company company : companyList) {
            if (StringUtils.isNotEmpty(company.getLogo())) {
                try {
                    //获取到暂存的文件
                    File tmpFile = new File(company.getLogo());
                    FileInputStream fileInputStream = new FileInputStream(tmpFile);
                    //转换为 multipartFile 类
                    MultipartFile multipartFile = new MockMultipartFile("file", tmpFile.getName(), "text/plain", IOUtils.toByteArray(fileInputStream));
                    //获取当前的日期，按日期归档
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
                    String format = sdf.format(new Date());
                    //获取到本地磁盘的路径，先建立路径
                    File file = new File(filePath + format);
                    if (!file.isDirectory()) {
                        file.mkdirs();
                    }
                    //初始文件名
                    String originName = tmpFile.getName();
                    //后缀名
                    String suffix = originName.substring(originName.lastIndexOf("."));
                    //存加密后的uuid+后缀作为存到path里的文件名
                    String fileName = UUID.randomUUID() + suffix;
                    File dest = new File(file.getAbsoluteFile() + File.separator + fileName);
                    String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/company/" + format + fileName;
                    multipartFile.transferTo(dest);
                    company.setLogo(filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

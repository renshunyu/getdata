package com.asia.controller;

import com.asia.Result.JsonResult;
import com.asia.Result.JsonSerie;
import com.asia.Result.ResultCode;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by renshunyu on 2018/5/10.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    //@Value("${spring.profiles}")
    //private String env;
    Workbook workbook;
    Sheet sheet;

    private String userName = "admin";
    private String pw = "pword";
    @Value(value="${com.backlog.sia.svnpath}")
    private String siasvnpath="http://10.1.198.30/svn/UAPPROGRAM/products/ailkiap30/doc/02-需求文档/04-产品需求-敏捷";
    @Value(value="${com.backlog.sia.dir}")
    private String siadir="AISIA-V04R02";
    @Value(value="${com.backlog.sia.file}")
    private String siafile="13-【迭代Backlog】-SIA-C13.xlsx";

    @Value(value="${com.backlog.ssa.svnpath}")
    private String ssasvnpath="http://10.1.198.30/svn/UAPPROGRAM/products/ailkiap30/doc/02-需求文档/04-产品需求-敏捷";
    @Value(value="${com.backlog.ssa.dir}")
    private String ssadir="AISIA-V04R02";
    @Value(value="${com.backlog.ssa.file}")
    private String ssafile="13-【迭代Backlog】-SIA-C13.xlsx";

    @Value(value="${com.backlog.uap.svnpath}")
    private String uapsvnpath="http://10.1.198.30/svn/UAPPROGRAM/products/ailkiap30/doc/02-需求文档/04-产品需求-敏捷";
    @Value(value="${com.backlog.uap.dir}")
    private String uapdir="AISIA-V04R02";
    @Value(value="${com.backlog.uap.file}")
    private String uapfile="13-【迭代Backlog】-SIA-C13.xlsx";

    @Value(value="${com.backlog.fort.svnpath}")
    private String fortsvnpath="http://10.1.198.30/svn/UAPPROGRAM/products/ailkiap30/doc/02-需求文档/04-产品需求-敏捷";
    @Value(value="${com.backlog.fort.dir}")
    private String fortdir="AISIA-V04R02";
    @Value(value="${com.backlog.fort.file}")
    private String fortfile="13-【迭代Backlog】-SIA-C13.xlsx";

    @Value(value="${com.backlog.sen.svnpath}")
    private String sensvnpath="http://10.1.198.30/svn/UAPPROGRAM/products/ailkiap30/doc/02-需求文档/04-产品需求-敏捷";
    @Value(value="${com.backlog.sen.dir}")
    private String sendir="AISIA-V04R02";
    @Value(value="${com.backlog.sen.file}")
    private String senfile="13-【迭代Backlog】-SIA-C13.xlsx";

    /**
     * 登录
     * @param response：用于保存token到cookie中
     * @param map：包含userName和password
     * @return
     */
    @RequestMapping("/login")
    public JsonResult login(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> map) {
        if(userName.equals(map.get("userName")) && pw.equals(map.get("password"))){
            return new JsonResult(ResultCode.SUCCESS, "登录成功！", null);
        }else {
            return new JsonResult(ResultCode.NOT_LOGIN, "登录失败！", null);
        }
    }
    @RequestMapping("/siaserie")
    public JsonSerie[] siaserie() {
        JsonSerie j1 = new JsonSerie("计划","剩余1");
        JsonSerie j2 = new JsonSerie("执行","剩余2");
        JsonSerie[] series={j1,j2};
        //=============================================================================

        Process process = null;
        File f=new File(".\\sia");
        if ( !f.exists() ) {
            try {
                process = Runtime.getRuntime().exec("svn checkout "+siasvnpath+"/"+siadir+" .\\sia", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            try {
                process = Runtime.getRuntime().exec("svn update  .\\sia", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //=============================================================================
        try {
            workbook = new XSSFWorkbook(new FileInputStream(".\\sia\\"+siafile));
            //workbook = XSSWorkbook.getWorkbook(new File(".\\AISIA-V04R02\\13-【迭代Backlog】-SIA-C13.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Double wd =  workbook.getSheet("【TASK】").getRow(5).getCell(8).getNumericCellValue();
        Double[] data1={wd,wd*(14.00/15),wd*(13.00/15),wd*(12.00/15),wd*(11.00/15),wd*(10.00/15),wd*(9.00/15),wd*(8.00/15),wd*(7.00/15),wd*(6.00/15),wd*(5.00/15),wd*(4.00/15),wd*(3.00/15),wd*(2.00/15),wd*(1.00/15),wd*(0.00/15)};
        j1.setData(data1);
        Integer lie = 8;
        for(Integer i=8;i<30;i++){
            if( 0 == workbook.getSheet("【TASK】").getRow(5).getCell(i).getNumericCellValue()){
                lie =i;
                break;
            }
        }
        Double[] data2= new Double[lie-8];
        for(Integer i=8;i<lie;i++){
            data2[i-8]=workbook.getSheet("【TASK】").getRow(5).getCell(i).getNumericCellValue();
        }
        j2.setData(data2);

        return series;
    }
    @RequestMapping("/uapserie")
    public JsonSerie[] uapserie() {
        JsonSerie j1 = new JsonSerie("计划","剩余1");
        JsonSerie j2 = new JsonSerie("执行","剩余2");
        JsonSerie[] series={j1,j2};
        //=============================================================================
        String svnpath;
        String backlogfir;
        String backfile;
        Process process = null;
        File f=new File(".\\uap");
        if ( !f.exists() ) {
            try {
                process = Runtime.getRuntime().exec("svn checkout "+uapsvnpath+"/"+uapdir+" .\\uap", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            try {
                process = Runtime.getRuntime().exec("svn update .\\uap", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //=============================================================================
        try {
            workbook = new XSSFWorkbook(new FileInputStream(".\\uap\\"+uapfile));
            //workbook = XSSWorkbook.getWorkbook(new File(".\\AISIA-V04R02\\13-【迭代Backlog】-SIA-C13.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Double wd =  workbook.getSheet("【TASK】").getRow(5).getCell(8).getNumericCellValue();
        Double[] data1={wd,wd*(14.00/15),wd*(13.00/15),wd*(12.00/15),wd*(11.00/15),wd*(10.00/15),wd*(9.00/15),wd*(8.00/15),wd*(7.00/15),wd*(6.00/15),wd*(5.00/15),wd*(4.00/15),wd*(3.00/15),wd*(2.00/15),wd*(1.00/15),wd*(0.00/15)};
        j1.setData(data1);
        Integer lie = 8;
        for(Integer i=8;i<30;i++){
            if( 0 == workbook.getSheet("【TASK】").getRow(5).getCell(i).getNumericCellValue()){
                lie =i;
                break;
            }
        }
        Double[] data2= new Double[lie-8];
        for(Integer i=8;i<lie;i++){
            data2[i-8]=workbook.getSheet("【TASK】").getRow(5).getCell(i).getNumericCellValue();
        }
        j2.setData(data2);

        return series;
    }

    @RequestMapping("/ssaserie")
    public JsonSerie[] ssaserie() {
        JsonSerie j1 = new JsonSerie("计划","剩余1");
        JsonSerie j2 = new JsonSerie("执行","剩余2");
        JsonSerie[] series={j1,j2};
        //=============================================================================
        String svnpath;
        String backlogfir;
        String backfile;
        Process process = null;
        File f=new File(".\\ssa");
        if ( !f.exists() ) {
            try {
                process = Runtime.getRuntime().exec("svn checkout "+ssasvnpath+"/"+ssadir+" .\\ssa", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            try {
                process = Runtime.getRuntime().exec("svn update .\\ssa", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //=============================================================================
        try {
            workbook = new XSSFWorkbook(new FileInputStream(".\\ssa\\"+ssafile));
            //workbook = XSSWorkbook.getWorkbook(new File(".\\AISIA-V04R02\\13-【迭代Backlog】-SIA-C13.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Double wd =  workbook.getSheet("【TASK】").getRow(5).getCell(8).getNumericCellValue();
        Double[] data1={wd,wd*(14.00/15),wd*(13.00/15),wd*(12.00/15),wd*(11.00/15),wd*(10.00/15),wd*(9.00/15),wd*(8.00/15),wd*(7.00/15),wd*(6.00/15),wd*(5.00/15),wd*(4.00/15),wd*(3.00/15),wd*(2.00/15),wd*(1.00/15),wd*(0.00/15)};
        j1.setData(data1);
        Integer lie = 8;
        for(Integer i=8;i<30;i++){
            if( 0 == workbook.getSheet("【TASK】").getRow(5).getCell(i).getNumericCellValue()){
                lie =i;
                break;
            }
        }
        Double[] data2= new Double[lie-8];
        for(Integer i=8;i<lie;i++){
            data2[i-8]=workbook.getSheet("【TASK】").getRow(5).getCell(i).getNumericCellValue();
        }
        j2.setData(data2);

        return series;
    }

    @RequestMapping("/fortserie")
    public JsonSerie[] fortserie() {
        JsonSerie j1 = new JsonSerie("计划","剩余1");
        JsonSerie j2 = new JsonSerie("执行","剩余2");
        JsonSerie[] series={j1,j2};
        //=============================================================================
        String svnpath;
        String backlogfir;
        String backfile;
        Process process = null;
        File f=new File(".\\fort");
        if ( !f.exists() ) {
            try {
                process = Runtime.getRuntime().exec("svn checkout "+fortsvnpath+"/"+fortdir+" .\\fort", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            try {
                process = Runtime.getRuntime().exec("svn update .\\fort", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //=============================================================================
        try {
            workbook = new XSSFWorkbook(new FileInputStream(".\\fort\\"+fortfile));
            //workbook = XSSWorkbook.getWorkbook(new File(".\\AISIA-V04R02\\13-【迭代Backlog】-SIA-C13.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Double wd =  workbook.getSheet("【TASK】").getRow(5).getCell(8).getNumericCellValue();
        Double[] data1={wd,wd*(14.00/15),wd*(13.00/15),wd*(12.00/15),wd*(11.00/15),wd*(10.00/15),wd*(9.00/15),wd*(8.00/15),wd*(7.00/15),wd*(6.00/15),wd*(5.00/15),wd*(4.00/15),wd*(3.00/15),wd*(2.00/15),wd*(1.00/15),wd*(0.00/15)};
        j1.setData(data1);
        Integer lie = 8;
        for(Integer i=8;i<30;i++){
            if( 0 == workbook.getSheet("【TASK】").getRow(5).getCell(i).getNumericCellValue()){
                lie =i;
                break;
            }
        }
        Double[] data2= new Double[lie-8];
        for(Integer i=8;i<lie;i++){
            data2[i-8]=workbook.getSheet("【TASK】").getRow(5).getCell(i).getNumericCellValue();
        }
        j2.setData(data2);

        return series;
    }

    @RequestMapping("/senserie")
    public JsonSerie[] senserie() {
        JsonSerie j1 = new JsonSerie("计划","剩余1");
        JsonSerie j2 = new JsonSerie("执行","剩余2");
        JsonSerie[] series={j1,j2};
        //=============================================================================
        String svnpath;
        String backlogfir;
        String backfile;
        Process process = null;
        File f=new File(".\\sen");
        if ( !f.exists() ) {
            try {
                process = Runtime.getRuntime().exec("svn checkout "+sensvnpath+"/"+sendir+" .\\sen", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            try {
                process = Runtime.getRuntime().exec("svn update .\\sen", null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 方法阻塞, 等待命令执行完成（成功会返回0）
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //=============================================================================
        try {
            workbook = new XSSFWorkbook(new FileInputStream(".\\sen\\"+senfile));
            //workbook = XSSWorkbook.getWorkbook(new File(".\\AISIA-V04R02\\13-【迭代Backlog】-SIA-C13.xlsx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Double wd =  workbook.getSheet("【TASK】").getRow(5).getCell(8).getNumericCellValue();
        Double[] data1={wd,wd*(14.00/15),wd*(13.00/15),wd*(12.00/15),wd*(11.00/15),wd*(10.00/15),wd*(9.00/15),wd*(8.00/15),wd*(7.00/15),wd*(6.00/15),wd*(5.00/15),wd*(4.00/15),wd*(3.00/15),wd*(2.00/15),wd*(1.00/15),wd*(0.00/15)};
        j1.setData(data1);
        Integer lie = 8;
        for(Integer i=8;i<30;i++){
            if( 0 == workbook.getSheet("【TASK】").getRow(5).getCell(i).getNumericCellValue()){
                lie =i;
                break;
            }
        }
        Double[] data2= new Double[lie-8];
        for(Integer i=8;i<lie;i++){
            data2[i-8]=workbook.getSheet("【TASK】").getRow(5).getCell(i).getNumericCellValue();
        }
        j2.setData(data2);

        return series;
    }
}

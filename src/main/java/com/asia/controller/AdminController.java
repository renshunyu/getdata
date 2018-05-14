package com.asia.controller;

import com.asia.Result.JsonResult;
import com.asia.Result.JsonSerie;
import com.asia.Result.ResultCode;

import com.asia.Result.jsonJira;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
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
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by renshunyu on 2018/5/10.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    //@Value("${spring.profiles}")
    //private String env;
    Logger log = Logger.getLogger(AdminController.class);
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

    @Value(value="${com.jira.loginurl}")
    private String loginurl = "http://10.21.17.179:8888/login.jsp";
    @Value(value="${com.jira.username}")
    private String username = "rensy";
    @Value(value="${com.jira.password}")
    private String password = "3edc@WSX";
    @Value(value="${com.jira.queryurl}")
    private String queryurl = "http://10.21.17.179:8888/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+in+%28SSSIA%2C+SSIAM%2C+SSFORT%2C+SSSSA%29+AND+issuetype+in+%28BUG%2C+%E6%95%85%E9%9A%9C%29+AND+status+in+%28Open%2C+%E5%B7%B2%E4%B8%8A%E7%BA%BF%2C+%E5%B7%B2%E5%8F%91%E5%B8%83%2C+%E5%BE%85%E5%8F%91%E5%B8%83%2C+%E5%BE%85%E6%B5%8B%E8%AF%95%2C+%E6%B5%8B%E8%AF%95%E4%B8%AD%2C+bug%E4%BF%AE%E5%A4%8D%E4%B8%AD%2C+%E5%BE%85%E9%AA%8C%E8%AF%81%2C+%E9%AA%8C%E8%AF%81%E4%B8%AD%2C+%E5%AE%9A%E4%BD%8D%E4%B8%AD%2C+%E5%BE%85%E4%BF%AE%E5%A4%8D%2C+%E6%95%85%E9%9A%9C%E4%BF%AE%E5%A4%8D%E4%B8%AD%2C+%E5%B7%B2%E6%8F%90%E4%BA%A4%29&tempMax=1000";

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
    @RequestMapping("/jiras")
    public jsonJira[] jiras() throws IOException, ParseException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(loginurl);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);

        // （3）设置http request头
        List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("content-type","application/x-www-form-urlencoded"));
        headers.add(new Header("DNT","1"));
        headers.add(new Header("Accept","text/html, application/xhtml+xml, */*"));
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        // 设置登陆时要求的信息，用户名和密码
        NameValuePair[] data = { new NameValuePair("os_username", username),
                new NameValuePair("os_password", password) };
        postMethod.setRequestBody(data);

        try {
            int rs = httpClient.executeMethod(postMethod);
            log.info(rs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // （5）读取response头信息



        Header headerResponse = postMethod.getResponseHeader("Set-Cookie");
        String headerStr = headerResponse.getValue();
        log.debug(headerStr);

        headers.add(new Header("Cookie",headerStr));
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        // 创建一个Get方法实例对象
        GetMethod getMethod = new GetMethod(queryurl);
        // 设置get请求超时为60000毫秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        try {
            int statusCode = httpClient.executeMethod(getMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }



        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        // 通过getMethod实例，获取远程的一个输入流
        is = getMethod.getResponseBodyAsStream();
        // 包装输入流
        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        StringBuffer sbf = new StringBuffer();
        // 读取封装的输入流
        String temp = null;
        while ((temp = br.readLine()) != null) {
            sbf.append(temp).append("\r\n");
        }

        result = sbf.toString();
        log.debug(result);

        XMLSerializer xmlSerializer = new XMLSerializer();

        JSON json = xmlSerializer.read(result);
        JsonParser parse =new JsonParser();
        JsonObject j1=(JsonObject) parse.parse(json.toString());
        int pnum = j1.get("channel").getAsJsonObject().get("item").getAsJsonArray().size();
        jsonJira[] jirasdata = new jsonJira[pnum];
        for (Integer i=0;i<pnum;i++){
            String id,name,type;
            Integer day;
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z", Locale.ENGLISH);
            SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd");
            Date d = sdf.parse((j1.get("channel").getAsJsonObject().get("item").getAsJsonArray().get(i).getAsJsonObject().get("created")+"").replace("\"",""));
            sdf1.format(d);
            id =(j1.get("channel").getAsJsonObject().get("item").getAsJsonArray().get(i).getAsJsonObject().get("key").getAsJsonObject().get("#text")+"").replace("\"","");
            name =(j1.get("channel").getAsJsonObject().get("item").getAsJsonArray().get(i).getAsJsonObject().get("summary")+"").replace("\"","");
            type =(j1.get("channel").getAsJsonObject().get("item").getAsJsonArray().get(i).getAsJsonObject().get("type").getAsJsonObject().get("#text")+"").replace("\"","");
            day =(int)((new Date().getTime() - d.getTime())/(1000*60*60*24));
            jirasdata[i] = new jsonJira(id,name,day,type);
        }

        jsonJira[] jj = {new jsonJira("1",j1.get("channel").getAsJsonObject().get("item").getAsJsonArray().get(0).getAsJsonObject().get("key")+"",1,"dfd")};
        return jirasdata;


    }


}

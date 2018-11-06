package com.space.requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Requests {
    public static String Api = "http://localhost:8081/report";

    public void request(String URL,String REPORT,Long WhitePageTime,Long FirstPageTime) throws IOException {
        String ReprotPath = REPORT + "/report_" + timeDate() + ".html";
        RestAssured.useRelaxedHTTPSValidation();
        HashMap paramsMap = new HashMap();
        paramsMap.put("URL",URL);
        paramsMap.put("WhitePageTime",WhitePageTime);
        paramsMap.put("FirstPageTime",FirstPageTime);
        Response response = (Response) given().params(paramsMap).when().get(Api).then().statusCode(200).
                extract();
        String result = response.body().asString();
        System.out.println("报告地址:" + ReprotPath);
        writeReport(ReprotPath,result);
    }

    /**
     * 写入报告
     */
    public void writeReport(String filePath,String content) throws IOException {
    try {
        FileWriter fw = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();
        fw.close();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }

    /**
     * 获取时间戳
     * @return
     */
    public static String timeDate(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestr = dateFormat.format(now);
        return timestr;

    }




}

package test_case;

import basic.BasicFun;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.LinkedList;

public class Case1 {
    public static Log log = LogFactory.getLog(Case1.class);

    @BeforeMethod
    public void before_methd() throws InterruptedException {
        log.info("BeforeMethod，测试用例之间等待5秒");
        Thread.sleep(5 * 1000);
    }

    @BeforeClass
    public void before_class(ITestContext context){
        log.info("BeforeClass");
        //从数据库中获取数据
        LinkedList linkedList = BasicFun.getCityNameFromMysql("cityname","name");
        Integer i;
        for( i=0;i<linkedList.size();i++){
            context.setAttribute(i.toString(),linkedList.get(i));
//            log.info("i.toString():"+i.toString());
//            log.info("linkedList.get(i):"+linkedList.get(i));
        }
    }

    @AfterClass
    public void afterclass(ITestContext context){
        log.info("AfterClass");
    }




    @Test(description="输入正确城市名称，获取城市天气" )
    //一、输入正确城市名称，获取城市天气
    public void case1_1(ITestContext context) throws IOException {
        log.info("case1_1，输入正确城市名称，获取城市天气");
        String cityname = (String) context.getAttribute("0");       //成都
        BasicFun basicFun = new BasicFun();
        String msg =basicFun.getWeatherApi(cityname);
        Assert.assertEquals(msg,"成功!");
    }


    @Test(description="输入错误城市名称，获取城市天气" )
    //二、输入错误城市名称，获取城市天气
    public void case1_2(ITestContext context) throws IOException {
        log.info("case1_2，输入错误城市名称，获取城市天气");
        String cityname = (String) context.getAttribute("5");   //成成都
        BasicFun basicFun = new BasicFun();
        String msg =basicFun.getWeatherApi(cityname);
        Assert.assertEquals(msg,"未获取到相关数据!");
    }

    @Test(description="输入错误城市名称，获取城市天气" )
    //三、输入错误城市名称，获取城市天气
    public void case1_3(ITestContext context) throws IOException {
        log.info("case1_3，输入错误城市名称，获取城市天气");
        String cityname = (String) context.getAttribute("7");   //beijing
        BasicFun basicFun = new BasicFun();
        String msg =basicFun.getWeatherApi(cityname);
        Assert.assertEquals(msg,"未获取到相关数据!");
    }







}

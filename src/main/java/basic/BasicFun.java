package basic;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.LinkedList;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class BasicFun {
    public static Log log = LogFactory.getLog(BasicFun.class);
    private static ResourceBundle bundle;

    //声明Connection对象
    public static Connection connection;
    //驱动名称
    public static String driver = "com.mysql.jdbc.Driver";
    //声明数据库连接、账号、密码
    static String url = "jdbc:mysql://localhost:3306/autotest?useSSL=false";
    //static String  url = "jdbc:mysql://l72.17.0.4:3306/autotest?useSSL=false";
    static String user = "root";
    static String password = "123456";


    /*
     * 获取数据库数据
     *
     *
     *
     * */
    public static LinkedList<String>  getCityNameFromMysql(String tablename,String name) {
        String sql;
        LinkedList<String> linkedList = new LinkedList<>();
        try {
            Class.forName(driver);
            //1、连接数据库
            connection = DriverManager.getConnection(url, user, password);
            //2、定义SQL语句
            sql = " select " +name+ " from " + tablename +  ";";
            log.info("sql语句是："+sql);
            //3、执行语句并获取返回
            PreparedStatement result = connection.prepareStatement(sql);
            ResultSet resultSet = result.executeQuery();
            while (resultSet.next()){
                //log.info("resultSet.getString(2):"+resultSet.getString(1));
                linkedList.add(resultSet.getString(1));
            }
            //log.info("linkedList:"+linkedList);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  linkedList;
    }












    /**
     * 输入城市，获取该城市的天气信息
     * parm:String cityname
     * return: String msg
     * 0717gst
     */
    public String getWeatherApi(String cityName) throws IOException {
        log.info("获取天气接口开始");
        String result=null;
        String msg =null;

        //一、获取URL
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        String url = bundle.getString("getWeatherApi");

        //二、发送请求，获取返回
        HttpGet get = new HttpGet(url + cityName);

        //1、header数据
        get.addHeader("Cache-Control", "max-age=0");
        get.addHeader("Accept-Language", "zh-CN,zh;q=0.9");

        //2、发送请求获取返回
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity());
        log.info("获取天气接口，获取的响应是：" + result);

        //3、抽取数据
        JSONObject resultjson = JSONObject.parseObject(result);
        log.info("获取天气接口，获取的resultjson是：" + resultjson);
        if (resultjson.get("code").equals(200)) {
             msg = (String) resultjson.get("msg");
            JSONObject responseData = (JSONObject) resultjson.get("data");
            log.info("获取天气接口，获取的responseData是：" + responseData);
        } else if (resultjson.get("code").equals(201)) {
             msg = (String) resultjson.get("msg");
        }
        return msg;
    }
}
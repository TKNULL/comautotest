package basic;


import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



//https://blog.csdn.net/u011541946/article/details/80413667
public class basic_fun {
    //get请求
    public CloseableHttpResponse get (String url) throws ClientProtocolException, IOException {
        //创建一个可关闭的HttpClient对象(相当于打开一个浏览器，url地址栏为空）
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个http get请求对象
        HttpGet httpGet = new HttpGet(url);
        //执行请求（相当于在浏览器地址栏输URL后，回车发送请求）
        CloseableHttpResponse response= httpClient.execute(httpGet);
        return response;
    }

    //带请求头的get请求
    public CloseableHttpResponse get(String url, HashMap<String,String> headermap) throws ClientProtocolException,IOException{
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpGet对象
        HttpGet httpget = new HttpGet(url);
        //加载请求头到HttpGet对象
        for (Map.Entry<String ,String> entry:headermap.entrySet()){
            httpget.addHeader(entry.getKey(),entry.getValue());
        }
        //执行请求，相当于点击发送
        CloseableHttpResponse httpResponse = httpClient.execute(httpget);
        return httpResponse;
    }

    //post方法
    public CloseableHttpResponse post(String url,String entityString, HashMap<String ,String> hashMap) throws ClientProtocolException,IOException{
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpPost的请求对象
        HttpPost httpPost = new HttpPost(url);
        //设置请求data???
        httpPost.setEntity(new StringEntity(entityString));

        //加载请求头到httppost对象
        for (Map.Entry<String ,String > entry:hashMap.entrySet()){
            httpPost.addHeader(entry.getKey(),entry.getValue());
        }

        //发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;

    }






}

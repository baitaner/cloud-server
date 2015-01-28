package rest;

import com.baitaner.common.constant.ConstConfig;
import com.baitaner.common.domain.result.Result;
import com.baitaner.common.utils.MD5Util;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zliu
 * Date: 14-2-23
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
 
public class TestPanelRest {
	private Map<String,String> headers = new HashMap<String,String>();
    Map<String,Object> payload = new HashMap<String,Object>();
    Map<String,Object> data1 = new HashMap<String,Object>();

    
    
    @Before
	public void init(){
    	/*
        sys_packege_type:panel.request.urls,
        sys_client_os_version:””,//客户端系统版本
		sys_client_version:"",//客户端软件版本
		sys_client_type:"app?|panel ",
        sys_client_id:””,//唯一标示客户端的，panel:panel_sn,app:device_id
		sys_client_firmware:"",//panel端有
		sys_user_id:””,//可选

         */
		headers.put("SECRET_KEY","panel.share.change");
        data1.put("headers", headers);
        
	}
    public static int versionCompare(String versionA,String versionB){
        String[] versionAs = versionA.split("\\.");
        String[] versionBs = versionB.split("\\.");
        if(versionAs.length<2 || versionBs.length<2){
            System.out.print("adfadf");
        }
        int len = versionAs.length>versionBs.length?versionBs.length:versionAs.length;
        int result = 0;
        for(int i=0;i<len;i++){
            result = versionAs[i].compareTo(versionBs[i]);
            if(result==0){
                continue;
            }else{
                break;
            }
        }
        return result;
    }
    @Test
    public void deviceAdd(){
        //00:22:f4:99:06:13
        System.out.println(409/10f);

    }
    @Test
    public void deviceActivate() throws Exception{

        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(1000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();

        map.setContentType(MediaType.parseMediaType("application/json"));
        map.set("SECRET_KEY", "secret");

        Map<String,String> deviceSn = new HashMap<String, String>();
        deviceSn.put("deviceSn","00:22:f4:99:05:fe");

        String params = "{\"deviceSn\":\"00:22:f4:99:05:fe\"}";

        HttpEntity httpEntity = new HttpEntity(params,map);

        Result result = rest.postForObject("http://54.191.138.9:8080/m/device/activate",httpEntity, Result.class);
        System.out.println("result = "+result);
    }
    @Test
    public void deviceHeartbeat() throws Exception{

        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(1000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();
        map.set("Connection","keep-alive");
    }

    @Test
    public void pushDataToCloud(){
        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(1000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();
        map.setContentType(MediaType.parseMediaType("application/json"));

        map.set("SECRET_KEY","p1eEtdeMJhEvmG1O");


        String params = "{\"panelId\":\"1413942727107\"}";
        HttpEntity httpEntity = new HttpEntity(params,map);
        Result res = rest.postForObject("http://54.191.138.9:8080/user/device/remove/1413969751440",httpEntity, Result.class);
        System.out.println("result = "+res);
    }
    @Test
    public void userBind(){
        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(1000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();
        map.setContentType(MediaType.parseMediaType("application/json"));

        map.set("SECRET_KEY","lpMT07fCPdHjMoKC");

        String params = "{\"registerCode\":\"583904\"}";
        HttpEntity httpEntity = new HttpEntity(params,map);

        Result res = rest.postForObject("http://54.191.138.9:8080/user/bind/1413969751440",httpEntity, Result.class);
        System.out.println("result = "+res);
    }
    @Test
    public void userGetDevice(){
        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(1000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();
        map.setContentType(MediaType.parseMediaType("application/json"));

        map.set("SECRET_KEY","CjmIanfdFY8paP7e");

        String params = "{\"panelId\":\"1413942727107\"}";
        HttpEntity httpEntity = new HttpEntity(params,map);

        Result res = rest.postForObject("http://54.191.138.9:8080/user/device/get/1413969751440",httpEntity, Result.class);
        System.out.println("result = "+res);
    }
    @Test
    public void userCtrl() throws Exception{
        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(1000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();
        map.setContentType(MediaType.parseMediaType("application/json"));

        String params = "{\"code\":301,\"payload\":{\"feedId\":1413942837861,\"accessKey\":\"lpMT07fCPdHjMoKC\"}}";
        HttpEntity httpEntity = new HttpEntity(params,map);
//        String params ="username=test@gmail.com&password=202cb962ac59075b964b07152d234b70";
        Result res = rest.postForObject("http://54.191.138.9:8080/user/sync/1413969751440",httpEntity, Result.class);
        System.out.println("result = "+res);
    }

    @Test
    public void userLogin() throws Exception{

        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(1000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();
        Map<String,String> userMap = new HashMap<String, String>();
        userMap.put("userName","kings36503@qq.com");
        userMap.put("password", MD5Util.md5Hash("123456"));
//        String params ="username=test@gmail.com&password=202cb962ac59075b964b07152d234b70";
        HttpEntity httpEntity = new HttpEntity(userMap,map);
        Result res = rest.postForObject("http://54.191.138.9:8080/m/user/login",httpEntity, Result.class);
        System.out.println("result = "+res);
    }

    @Test
    public void userLoginLocal() throws Exception{

        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(1000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();
        Map<String,String> userMap = new HashMap<String, String>();
        userMap.put("userName","liuzhen1984@gmail.com");
        userMap.put("password", "e10adc3949ba59abbe56e057f20f883e");
//        String params ="username=test@gmail.com&password=202cb962ac59075b964b07152d234b70";
        HttpEntity httpEntity = new HttpEntity(userMap,map);
        Result res = rest.postForObject("http://10.1.1.191:8080/m/user/login",httpEntity, Result.class);
        System.out.println("result = "+res);
    }

    @Test
    public void removePanelLocal(){
        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(1000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();
        map.setContentType(MediaType.parseMediaType("application/json"));

        map.set("SECRET_KEY","UeMAVCoUvKNIx02x");


        String params = "{\"panelId\":\"1411804134361\"}";
        HttpEntity httpEntity = new HttpEntity(params,map);
        Result res = rest.postForObject("http://10.1.1.191:8080/user/device/remove/1411800605223",httpEntity, Result.class);
        System.out.println("result = "+res);
    }
    @Test
    public void userGetDeviceLocal(){
        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(1000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();
        map.setContentType(MediaType.parseMediaType("application/json"));

        map.set("SECRET_KEY","KERMzJri3hjPAQYr");

        String params = "{\"panelId\":\"1411800927505\"}";
        HttpEntity httpEntity = new HttpEntity(params,map);

        Result res = rest.postForObject("http://10.1.1.191:8080/user/device/get/1411800605223",httpEntity, Result.class);
        System.out.println("result = "+res);
    }

    @Test
    public void testWeather(){
        SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
        schr.setConnectTimeout(5000);
        RestTemplate rest = new RestTemplate(schr);
        HttpHeaders map = new HttpHeaders();
        map.setContentType(MediaType.parseMediaType("application/json"));

        map.set("SECRET_KEY","KERMzJri3hjPAQYr");

        String params = "{\"zipCode\":\"95138\"}";
        HttpEntity httpEntity = new HttpEntity(params,map);

        Result res = rest.postForObject("http://10.1.1.193:8080/device/weather/sync/1411800605223",httpEntity, Result.class);
        System.out.println("result = "+res);
    }
}

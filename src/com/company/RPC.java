package com.company;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class RPC {
    public static JSONObject doPost(String url,JSONObject json){
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
    public static void addrpc(String Uri,int i) {
        String ID= String.valueOf(i);
        String uri =("[[\""+Uri+"\"]]");
        JSONObject obj = new JSONObject();
        obj.put("jsonrpc", "2.0");
        obj.put("id", ID);
        obj.put("method", "aria2.addUri");
        obj.put("params", uri);
        System.out.println(obj);
        JSONObject w=doPost("http://127.0.0.1:6800/jsonrpc",obj);
        System.out.println(w);
    }
    public static void tellrpc(int i) {
        String ID= String.valueOf(i);
        JSONObject obj = new JSONObject();
        obj.put("jsonrpc", "2.0");
        obj.put("id", ID);
        obj.put("method", "aria2.tellActive");
        System.out.println(obj);
        JSONObject w=doPost("http://127.0.0.1:6800/jsonrpc",obj);
        System.out.println(w);
    }
}


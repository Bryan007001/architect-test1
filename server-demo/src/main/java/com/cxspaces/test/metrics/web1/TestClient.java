package com.cxspaces.test.metrics.web1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class TestClient {

	public static void main(String[] args) {
        try {
            long t0 = new Date().getTime();
            for (int i = 0; i < 10000; i ++) {
                RestUtil restUtil = new RestUtil();
	            String resultString = restUtil.load("http://localhost:8080/greeting");
	            System.out.print(resultString);
            }
            long t1 = new Date().getTime();
            System.out.println("\nTime elapsed:" + (t1-t0));
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
	}
}

class RestUtil {

    public String load(String url) throws Exception {
    	URL restURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.setAllowUserInteraction(false);
        
        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line,resultStr="";
        while(null != (line=bReader.readLine())) {
        	resultStr += line;
        }
        bReader.close();
        return resultStr;
    }
}
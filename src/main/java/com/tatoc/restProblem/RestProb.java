package com.tatoc.restProblem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.tatoc.client.RestClientGetImp;
import com.tatoc.util.TestUtil;

public class RestProb 
{
    public static void main( String[] args ) throws ClientProtocolException, IOException
    {
        WebDriver driver = new ChromeDriver();
        driver.get("http://10.0.1.86/tatoc/advanced/rest/");
		String session = driver.findElement(By.cssSelector("#session_id")).getText();
		String session_ID = session.split(": ")[1];
		System.out.println("Session id = " + session_ID);
		String getUrl = "http://10.0.1.86/tatoc/advanced/rest/service/token/" + session_ID;
		System.out.println(getUrl);
		JSONObject jsonObj = RestClientGetImp.get(getUrl);
		String token = TestUtil.getValueByJPath(jsonObj, "token");
		System.out.println("Token --> " + token);
		
		String postUrl = "http://10.0.1.86/tatoc/advanced/rest/service/register";
		List<NameValuePair> paramNameValuePair = new ArrayList<NameValuePair>();
		paramNameValuePair.add(new BasicNameValuePair("id", session_ID));
		paramNameValuePair.add(new BasicNameValuePair("signature", token));
		paramNameValuePair.add(new BasicNameValuePair("allow_access", "1"));
		CloseableHttpResponse response = RestClientGetImp.post(postUrl, paramNameValuePair);
		System.out.println(" Status Code =  " + response.getStatusLine());
		driver.quit();
    }
}

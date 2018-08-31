package com.tatoc.restProblem;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
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
		String postString = "id=" + session_ID + "& signature=" + token + "&allow_access=1";;
		CloseableHttpResponse response = RestClientGetImp.post(postUrl, postString);
		System.out.println(" Status Code =  " + response.getStatusLine());
    }
}

package com.sinoglobal.app.util.http;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.sinoglobal.app.util.FileLocalCache;
import com.sinoglobal.app.util.LogUtil;
import com.sinoglobal.app.util.TextUtil;
import com.sinoglobal.app.util.exception.NoDataException;



/** 
 * @author ty
 * @createdate 2012-9-19 上午10:40:06
 * @Description: Http请求
 */ 
public class ConnectionUtil {
	//	private static final String URL = "http://scly.sinosns.cn/app/send.php";   //四川旅游接口正式
	//	private static final String URL = "http://www.taiim.com/airport/";   //一期php接口
//	public static final String DOWNLOAD_URL = "http://202.175.83.22:8095/RequestService?actionType=download&file=";//下载文件接口
	public static final String DOWNLOAD_URL = "http://112.91.147.101:8095:8095/RequestService?actionType=download&file=";//下载文件接口
//	public static final String URL = "http://112.91.147.101:88/airport/";   //二期php接口 
	public static final String URL = "http://www.macau-airport.com/airportapps/";   //二期php接口 


	private static final String ACCEPT="Accept-Charset";
	private static final String UTF8 = "UTF-8";
	private static final String UTF8_ES = "UTF-8,*";
	private static final String CONNECTION_JSON="json";
	private static final String APP_JSON="APPLICATION/JSON";
	//	private static final String DEPUTIZW_IP="10.0.0.172";
	//	private static final String HTTP_RESPONSE_NO_DATA = "can't get content from url";
	private static final String REQUEST_URL="请求：URL:-->:";
	private static final String RESPONSE_STATUS="响应状态码：";
	private static final String RESPONSE_PARAMS="---返回的参数为--->>：";
	private static final String TERMINAL_ID="terminalId";

	private static Date serverDate;  //服务器时间 
	public static Date getServerDate() {
		return serverDate;
	}

	/**
	 * 
	 * @author ty
	 * @createdate 2013-3-21 上午10:22:01
	 * @Description: post请求
	 * @param url 请求的URL
	 * @param params 请求的参数封装
	 * @return
	 * @throws Exception
	 *
	 */
	public static String post(Map<String, Object> params)throws Exception {
		LogUtil.e(REQUEST_URL+URL);
		HttpPost httpost = new HttpPost(URL);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(params!=null){
			for (String key : params.keySet()) {
				LogUtil.e("key:"+key+", value:"+params.get(key));
				nvps.add(new BasicNameValuePair(key, params.get(key)==null?null:params.get(key).toString()));
			}
			params.clear();
			params=null;
		}
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		return execute(httpost);
	}
	public static String post(String url,Map<String, Object> params)throws Exception {
		LogUtil.e(REQUEST_URL+url);
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(params!=null){
			for (String key : params.keySet()) {
				LogUtil.e("key:"+key+", value:"+params.get(key));
				nvps.add(new BasicNameValuePair(key, params.get(key)==null?null:params.get(key).toString()));
			}
			params.clear();
			params=null;
		}
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		return execute(httpost);
	}
	public static String post(Map<String, Object> params,String interfaceName)throws Exception {
		LogUtil.e(REQUEST_URL+URL);
		HttpPost httpost = new HttpPost(URL+interfaceName);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(params!=null){
			for (String key : params.keySet()) {
				LogUtil.e("key:"+key+", value:"+params.get(key));
				nvps.add(new BasicNameValuePair(key, params.get(key)==null?null:params.get(key).toString()));
			}
			params.clear();
			params=null;
		}
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		return execute(httpost);
	}
	public static InputStream get(String url)throws Exception {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = executeLoad(httpGet);
		return response.getEntity().getContent();
	}
	public static String get(Map<String, Object> params,String interfaceName)throws Exception {
		StringBuilder sb=new StringBuilder();
		sb.append(URL).append(interfaceName);
		if(params!=null){
			sb.append("?");
			if (params != null) {
				for (String key : params.keySet()) {
					LogUtil.e("key:" + key + ", value:" + params.get(key));
					sb.append(key)
					.append("=")
					.append(params.get(key))
					.append("&");
				}
				sb.deleteCharAt(sb.lastIndexOf("&"));
				LogUtil.e("请求",sb.toString());
				params.clear();
				params = null;
			}
		}
		HttpGet httpGet = new HttpGet(sb.toString());
		return execute(httpGet);
	}
	public static String get(String url,Map<String, Object> params)throws Exception {
		StringBuilder sb=new StringBuilder();
		sb.append(url);
		if(params!=null){
			sb.append("?");
			if (params != null) {
				for (String key : params.keySet()) {
					LogUtil.e("key:" + key + ", value:" + params.get(key));
					sb.append(key)
					.append("=")
					.append(params.get(key))
					.append("&");
				}
				sb.deleteCharAt(sb.lastIndexOf("&"));
				LogUtil.e("请求",sb.toString());
				params.clear();
				params = null;
			}
		}
		HttpGet httpGet = new HttpGet(sb.toString());
		return execute(httpGet);
	}
	public static String get(String url,Map<String, Object> params,boolean bCache)throws Exception {
		StringBuilder sb=new StringBuilder();
		sb.append(url);
		String fileName = FileLocalCache.getFileName(url, params);
		if(params!=null&&!params.isEmpty()){
			sb.append("?");
			for (String key : params.keySet()) {
				LogUtil.e("key:" + key + ", value:" + params.get(key));
				sb.append(key)
				.append("=")
				.append(params.get(key).toString().contains(" ")?URLEncoder.encode(params.get(key).toString(),"utf-8"):params.get(key))
				.append("&");
			}
			sb.deleteCharAt(sb.lastIndexOf("&"));
			LogUtil.e("请求",sb.toString());
			params.clear();
			params = null;
		}
		HttpGet httpGet = new HttpGet(sb.toString());
		return execute(httpGet,fileName);
	}



	private static String execute(HttpUriRequest req)throws Exception {
		long startTime = System.currentTimeMillis();

		String result = null;
		InputStream instream = null;
		try {
			HttpResponse response = executeLoad(req);
			if (response != null) {
				int statusCode = response.getStatusLine().getStatusCode();
				LogUtil.d(RESPONSE_STATUS+statusCode);
				switch (statusCode) {
				case HttpStatus.SC_OK:
					result = EntityUtils.toString(response.getEntity(), UTF8);
					LogUtil.e("result-->", result);
					break;
				case HttpStatus.SC_FORBIDDEN:  // 验证未通过
					break;
				case HttpStatus.SC_NOT_FOUND:  // 请求错误
					break;
				}
			}
		} finally {
			FileLocalCache.closeInputStream(instream);
		}

		//打包时应该注释
		long endTime = System.currentTimeMillis();
		String info="请求：Time:" + (endTime - startTime) + "-->:" + req.getURI();
		LogUtil.d(info);
		if(TextUtil.stringIsNull(result)){
			throw new NoDataException("request not data :"+req.getURI());
		}
		LogUtil.d(RESPONSE_PARAMS+result);
		//		FileLocalCache.saveFile(info,result);
		return result;
	}

	private static String execute(HttpUriRequest req,String fileName)throws Exception {
		long startTime = System.currentTimeMillis();

		String result = null;
		InputStream instream = null;
		try {
			HttpResponse response = executeLoad(req);
			if (response != null) {
				int statusCode = response.getStatusLine().getStatusCode();
				LogUtil.d(RESPONSE_STATUS+statusCode);
				switch (statusCode) {
				case HttpStatus.SC_OK:
					result = EntityUtils.toString(response.getEntity(), UTF8);
					LogUtil.e("result-->", result);
					break;
				case HttpStatus.SC_FORBIDDEN:  // 验证未通过
					break;
				case HttpStatus.SC_NOT_FOUND:  // 请求错误
					break;
				}
			}
		} finally {
			FileLocalCache.closeInputStream(instream);
		}

		//打包时应该注释
		long endTime = System.currentTimeMillis();
		String info="请求：Time:" + (endTime - startTime) + "-->:" + req.getURI();
		LogUtil.d(info);
		if(TextUtil.stringIsNull(result)){
			throw new NoDataException("request not data :"+req.getURI());
		}
		LogUtil.d(RESPONSE_PARAMS+result);
		FileLocalCache.saveFile(fileName,result);

		return result;
	}

	/**
	 * @author ty
	 * @createdate 2012-11-3 下午9:46:54
	 * @Description: 加载数据
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse executeLoad(HttpUriRequest req)throws Exception {
		HttpClient httpclient = CustomerHttpClient.getInstance();
		/*if (NetWorkUtil.ONLYWAP == true) {
			HttpHost proxy = new HttpHost(DEPUTIZW_IP, 80);
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
		}*/
		req.addHeader(ACCEPT, UTF8_ES);
		HttpResponse response = httpclient.execute(req);
		return response;
	}

	/**
	 * 取得响应的信息流
	 * @param response
	 * @param instream
	 * @return
	 * @throws Exception
	 */
	/*private static String parseResponse(HttpResponse response,InputStream instream) throws Exception {
		String result = null;
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			instream = entity.getContent();
			result = TextUtil.InputStreamToString(instream);
		}
		return result;
	}*/
}

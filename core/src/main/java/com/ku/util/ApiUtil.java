package com.ku.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class ApiUtil {

	public ApiUtil() {
	}

	public static String getRequest(String url) {
		URL obj;
		StringBuffer response = new StringBuffer();
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static String getFlipkartData(String url, Map<String, Object> params) {
		StringBuffer response = new StringBuffer();
		try {
			URL request = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) request
					.openConnection();
			connection.setRequestMethod("GET");
			Map<String, String> headers = (Map<String, String>)params.get("headers");
			for(String key : headers.keySet()) {
				connection.setRequestProperty(key, headers.get(key));
			}
			/*connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Fk-Affiliate-Token",
					"ef77d485ad7b418984aeea01d2d3eaa9");
			connection.setRequestProperty("Fk-Affiliate-Id", "adminmuni");*/

			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString();
	}

	public static String postRequest() {
		return "success";
	}
	
	public static void saveImageFromUrl(String imageUrl, String destinationFile) throws IOException {
		File file = new File(destinationFile);
		if(!file.exists()) {
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(destinationFile);
	
			byte[] b = new byte[2048];
			int length;
	
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
	
			is.close();
			os.close();
		}
	}

}

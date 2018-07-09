
package co.th.aten.network.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SMSGateway {

	private Charset CSET_UTF8 = Charset.forName("UTF-8");
	// register web https://secure.thaibulksms.com
	private String gwUser = "0858207768";//0858207768,0856614358
	private String gwPass = "659368";//659368,716309
	private String gwSender = "SMS";
	private String gwForce = "Standard";// Corporate, Standard

	public static void main(String arg0[]) {
		SMSGateway smsGateway = new SMSGateway();
		smsGateway.sendSMSGateway("0858207768", "ทดสอบระบบส่ง SMS ของ thaibulksms.com");
//		smsGateway.creditRemain();
//		String xml = "<SMS>"+
//				"<QUEUE>"+
//				"<Msisdn>0856614358</Msisdn>"+
//				"<Status>1</Status>"+
//				"<Transaction>b29ddfc38e604a498bc5a256d8b02d76</Transaction>"+
//				"<UsedCredit>1</UsedCredit>"+
//				"<RemainCredit>9</RemainCredit>"+
//				"</QUEUE>"+
//				"<!-- 0.010712146759033 -->"+
//				"</SMS>";
	}

	public boolean sendSMSGateway(String moblielist, String message) {
		boolean chk = false;
		try {
			System.out.println("SendSMS Gateway mobile : " + moblielist + " ,message:" + message);
			message = Util.getURLEncoder(message);

			//          https://secure.thaibulksms.com/sms_api.php?username=000000000&password=0000000&tag=credit_remain
			String serverUrl = "https://secure.thaibulksms.com/sms_api.php?";
			String ScheduledDelivery = "";//1108171455 YYMMDDHHMM
			String strUrl = "";

			strUrl = serverUrl
					+ "username=" + gwUser
					+ "&password=" + gwPass
					+ "&msisdn=" + moblielist
					+ "&message=" + message
					+ "&sender=" + gwSender
					+ "&ScheduledDelivery=" + ScheduledDelivery
					+ "&force=" + gwForce;


			Authenticator.setDefault(new MyAuthenticator());

			TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			}};

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());


			URL url = new URL(strUrl);
			System.out.println("smsUrl =" + strUrl);
			URLConnection con = url.openConnection();
			HttpsURLConnection httpc = (HttpsURLConnection) con;
			httpc.connect();
			StringWriter w = new StringWriter();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpc.getInputStream(), CSET_UTF8));
			String ss = "";
			while ((ss = reader.readLine()) != null) {
				w.append(ss);
				w.append("\n");
			}
			System.out.println("result =" + w.toString());
			if(w.toString().contains("<Status>1</Status>")){
				chk = true;
			}
			reader.close();
			w.close();
			httpc.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chk;
	}

	public int creditRemain() {

		try {

			System.out.println("check crefit remain.");
			String serverUrl = "https://secure.thaibulksms.com/sms_api.php?";
			String strUrl = "";
			strUrl = serverUrl
					+ "username=" + gwUser
					+ "&password=" + gwPass
					+ "&tag=credit_remain";

			Authenticator.setDefault(new MyAuthenticator());

			TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			}};

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			URL url = new URL(strUrl);
			URLConnection con = url.openConnection();
			HttpsURLConnection httpc = (HttpsURLConnection) con;
			httpc.connect();
			StringWriter w = new StringWriter();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpc.getInputStream(), CSET_UTF8));
			String ss = "";
			while ((ss = reader.readLine()) != null) {
				w.append(ss);
				w.append("\n");
			}
			System.out.println("result = " + w.toString());
			reader.close();
			w.close();
			httpc.disconnect();
			if (w.toString() != null && !w.toString().equals("")) {
				int creditRemain = new BigDecimal(w.toString().trim()).intValue();
				return creditRemain;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public class MyAuthenticator extends Authenticator {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			String username = gwUser;
			String password = gwPass;
			return new PasswordAuthentication(username, password.toCharArray());
		}
	}
}

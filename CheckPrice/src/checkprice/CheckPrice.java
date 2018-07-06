/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkprice;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import model.CoinModel;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author IdeaPad
 */
public class CheckPrice {

    private static final String lineApi = "https://notify-api.line.me/api/notify";
    private static final String cryptoBridgeTickerApi = "https://api.crypto-bridge.org/api/v1/ticker";
    private static final String graviexTickerApi = "https://graviex.net:443//api/v2/tickers.json";
//    private static String lineToken = "pMs2I1d7uDeNrD5Hl33uQK3LuPkKhYA9BhimCMm370x";// Test
    private static String lineToken = "NunHl4Fn8vbyHv3Yyz06StJHzg7iNNIDjnhH5c3tHBK";// Masternode
    private static DecimalFormat df = new DecimalFormat("#,##0.00");
    private static DecimalFormat df9 = new DecimalFormat("#,##0.000000000");
    private static DecimalFormat df8 = new DecimalFormat("#,##0.00000000");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("mm", Locale.US);
    private static List<CoinModel> listGraviex;
    private static List<CoinModel> listCryptoBridge;

    public static void main(String[] args) {

        loadConfigGraviex();
        loadConfigCryptoBridge();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("mm : " + sdf.format(new Date()));
                        if (sdf.format(new Date()).equals("00")) {
                            String message = "\n";
                            List<CoinModel> listPriceGraviex = priceGraviex();
                            for (CoinModel model : listPriceGraviex) {
                                message += model.getName() + "\n Buy : " + model.getBuy() + "\n Sell : " + model.getSell() + "\n";
                            }
                            List<CoinModel> listPriceCryptoBridge = priceCryptoBridge();
                            for (CoinModel model : listPriceCryptoBridge) {
                                message += model.getName() + "\n Buy : " + model.getBuy() + "\n Sell : " + model.getSell() + "\n";
                            }
                            callEvent(lineToken, message);
                        }
                        Thread.sleep(1000 * 60);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(run).start();
    }

    private static void loadConfigGraviex() {
        listGraviex = new ArrayList<CoinModel>();
        try {
            Document doc = null;
            Element root, temp, temp1;
            Iterator list, list1;
            String path = System.getProperty("user.dir");
            String fileName = "/listGraviex.xml";
            System.out.println("xml file:" + fileName);
            path = path + fileName;
            System.out.println("File Path:" + path);
            FileInputStream fileInputStream = new FileInputStream(path);

            try {
                doc = new SAXReader().read(fileInputStream);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            root = doc.getRootElement();
            list = root.elementIterator();

            while (list.hasNext()) {
                temp = ((Element) list.next());
                if (temp.getName().contains("monitor")) {
                    list1 = temp.elementIterator();
                    CoinModel coinModel = new CoinModel();
                    while (list1.hasNext()) {
                        temp1 = (Element) list1.next();
                        switch (temp1.getName()) {
                            case "name":
                                coinModel.setName(temp1.getText());
                                break;
                            case "key":
                                coinModel.setKey(temp1.getText());
                                break;
                        }
                    }
                    listGraviex.add(coinModel);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void loadConfigCryptoBridge() {
        listCryptoBridge = new ArrayList<CoinModel>();
        try {
            Document doc = null;
            Element root, temp, temp1;
            Iterator list, list1;
            String path = System.getProperty("user.dir");
            String fileName = "/listCryptoBridge.xml";
            System.out.println("xml file:" + fileName);
            path = path + fileName;
            System.out.println("File Path:" + path);
            FileInputStream fileInputStream = new FileInputStream(path);

            try {
                doc = new SAXReader().read(fileInputStream);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            root = doc.getRootElement();
            list = root.elementIterator();

            while (list.hasNext()) {
                temp = ((Element) list.next());
                if (temp.getName().contains("monitor")) {
                    list1 = temp.elementIterator();
                    CoinModel coinModel = new CoinModel();
                    while (list1.hasNext()) {
                        temp1 = (Element) list1.next();
                        switch (temp1.getName()) {
                            case "name":
                                coinModel.setName(temp1.getText());
                                break;
                            case "key":
                                coinModel.setKey(temp1.getText());
                                break;
                        }
                    }
                    listCryptoBridge.add(coinModel);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<CoinModel> priceGraviex() {
        List<CoinModel> list = new ArrayList<CoinModel>();
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs,
                            String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs,
                            String authType) {
                    }
                }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            URL url = new URL(graviexTickerApi);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.addRequestProperty("Accept-Language", "th,en-US;q=0.7,en;q=0.3");
            connection.addRequestProperty("Connection", "keep-alive");
            connection.addRequestProperty("Cookie", "XSRF-TOKEN=AbLt5dZOyRlPWqot3nBGz0fmBh%2F5Sy%2FzkOGRblJUMmo%3D; _peatio_session=5efb8487874d373d2c253b94ae9c9f11; lang=en");
            connection.addRequestProperty("Host", "graviex.net");
            connection.addRequestProperty("If-None-Match", "b7fcd10e801effd9b708c4ee625911e7");
            connection.addRequestProperty("Upgrade-Insecure-Requests", "1");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
            connection.setDoOutput(true);
            connection.connect();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                String responseMsg = connection.getResponseMessage();
                System.out.println(responseMsg);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
//                System.out.println(response.toString());
                JSONObject json = new JSONObject(response.toString());
                for (CoinModel coinModel : listGraviex) {
                    JSONObject head = json.getJSONObject(coinModel.getKey());
                    if (head != null) {
                        JSONObject ticker = head.getJSONObject("ticker");
                        String buy = ticker.getString("buy");
                        String sell = ticker.getString("sell");
                        String change = ticker.getString("change");
                        CoinModel model = new CoinModel();
                        buy = df9.format(Double.parseDouble(buy));
                        buy = (buy.substring(0, buy.length() - 1) + "'" + buy.substring(buy.length() - 1, buy.length()));
                        sell = df9.format(Double.parseDouble(sell));
                        sell = (sell.substring(0, sell.length() - 1) + "'" + sell.substring(sell.length() - 1, sell.length()));
                        model.setName(coinModel.getName());
                        model.setBuy(buy);
                        model.setSell(sell);
                        model.setChange(df.format(Double.parseDouble(change)));
                        list.add(model);
                    }
                }
            } else {
                throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<CoinModel> priceCryptoBridge() {
        List<CoinModel> list = new ArrayList<CoinModel>();
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs,
                            String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs,
                            String authType) {
                    }
                }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            URL url = new URL(cryptoBridgeTickerApi);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.connect();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                String responseMsg = connection.getResponseMessage();
                System.out.println(responseMsg);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String jsonData = "{\"dataArray\":" + response.toString() + "}";
//                System.out.println(jsonData);
                JSONObject json = new JSONObject(jsonData);
                JSONArray dataArray = json.getJSONArray("dataArray");
                if (dataArray != null) {
                    for (CoinModel coinModel : listCryptoBridge) {
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject data = dataArray.getJSONObject(i);
                            String id = data.getString("id");
                            if (id != null && id.equals(coinModel.getKey())) {
                                String bid = data.getString("bid");
                                String ask = data.getString("ask");
                                CoinModel model = new CoinModel();
                                model.setName(coinModel.getName());
                                model.setBuy(df8.format(Double.parseDouble(bid)));
                                model.setSell(df8.format(Double.parseDouble(ask)));
                                model.setChange(df.format(Double.parseDouble("0")));
                                list.add(model);
                                break;
                            }
                        }
                    }
                }
            } else {
                throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean callEvent(String token, String message) {
        boolean result = false;
        try {
            message = replaceProcess(message);
            message = URLEncoder.encode(message, "UTF-8");
            URL url = new URL(lineApi);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.addRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            String parameterString = "message=" + message;
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
            printWriter.print(parameterString);
            printWriter.close();
            connection.connect();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                result = true;
            } else {
                throw new Exception("Error:(StatusCode)" + statusCode + ", " + connection.getResponseMessage());
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String replaceProcess(String txt) {
        txt = replaceAllRegex(txt, "\\\\", "￥");		// \
        return txt;
    }

    private static String replaceAllRegex(String value, String regex, String replacement) {
        if (value == null || value.length() == 0 || regex == null || regex.length() == 0 || replacement == null) {
            return "";
        }
        return Pattern.compile(regex).matcher(value).replaceAll(replacement);
    }
}

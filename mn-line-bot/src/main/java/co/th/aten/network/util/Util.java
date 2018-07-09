package co.th.aten.network.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Util {

	public static String getURLEncoder(String code) throws UnsupportedEncodingException {
		return URLEncoder.encode(code, "UTF-8");
	}

	public static String DateToString(Date date) throws NullPointerException {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		return sdf.format(date);
	}

	public static Date convertStringtoDate(String dateString) throws ParseException,
	NullPointerException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s.S");
		return sdf.parse(dateString);
	}

	public static String DateTimeToString(Date date) throws NullPointerException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s.S");
		return sdf.format(date);
	}

	public static String DateTimeTodayString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s.S");
		return sdf.format(new Date());
	}

	public static String byteArrayToHexString(byte in[]) {

		byte ch = 0x00;

		int i = 0;

		if (in == null || in.length <= 0) {
			return null;
		}

		String pseudo[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
				"E", "F" };

		StringBuffer out = new StringBuffer(in.length * 2);

		while (i < in.length) {

			ch = (byte) (in[i] & 0xF0); // Strip off high nibble

			ch = (byte) (ch >>> 4);
			// shift the bits down

			ch = (byte) (ch & 0x0F);
			// must do this is high order bit is on!

			out.append(pseudo[ch]); // convert the nibble to a String
			// Character

			ch = (byte) (in[i] & 0x0F); // Strip off low nibble

			out.append(pseudo[ch]); // convert the nibble to a String
			// Character

			i++;

		}

		String rslt = new String(out);

		return rslt;

	}

	public static String getNodeValue(String NodeName, Document doc) {
		NodeList nodelist = doc.getElementsByTagName(NodeName);
		if (nodelist.item(0).getNodeName().equals(NodeName)) {
			return nodelist.item(0).getTextContent();
		} else {
			return "";
		}
	}

	public static Element getElementByTagName(Element node, String name) {
		NodeList list = node.getElementsByTagName(name);
		if (list.getLength() == 1) {
			return (Element) list.item(0);
		}
		return null;
	}

	public static Date resetMillisecond(Date master) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(master);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date resetTime(Date master) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(master);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static String DateToStringSecKey( Date date ) throws NullPointerException {
		// SimpleDateFormat sdf = new SimpleDateFormat("dMyyHms", Locale.US);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
		return sdf.format(date);
	}

	public static double excludeVAT(double amount ,double vatPercent){
		double afterVat = amount - ((double)(amount * vatPercent)/(double)(100 + vatPercent));
		return afterVat;
	}

	public static double calVAT(double amount ,double vatPercent){
		double vat = (double)(amount * vatPercent)/(double)(100);
		return vat;	
	}

	public static double calVAT(double amount ,float vatPercent){
		double vat = (double)(amount * vatPercent)/(double)(100);
		return vat;	
	}

	//    public static double calVAT(double amount ,int vatPercent){
	//    	double vat = (double)(amount * vatPercent)/(double)(100 + vatPercent);
	//    	return vat;	
	//    }
	//    
	//    public static double calVAT(double amount ,float vatPercent){
	//    	double vat = (double)(amount * vatPercent)/(double)(100 + vatPercent);
	//    	return vat;	
	//    }


	public static double nullSafeParseDouble( String s ) {

		try {
			if (s != null && s.length() > 0) {
				return Double.parseDouble(s);
			}
		} catch (NullPointerException e) {
			// System.out.println("parse double error null");
		}
		return 0;
	}

	public static Long nullSafeParseLong( String s ) {
		try {
			if (s != null && s.length() > 0) {
				return Long.parseLong(s);
			}
		} catch (NullPointerException e) {
			// System.out.println("parse long error null");
		}
		return null;
	}

	public static Short nullSafeParseShort( String s ) {
		try {
			if (s != null && s.length() > 0) {
				return Short.parseShort(s);
			}
		} catch (NullPointerException e) {
			// System.out.println("parse short error null");
		}
		return null;
	}

	public static Short nullSafeParseShort( String s, int radix ) {
		try {
			if (s != null && s.length() > 0) {
				return Short.parseShort(s, radix);
			}
		} catch (NullPointerException e) {
			// System.out.println("parse short error null");
		}
		return null;
	}

	public static Integer nullSafeParseInt( String s ) {
		try {
			if (s != null && s.length() > 0) {
				return Integer.parseInt(s);
			}
		} catch (NullPointerException e) {
			// System.out.println("parse int error null");
		}
		return null;
	}

	public static Integer nullSafeParseInt( String s, int radix ) {
		try {
			if (s != null && s.length() > 0) {
				return Integer.parseInt(s, radix);
			}
		} catch (NullPointerException e) {
			// System.out.println("parse int error null");
		}
		return null;
	}

	public static Float nullSafeParseFloat( String s ) {
		try {
			if (s != null && s.length() > 0) {
				return Float.parseFloat(s);
			}
		} catch (NullPointerException e) {
			// System.out.println("parse float error");
		}
		return null;
	}

	public static Date nullSafeParseDate( String s, String format, Locale locale ) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		return nullSafeParseDate(s, sdf);
	}

	public static Date nullSafeParseDate( String s, String format ) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		return nullSafeParseDate(s, sdf);
	}

	public static Date nullSafeParseDate( String s, SimpleDateFormat sdf ) throws ParseException {
		try {
			if (s != null && s.trim().length() > 0) {
				return sdf.parse(s);
			}
		} catch (NullPointerException e) {
			System.out.println("parse date error null");
		}
		return null;
	}

	public static String nullToBlank( String str ) {
		return (str == null ? "" : str);
	}

	public static String nullSafeDate( Date date, String format ) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		return (date == null ? "" : sdf.format(date));
	}

	public static int nullSafeInt( Integer integer ) {
		return (integer == null ? 0 : integer);
	}

	public static long nullSafeLong( Long _long ) {
		return (_long == null ? 0 : _long);
	}

	public static short nullSafeShort( Short sh ) {
		return (sh == null ? 0 : sh);
	}

	public static double nullSafeDouble( Double db ) {
		return (db == null ? 0.0 : db);
	}

	public static double nullSafeBigDec( BigDecimal bd ) {
		return (bd == null ? 0.0 : bd.doubleValue());
	}

	public static String nullSafeDoubleToStr( Double db ) {
		return (db == null ? "0" : db.toString());
	}

	public static String nullSafeIntToStr( Integer integer ) {
		return (integer == null ? "0" : integer.toString());
	}

	public static String nullSafeLongToStr( Long _long ) {
		return (_long == null ? "0" : _long.toString());
	}

	public static String nullSafeShortToStr( Short _short ) {
		return (_short == null ? "0" : _short.toString());
	}    
}

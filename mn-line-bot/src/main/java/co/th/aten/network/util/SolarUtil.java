package co.th.aten.network.util;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SolarUtil {
	private static String OS = System.getProperty("os.name").toLowerCase();
	// private static final SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	// private static final SimpleDateFormat sdfHour = new SimpleDateFormat("HHmm", Locale.US);
	// private static final SimpleDateFormat sdfNewsDate = new SimpleDateFormat("dd/MM/yyyyHHmm", Locale.US);

	public static String getHourUtcStr() {
		SimpleDateFormat sdfHourUtc = new SimpleDateFormat("HHmm", Locale.US);
		sdfHourUtc.setTimeZone(TimeZone.getTimeZone("GMT0"));
		return sdfHourUtc.format(Calendar.getInstance().getTime());
	}

	public static String getDateUtcStr() {
		SimpleDateFormat sdfDateUtc = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		sdfDateUtc.setTimeZone(TimeZone.getTimeZone("GMT0"));
		return sdfDateUtc.format(Calendar.getInstance().getTime());
	}

	public static String getDatetimeUtcStr(Date d) {
		SimpleDateFormat sdfDatetimeUtc = new SimpleDateFormat("dd/MM/yyyy HHmmss", Locale.US);
		sdfDatetimeUtc.setTimeZone(TimeZone.getTimeZone("GMT0"));
		return sdfDatetimeUtc.format(d);
	}

	public static String getDatetimeStr(Date d) {
		SimpleDateFormat sdfDatetime = new SimpleDateFormat("dd/MM/yyyy HHmmss", Locale.US);
		return sdfDatetime.format(d);
	}

	public static String getDatetimeShowStr(Date d) {
		SimpleDateFormat sdfDatetime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
		return sdfDatetime.format(d);
	}

	public static String getDateStr(Date d) {
		SimpleDateFormat sdfDatetime = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		return sdfDatetime.format(d);
	}

	public static String getHourStr(Date d) {
		SimpleDateFormat sdfHour = new SimpleDateFormat("HHmm", Locale.US);
		return sdfHour.format(d);
	}

	public static String getHour24Str() {
		SimpleDateFormat sdfHour = new SimpleDateFormat("H", Locale.US);

		return sdfHour.format(Calendar.getInstance().getTime());
	}

	public static Date getDate() {
		SimpleDateFormat sdfDatetime = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		try {
			return sdfDatetime.parse(getDatetimeStr(Calendar.getInstance().getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getDate(Date d) {
		SimpleDateFormat sdfDatetime = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		try {
			return sdfDatetime.parse(getDatetimeStr(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getDatetime() {
		SimpleDateFormat sdfDatetime = new SimpleDateFormat("dd/MM/yyyy HHmmss", Locale.US);
		try {
			return sdfDatetime.parse(getDatetimeStr(Calendar.getInstance().getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getDatetime(Date date, String HHmm) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		SimpleDateFormat sdfDatetime = new SimpleDateFormat("dd/MM/yyyy HHmmss", Locale.US);
		try {
			return sdfDatetime.parse(sdfDate.format(date) + " " + HHmm + "00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getDatetime(String dateStr, String HHmm) {
		SimpleDateFormat sdfDatetime = new SimpleDateFormat("dd/MM/yyyy HHmmss", Locale.US);
		try {
			return sdfDatetime.parse(dateStr + " " + HHmm + "00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getDateUTC() {
		return getDateUTC(Calendar.getInstance().getTime());
	}

	public static Date getDateUTC(Date d) {
		SimpleDateFormat sdfDatetimeUtc = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		sdfDatetimeUtc.setTimeZone(TimeZone.getTimeZone("GMT0"));
		try {
			return sdfDatetimeUtc.parse(getDatetimeUtcStr(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getDatetimeUTC() {
		return getDateUTC(Calendar.getInstance().getTime());
	}

	public static Date getDatetimeUTC(Date d) {
		SimpleDateFormat sdfDatetimeUtc = new SimpleDateFormat("dd/MM/yyyy HHmmss", Locale.US);
		sdfDatetimeUtc.setTimeZone(TimeZone.getTimeZone("GMT0"));
		try {
			return sdfDatetimeUtc.parse(getDatetimeUtcStr(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int findNumeric(String str) {
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				if (Character.isDigit(str.charAt(i)))
					return i;
			}
		}

		return -1;
	}

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static String getExtension(File file) {
		String fileName = file.getName();
		return getExtension(fileName);
	}

	public static String getExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	public static String getFileName(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos > 0) {
			return fileName = fileName.substring(0, pos);
		} else {
			return "";
		}
	}

	public static String getSplitSrting(String Name, Short index) {
		String[] parts = null;
		String ans = "";
		try {
			parts = Name.split("-");
			ans = parts[index];
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ans;
	}

	public static String getCutLeadingZero(String str) {
		if (str != null) {
			return str.replaceFirst("^0+(?!$)", "");
		}
		return null;
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static boolean getBoolean(Object ob) {
		if (ob != null) {
			if (isShort(ob.toString())) {
				return Short.parseShort(ob.toString()) == 1;
			}
		}
		return false;
	}
	
	public static String getString(Object ob) {
		if (ob != null) {
			return ob.toString();
		}
		return null;
	}

	public static String getStringEmpty(Object ob) {
		if (ob != null) {
			return ob.toString();
		}
		return "";
	}

	public static Date getDate(Object ob) {
		if (ob != null) {
			try {
				Date dt = (Date) ob;
				return dt;
			} catch (Exception e) {
				
			}
		}
		return null;
	}

	public static short getshort(Object ob) {
		if (ob != null) {
			if (isShort(ob.toString())) {
				return Short.parseShort(ob.toString());
			}
		}
		return (short) 0;
	}

	public static Short getShort(Object ob) {
		if (ob != null) {
			if (isShort(ob.toString())) {
				return Short.parseShort(ob.toString());
			}
		}
		return null;
	}

	public static Integer getint(Object ob) {
		if (ob != null) {
			if (isInteger(ob.toString())) {
				return Integer.parseInt(ob.toString());
			}
		}
		return 0;
	}
	
	public static Integer getInteger(Object ob) {
		if (ob != null) {
			if (isInteger(ob.toString())) {
				return Integer.parseInt(ob.toString());
			}
		}
		return null;
	}

	public static Long getLong(Object ob) {
		if (ob != null) {
			if (isLong(ob.toString())) {
				return Long.parseLong(ob.toString());
			}
		}
		return null;
	}

	public static long getlong(Object ob) {
		if (ob != null) {
			if (isLong(ob.toString())) {
				return Long.parseLong(ob.toString());
			}
		}
		return (long) 0;
	}

	public static double getdouble(Object ob) {
		if (ob != null) {
			if (isDouble(ob.toString())) {
				return Double.parseDouble(ob.toString());
			}
		}
		return 0;
	}
	
	public static Double getDouble(Object ob) {
		if (ob != null) {
			if (isDouble(ob.toString())) {
				return Double.parseDouble(ob.toString());
			}
		}
		return null;
	}

	public static Boolean isShort(String str) {
		try {
			Short.parseShort(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Boolean isLong(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String NumberFormat0(Long value) {
		DecimalFormat df = new DecimalFormat("#,##0");
		if (value != null) {
			return df.format(value);
		}
		return df.format(0);
	}

	public static String NumberFormat0(Double value) {
		DecimalFormat df = new DecimalFormat("#,##0");
		if (value != null) {
			return df.format(value);
		}
		return df.format(0);
	}

	public static String NumberFormat0(Integer value) {
		DecimalFormat df = new DecimalFormat("#,##0");
		if (value != null) {
			return df.format(value);
		}
		return df.format(0);
	}

	public static String NumberFormat0(int value) {
		DecimalFormat df = new DecimalFormat("#,##0");
		return df.format(value);
	}
	
	public static String NumberFormat2(Double value) {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		if (value != null) {
			return df.format(value);
		}
		return df.format(0);
	}

	public static String NumberFormat2(Integer value) {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		if (value != null) {
			return df.format(value);
		}
		return df.format(0);
	}

	public static String NumberFormat2(float value) {
		DecimalFormat df = new DecimalFormat("#,##0.00");
//		if (value != null) {
			return df.format(value);
//		}
//		return df.format(0);
	}
	

	public static String getSqlValue(String val) {
		if (val != null && !val.isEmpty()) {
			return "'" + val + "'";
		}
		return "NULL";
	}

	public static String getSqlValue(Integer val) {
		if (val != null) {
			getSqlValue(val.toString());
		}
		return "NULL";
	}

	public static String getSqlValue(Short val) {
		if (val != null) {
			return "'" + val + "'";
		}
		return "NULL";
	}

	public static String getSqlValue(Long val) {
		if (val != null) {
			return "'" + val + "'";
		}
		return "NULL";
	}

	public static String getSqlValue(int val) {
		return "'" + val + "'";
	}

	public static String getSqlValue(short val) {
		return "'" + val + "'";
	}

	public static String getSqlValue(long val) {
		return "'" + val + "'";
	}
	
	public static String getSqlDateValue(Date val) {
		if (val != null) {
			SimpleDateFormat sdfDatetime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.US);
			return "to_date('" + sdfDatetime.format(val) + "', 'yyyy/mm/dd hh24:mi:ss')";
		}
		return "NULL";
	}
}

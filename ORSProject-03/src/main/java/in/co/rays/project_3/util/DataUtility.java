package in.co.rays.project_3.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data Utility class to format data
 * 
 * @author mehre
 */
public class DataUtility {

	// ✅ FIXED FORMAT (HTML date compatible)
	public static final String APP_DATE_FORMATE = "yyyy-MM-dd";
	public static final String APP_TIME_FORMATE = "yyyy-MM-dd HH:mm:ss";

	public static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMATE);
	public static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMATE);

	// Trim String
	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}

	// Object to String
	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}

	// String to int
	public static int getInt(String val) {
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}

	// String to Long
	public static Long getLong(String val) {
		if (DataValidator.isLong(val)) {
			return Long.parseLong(val);
		} else {
			return (long) 0;
		}
	}

	// ✅ FIXED: String to Date
	public static Date getDate(String val) {
		Date date = null;
		try {
			if (val != null && val.trim().length() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				date = sdf.parse(val);
			}
		} catch (Exception e) {
		}
		return date;
	}

	// ✅ FIXED: Date to String
	public static String getDateString(Date date) {
		try {
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(date);
			}
		} catch (Exception e) {
		}
		return "";
	}

	// Not used (optional)
	public static Date getDate(Date date, int day) {
		return null;
	}

	// String to Timestamp
	public static Timestamp geTimestamp(String val) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(timeFormatter.parse(val).getTime());
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	// Long to Timestamp
	public static Timestamp getTimeStamp(long l) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(l);
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	// Current Timestamp
	public static Timestamp getCurrentTimeStamp() {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
		}
		return timeStamp;
	}

	// Timestamp to long
	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	// String to Double
	public static Double getDouble(String val) {
		if (DataValidator.isDouble(val)) {
			return Double.parseDouble(val);
		} else {
			return (double) 0;
		}
	}

}
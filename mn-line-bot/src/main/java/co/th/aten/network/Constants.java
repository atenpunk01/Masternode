package co.th.aten.network;

public class Constants {

	public static int GROUP_STAFF = 1; 
	public static int VAT = 3;
	public static double DEDUCT_TRANSFER = 0;// Order By P'Jaikar 2018-06-10
	public static int PAYMENT_RATE = 1000;
	public static int REGIS_AMOUNT = 100;
	public static String LOGGER_FORMAT = "USER_LOGIN_ID : ";
	public static int PARTNER_SHARE = 5;
	public static String URL_IMAGE = "http://1.20.151.110:26/img/product/";//http://1.20.151.110:26/img/product/
	public static String DATE_FORMATE = "yyyyMMddHHmmss";
	public static int HOUR_RANDOM = 20;
	public static int POSITION_DEALER = 1;
	public static int POSITION_VIP_AGEN = 2;
	public static int POSITION_AGEN_LEVEL1 = 3;
	public static int POSITION_AGEN_LEVEL2 = 4;
	public static int POSITION_AGEN_LEVEL3 = 5;
	public static int POSITION_SHOP = 6;
	
	public static int COMMISSION_TYPE_MARKET = 1;
	public static int COMMISSION_TYPE_DIFF = 2;
	public static int COMMISSION_TYPE_ALL_SALE = 3;

	public final static int TRX_STOCK_TYPE_RECEIVE = 1; //(+)
	public final static int TRX_STOCK_TYPE_CANCLE_RECEIVE = 2; //(-)
	public final static int TRX_STOCK_TYPE_SEND = 3; //From (-), To(+)
	public final static int TRX_STOCK_TYPE_CANCLE_SEND = 4; //From (+), To(-)
	public final static int TRX_STOCK_TYPE_SELL = 5; //(-)
	public final static int TRX_STOCK_TYPE_CANCLE_SELL = 6; //(+)
	
	public final static int TRX_STOCK_STATUS_AVTIVE = 1;
	public final static int TRX_STOCK_STATUS_NOT_AVTIVE = 2;

	public final static int TRX_STOCK_DATAIL_TYPE_RECEIVE = 1;
	public final static int TRX_STOCK_DATAIL_TYPE_SELL = 2;
	public final static int TRX_STOCK_DATAIL_TYPE_PROMOTION = 3;
	
	// NEW //
	public static int SHARE_TYPE_MARKET = 1;// รายได้ตามระบบ
	public static int SHARE_TYPE_DIF = 2;// ส่วนต่างตามตำแหน่ง
	public static int SHARE_TYPE_ALL = 3;// all sale
	public static int SHARE_TYPE_INC = 4;// ค่าแนะนำ
	public static int SHARE_TYPE_DIF_AGEN = 5;// ส่วนต่างตามตำแหน่ง จาก Agency
	
	public static int POSITION_ONE_START = 1;
	public static int POSITION_THREE_START = 2;
	public static int POSITION_FIVE_START = 3;
	public static int POSITION_AGENCY = 4;
}

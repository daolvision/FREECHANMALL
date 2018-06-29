package com.daol.oms.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class DaolUtils {

	private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 문자열 형식의  현재 날짜 취득
	 * @return yyyy-MM-dd형식의 날짜문자
	 */
	public static String getCurrentDateStr(){
		return getCurrentDateStr(DEFAULT_DATE_FORMAT);
	}
	
	/**
	 * 문자열 형식의  현재 날짜 취득
	 * @param format 날짜형식
	 * @return 날짜형식으로 변환한 현재날짜 문자열
	 */
	public static String getCurrentDateStr(String format){
		return getDateStr(new Date(), format);
	}

	/**
	 * 문자열 형식으로  날짜 변환
	 * @param date 변환대상날짜
	 * @param format 날짜형식
	 * @return 날짜형식으로 변환한 날짜 문자열
	 */
	public static String getDateStr(Date date, String format){
		SimpleDateFormat sd = new SimpleDateFormat(format);
		return sd.format(date);
	}

	/**
	 * 문자열 형식으로  날짜 계산
	 * @param date 변환대상날짜
	 * @param addDay 적용일자
	 * @return 날짜형식으로 변환한 날짜 문자열
	 */
	public static String addDate(String date, int addDay) throws Exception{
		return addDate(date, addDay, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 문자열 형식으로  날짜 계산
	 * @param date 변환대상날짜
	 * @param addDay 적용일자
	 * @param format 날짜형식
	 * @return 날짜형식으로 변환한 날짜 문자열
	 */
	public static String addDate(String date, int addDay, String format) throws Exception{
		SimpleDateFormat sd = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sd.parse(date));
		cal.add(Calendar.DAY_OF_MONTH, addDay);
		return sd.format(cal.getTime());
	}

	/**
	 * 문자열형식(yyyy-MM-dd)의  날짜를 DATE 객체로 변환
	 * @param date 변환대상날짜
	 * @return 날짜객체
	 */
	public static Date converStrDate(String date) throws Exception{
		return converStrDate(date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 문자열 형식의  날짜를 DATE 객체로 변환
	 * @param date 변환대상날짜
	 * @param format 날짜형식
	 * @return 날짜객체
	 */
	public static Date converStrDate(String date, String format) throws Exception{
		SimpleDateFormat sd = new SimpleDateFormat(format);
		return sd.parse(date);
	}

	/**
	 *  DATE 객체를 문자열형식(yyyy-MM-dd)의  날짜로 변환
	 * @param date 변환대상날짜
	 * @return 날짜객체
	 */
	public static String converDateStr(Date date) throws Exception{
		return converDateStr(date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 문자열 형식의  날짜를 DATE 객체로 변환
	 * @param date 변환대상날짜
	 * @param format 날짜형식
	 * @return 날짜객체
	 */
	public static String converDateStr(Date date, String format) throws Exception{
		SimpleDateFormat sd = new SimpleDateFormat(format);
		return sd.format(date);
	}

	/**
	 * 월 계산
	 * @param date
	 * @param format
	 * @return 
	 * @throws Exception
	 */
	public static String addMonth(Date date, int addMonths) throws Exception{
		return addMonth(date, addMonths, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 월 계산
	 * @param date
	 * @param format
	 * @return 
	 * @throws Exception
	 */
	public static String addMonth(Date date, int addMonths, String format) throws Exception{
		SimpleDateFormat sd = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, addMonths);
		return sd.format(cal.getTime());
	}
	
	/**
	 * 월 계산
	 * @param date
	 * @param format
	 * @return 
	 * @throws Exception
	 */
	public static String addMonth(String date, int addMonths) throws Exception{
		return addMonth(date, addMonths, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 월 계산
	 * @param date
	 * @param format
	 * @return 
	 * @throws Exception
	 */
	public static String addMonth(String strDate, int addMonths, String format) throws Exception{
		SimpleDateFormat sd = new SimpleDateFormat(format);
		Date date = sd.parse(strDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, addMonths);
		return sd.format(cal.getTime());
	}
	
	/**
	 * Byte단위로 왼쪽 정렬 후 채우기
	 * @param target
	 * @param size
	 * @param padStr
	 * @return
	 * @throws Exception
	 */
	public static byte[] leftPadBytes(String target, int size, String padStr) throws Exception{
		byte[] targetByte = null;
		byte[] padByte = null;
		byte[] tmpByte = null;
		byte[] rtnByte = new byte[size];
		
		if(target == null){
			targetByte = new byte[0];
		}else{
			targetByte = target.getBytes("UTF-8");
		}
		
		if(size <= targetByte.length){
			System.arraycopy(targetByte, 0, rtnByte, 0, size);
			return rtnByte;
		}
		
		padByte = new byte[size - targetByte.length];
		
		int padByteLeng = 0;
		while(padByte.length > padByteLeng){
			tmpByte = padStr.getBytes("UTF-8");
			System.arraycopy(tmpByte, 0, padByte, padByteLeng, (padByteLeng + tmpByte.length) > padByte.length ? tmpByte.length - ((padByteLeng + tmpByte.length) - padByte.length) : tmpByte.length);
			padByteLeng += tmpByte.length;
		}
		
		System.arraycopy(targetByte, 0, rtnByte, 0, targetByte.length);
		System.arraycopy(padByte, 0, rtnByte, targetByte.length, padByte.length);
		
		return rtnByte;
	}

	/**
	 * Byte단위로 오른쪽 정렬 후 채우기
	 * @param target
	 * @param size
	 * @param padStr
	 * @return
	 * @throws Exception
	 */
	public static byte[] rightPadBytes(String target, int size, String padStr) throws Exception{
		byte[] targetByte = null;
		byte[] padByte = null;
		byte[] tmpByte = null;
		byte[] rtnByte = new byte[size];
		
		if(target == null){
			targetByte = new byte[0];
		}else{
			targetByte = target.getBytes("UTF-8");
		}

		if(size <= targetByte.length){
			System.arraycopy(targetByte, 0, rtnByte, 0, size);
			return rtnByte;
		}
		
		padByte = new byte[size - targetByte.length];
		
		int padByteLeng = 0;
		while(padByte.length > padByteLeng){
			tmpByte = padStr.getBytes("UTF-8");
			System.arraycopy(tmpByte, 0, padByte, padByteLeng, (padByteLeng + tmpByte.length) > padByte.length ? tmpByte.length - ((padByteLeng + tmpByte.length) - padByte.length) : tmpByte.length);
			padByteLeng += tmpByte.length;
		}
		
		System.arraycopy(padByte, 0, rtnByte, 0, padByte.length);
		System.arraycopy(targetByte, 0, rtnByte, padByte.length, targetByte.length);
		

		return rtnByte;
	}
	
	/**
	 * byte배열 합치기
	 * @param first
	 * @param second
	 * @return
	 */
	public static byte[] concatBytes(byte[] first, byte[] second){
		
		if(first == null && second == null){
			return null;
		}
		
		if(first == null){
			return second;
		}
		
		if(second == null){
			return first;
		}

		byte[] rtnByte = new byte[first.length + second.length];
		System.arraycopy(first, 0, rtnByte, 0, first.length);
		System.arraycopy(second, 0, rtnByte, first.length, second.length);
		
		return rtnByte;
	}

	/**
	 * CRC-CCIT
	 * 체크썸 크기 : 16비트
	 * 생성 다항식 : X^16 + X^12 + X^5 + 1
	 * 제수 : 0x1021
	 * 나머지 초기값 : 0xFFFF
	 * 최종 XOR값 : 0x0000
	 * @param data 변환대상
	 * @return CRC값
	*/
	public static String getCrcCcit(String data) {

		int crc = 0xFFFF;			// 나머지 초기값
		int polynomial = 0x1021;	// 제수 
	
		byte[] bytes = data.getBytes();
		for (byte b : bytes) {
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b   >> (7-i) & 1) == 1);
				boolean c15 = ((crc >> 15    & 1) == 1);
				crc <<= 1;
				if (c15 ^ bit) crc ^= polynomial;
			}
		}
	
		crc &= 0xffff;
		return StringUtils.leftPad(StringUtils.upperCase(Integer.toHexString(crc)), 4, "0");
	}

	/** 
	 * 타입 미지정 Map 캐스트
	 * @param clazzK
	 * @param clazzV
	 * @param map
	 * @return
	 */
	public static <K, V> Map<K, V> castToMapOf(Class<K> clazzK, Class<V> clazzV, Map<?, ?> map) throws Exception{

		for(Map.Entry<?, ?> e : map.entrySet()){
			if(clazzK != Object.class){
				if(!clazzK.isInstance(e.getKey())){
					throw new Exception("Can not Cast Map. key[" + clazzK.getName() + "," + e.getKey().getClass().getName());
				}
			}
			if(clazzV != Object.class){
				if(!clazzV.isInstance(e.getValue())){
					throw new Exception("Can not Cast Map. value[" + clazzK.getName() + "," + e.getValue().getClass().getName());
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		Map<K, V> result = (Map<K, V>) map;        
		return result;
		
	}

	/**
	 * 숫자 여부 판단
	 * @param target
	 * @return
	 */
	public static boolean isNumeric(String target){
		
		boolean isNumeric = false;
		
		try{
			Integer.parseInt(target);
			return true;
		}catch(Exception e){}
		
		try{
			Double.parseDouble(target);
			return true;
		}catch(Exception e){}
		
		return isNumeric;
	}

	/**
	 * 카멜케이스 변환
	 * @param target
	 * @return
	 */
	public static String toCamelCase(String target) {
		StringBuffer buffer = new StringBuffer();
		for (String token : target.toLowerCase().split("_")){
			buffer.append(StringUtils.capitalize(token));
		}
		return StringUtils.uncapitalize(buffer.toString());
	}

	/**
	 * 무작위 문자열 생성
	 * @param target
	 * @return
	 */
	public static String randomString(int digit) {
		StringBuffer rtnStr = new StringBuffer();
		Random rnd = new Random();
		for(int i = 0; i < digit; i++){
			if(rnd.nextBoolean()){
				rtnStr.append((char)((int)(rnd.nextInt(26))+97));
			}else{
				rtnStr.append((rnd.nextInt(10)));
			}
		}
		return rtnStr.toString();
	}
}
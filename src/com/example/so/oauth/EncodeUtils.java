package com.example.so.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Ravindra HV
 */
public class EncodeUtils {

	public static final String UTF_8_ENCODING = "UTF-8";
	
	/**
	 * @see http://tools.ietf.org/html/rfc3986#section-2.3
	 */
	public static final String[] UNRESERVED_CHARS = {"-",".","_","~","0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	public static final SortedSet<String> UNRESERVED_CHARS_SET;

	public static final char[] HEX_TO_CHAR = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	public static final char PERCENT_SYMBOL = '%';

	
	static {
		TreeSet<String> treeSet = new TreeSet<String>();
		Collections.addAll(treeSet, UNRESERVED_CHARS);
		UNRESERVED_CHARS_SET = Collections.unmodifiableSortedSet(treeSet);
	}
	
	public static void main(String[] args) {
		
		String input = "http://oauth.example.com/marvin~irobot/geneva-helsinki/music+video/_stars/hello world.do?q=*";
		System.out.println(input);
		String encodedOne = oauthPercentEncode(input);
		String encodedTwo = oauthPercentEncodeTwo(input);
		
		System.out.println(encodedOne);
		System.out.println(encodedTwo);
		System.out.println(encodedOne.equals(encodedTwo));
		String decodedString = oauthPercentDecode(encodedTwo);
		System.out.println(input.equals(decodedString));
		
	}
	
	/**
	 * @see http://oauth.googlecode.com/svn/code/java/core/commons/src/main/java/net/oauth/OAuth.java#percentEncode(String)
	 * @param stringToEncode
	 * @return
	 */
	public static String oauthPercentEncode(String stringToEncode) {
		if( stringToEncode == null || stringToEncode.length() == 0) {
			return stringToEncode;
		}
		String encodedString = null;
		try {
			encodedString = URLEncoder.encode(stringToEncode, UTF_8_ENCODING);
			encodedString = encodedString.replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return encodedString;
	}
	
	public static String oauthPercentEncodeTwo(String stringToEncode) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i=0; i<stringToEncode.length(); i++ ) {
			char charTemp = stringToEncode.charAt(i);
			String strTemp = String.valueOf(charTemp);
			if( UNRESERVED_CHARS_SET.contains(strTemp)) {
				stringBuilder.append(strTemp);
			}
			else {
				char[] encodedChars = urlPercentEncodeTwo(charTemp);
				stringBuilder.append(encodedChars);
			}
		}
		String result = stringBuilder.toString();
		return result;
	}
	
	public static char[] urlPercentEncodeTwo(char c) {
		char[] result = null;
		int ci = c;
		int leftNibble = (0xF0 & ci) >> 4;
		int rightNibble = (0x0F & ci );
		result = new char[]{PERCENT_SYMBOL, HEX_TO_CHAR[leftNibble], HEX_TO_CHAR[rightNibble]};
		return result;
	}
	
	public static String oauthPercentDecode(String encodedString) {
		String decodedString = null;
		try {
			decodedString = URLDecoder.decode(encodedString, UTF_8_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return decodedString;
	}

}

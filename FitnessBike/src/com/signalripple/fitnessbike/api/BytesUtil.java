package com.signalripple.fitnessbike.api;

/**
 * 字节工具
 * @author xushiyong
 *
 */
public class BytesUtil {

	/// </summary>  
	/// <param name="x"></param>  
	/// <param name="length"></param>  
	/// <returns></returns>  
	public static byte[] int2Bytes(int x, int length)  
	{  
	    byte[] b = new byte[length];  
	    int temp = x;  
	    for (int i = 0; i <= length - 1 && i < 4; i++)  
	    {  
	        b[i] = (byte)((x >> (i * 8)));  
	    }  
	    return b;  
	}  
	
	/// <summary>  
	///把数组转化为数字  解析低位存低字节，高位存高字节  
	  
	/// </summary>  
	/// <param name="b"></param>  
	/// <param name="length"></param>  
	/// <returns></returns>  
	public static int bytes2Int(byte[] b, int length)  
	{  
	    int temp = 0;  
	    for (int i = 0; i <= length - 1 && i < 4; i++)  
	    {  
	        temp += (int)(b[i] << (i * 8));  
	    }  
	    return temp;  
	}   
	
    /** 
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit 
     */  
    public static byte[] getByteArray(byte b) {  
        byte[] array = new byte[8];  
        for (int i = 7; i >= 0; i--) {  
            array[i] = (byte)(b & 1);  
            b = (byte) (b >> 1);  
        }  
        return array;  
    }  
    
    /**
     * 字节数组转化为16进制字符串
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toHexString(bytes[i] & 0xFF);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            result += hexString.toUpperCase();
        }
        return result;
    }
}

package com.strong.hardwaretest.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 各种类型转换的工具类
 */

public class DataUtil {

    //byte型转16进制字符串
    public static String Byte2Hex(Byte inByte) {
        return String.format("%02x", inByte).toUpperCase();
    }

    // 计算校验位
    public static byte calculateChk(List<Byte> data) {
        byte a = 0;
        for (int i = 0; i < data.size(); i++) {
            a += data.get(i);
        }
        a = (byte) ((~a) + 1);
        return a;
    }

    public static List<Byte> intToByteList(int i){
        List<Byte> list = new ArrayList<Byte>();
        list.add((byte) (i & 0xFF));
        list.add((byte) (i >> 8 & 0xFF));
        list.add((byte) (i >> 16 & 0xFF));
        list.add((byte) (i >> 24 & 0xFF));
        return list;
    }

    //字符串型转byte型
    static public byte HexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }


    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }


    //用来检验是奇数还是偶数
    public static int isOdd(int num) {
        return num & 0x1;
    }

    //字节数据转换成十六进制字符串
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF; // 字节型转换成整数型
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    //十六进制字符串转换成byte型数组
    public static byte[] HexToByteArr(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (isOdd(hexlen) == 1)     //isOdd(hexlen)==1表示长度为奇数
        {
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = HexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    // bytes2HexString byte与16进制字符串的互相转换
    public static String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase() + " ";
        }
        return ret;
    }

    public static String bytesList2HexString(List<Byte> b) {
        String ret = "";
        for (int i = 0; i < b.size(); i++) {
            String hex = Integer.toHexString(b.get(i) & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase() + " ";
        }
        return ret;
    }


    //将一个byte型字节转换位格式的字符
    public static String hexIntData2binaryString(byte hexData) {
        String binaryString = "", temp = "";
        binaryString = Integer.toBinaryString(hexData & 0xFF);
        for (int i = 0; i < 8 - binaryString.length(); i++) {
            temp += "0";
        }
        binaryString = temp + binaryString;
        return binaryString;
    }

    //将一个byte型字节转换位格式的字符
    public static String hexIntData2binaryString(int hexData) {
        String binaryString = "", temp = "";
        binaryString = Integer.toBinaryString(hexData & 0xFF);
        for (int i = 0; i < 8 - binaryString.length(); i++) {
            temp += "0";
        }
        binaryString = temp + binaryString;
        return binaryString;
    }

    //参数为字符的集合字节命令
    public static List<Byte> getByteListByChar(char lower, char num, char upp) {
        List<Byte> a = new ArrayList<>();
        a.add((byte) 27);
        a.add((byte) 33);
        a.add((byte) lower);
        a.add((byte) num);
        a.add((byte) upp);
        return a;
    }

    //参数为字符串的集合字节命令
    public static List<Byte> getByteListByString(char lower, String string, char upp) {
        List<Byte> a = new ArrayList<>();
        a.add((byte) 27);
        a.add((byte) 33);
        a.add((byte) lower);
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            a.add((byte) chars[i]);
        }
        a.add((byte) upp);
        return a;
    }

    public static List<Byte> getByteListByFloat(char lower, float num, char upp){
        List<Byte> a =  new ArrayList<>();
        a.add((byte) 27);
        a.add((byte) 33);
        a.add((byte) lower);
        byte[] b = ByteBuffer.allocate(4).putFloat(num).array();
        for (int i = 3 ; i >= 0 ; i--){
            a.add(b[i]);
        }
        a.add((byte)upp);
        return a;

    }

    //将字节数组转换成十六进制数组
    public static String[] byteArrayToString(byte[] array) {
        String[] string = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            string[i] = Byte2Hex(array[i]);
        }
        return string;
    }


    //1个byte型转16进制字符串
    public static String Byte2Hex(byte inByte) {
        return String.format("%02x", inByte).toUpperCase();
    }

    //获取一个浮点数的整数部分
    public static int returnIntegralPart(float n) {
        int i = (int) n;
        return i;
    }

    //字节数组转换成字符串，忽略字节数组后面多的0
    public static String byteArrayToContent(byte[] array) {
        String string = null;
        int size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                size++;
            }
        }
        byte[] content = new byte[size];
        for (int j = 0; j < size; j++) {
            content[j] = array[j];
        }
        try {
            string = new String(content, "Unicode");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }

}

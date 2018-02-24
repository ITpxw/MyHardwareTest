package com.strong.hardwaretest.entity;

/**
 * 串口数据封装类
 * 所有的串口接收数据都会封装成此类，再解析
 */
public class ComBean {

    public byte[] bRec = null;      //创建接收数据的字节数组

    public ComBean(byte[] buffer, int size) {
        bRec = new byte[size];
        for (int i = 0; i < size; i++) {
            bRec[i] = buffer[i];
        }
    }

    //重新初始化成员对象bRec
    public void initArray(byte[] buffer , int size){
        bRec = null;
        bRec = new byte[size];
        for (int i = 0; i < size; i++) {
            bRec[i] = buffer[i];
        }
    }
}
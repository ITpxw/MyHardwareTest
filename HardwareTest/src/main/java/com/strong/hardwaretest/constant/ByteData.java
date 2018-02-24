package com.strong.hardwaretest.constant;

/**
 * 存放字节数组命令常量
 * 共45条指令，每个指令为一个字节数组,是主机向NIBP模块发送的命令。
 *
 * @author Administrator
 */
public class ByteData {
    public static byte stop[] = {'X'};                                                  //测量终止
    public static byte pHand[] = {2, '0', '1', ';', ';', 'D', '7', 3};                  //开启手动测量
//    public static byte pSelfToHand[] = {2, '0', '3', ';', ';', 'D', '9', 3};            //结束自动测量方式，转为手动测量方式
//    public static byte pSelfOne[] = {2, '0', '4', ';', ';', 'D', 'A', 3};               //设置成周期为 1 分钟的自动测量方式
//    public static byte pSelfTwo[] = {2, '0', '5', ';', ';', 'D', 'B', 3};               //设置成周期为 2 分钟的自动测量方式
//    public static byte pSelfThree[] = {2, '0', '6', ';', ';', 'D', 'C', 3};             //设置成周期为 3 分钟的自动测量方式
//    public static byte pSelfFour[] = {2, '0', '7', ';', ';', 'D', 'D', 3};              //设置成周期为 4 分钟的自动测量方式
//    public static byte pSelfFive[] = {2, '0', '8', ';', ';', 'D', 'E', 3};              //设置成周期为 5 分钟的自动测量方式
//    public static byte pSelfTen[] = {2, '0', '9', ';', ';', 'D', 'F', 3};               //设置成周期为 10 分钟的自动测量方式
//    public static byte pSelfFifteen[] = {2, '1', '0', ';', ';', 'D', '7', 3};           //设置成周期为 15 分钟的自动测量方式
//    public static byte pSelfThirty[] = {2, '1', '1', ';', ';', 'D', '8', 3};            //设置成周期为 30 分钟的自动测量方式
//    public static byte pSelfSixth[] = {2, '1', '2', ';', ';', 'D', '9', 3};             //设置成周期为 60 分钟的自动测量方式
//    public static byte pSelfNiney[] = {2, '1', '3', ';', ';', 'D', 'A', 3};             //设置成周期为 90 分钟的自动测量方式
//
    public static byte pStartCalibrate[] = {2, '1', '4', ';', ';', 'D', 'B', 3};        //开始校准方式（返回实时袖带压力数据）
//    public static byte pStartWatchdog[] = {2, '1', '5', ';', ';', 'D', 'C', 3};         //进行 Watchdog 检测 （检测成功则系统复位）
//    public static byte pSystemReset[] = {2, '1', '6', ';', ';', 'D', 'D', 3};           //系统复位，完成自检
//    public static byte pLeakDetection[] = {2, '1', '7', ';', ';', 'D', 'E', 3};         //进行气路漏气检测(关阀)
    public static byte pReturnSystemStatus[] = {2, '1', '8', ';', ';', 'D', 'F', 3};    //返回系统状态

//    public static byte pNewBorn100[] = {2, '1', '9', ';', ';', 'E', '0', 3};            //在新生儿模式时，设置预充气压力为 100mmHg
//    public static byte pNewBorn120[] = {2, '2', '0', ';', ';', 'D', '8', 3};            //在新生儿模式时，设置预充气压力为 120mmHg
//    public static byte pChangeAdult140[] = {2, '2', '1', ';', ';', 'D', '9', 3};        //转换为成人模式，设置预充气压力为 140mmHg
//
//    public static byte pAC160[] = {2, '2', '2', ';', ';', 'D', 'A', 3};                 //在成人/小儿模式时，设置预充气压力为 160mmHg
//    public static byte pAC180[] = {2, '2', '3', ';', ';', 'D', 'B', 3};                 //在成人/小儿模式时，设置预充气压力为 180mmHg
    public static byte pChangeAdult150[] = {2, '2', '4', ';', ';', 'D', 'C', 3};        //转换为成人模式，设置预充气压力为 150mmHg
    public static byte pChangeNewBorn70[] = {2, '2', '5', ';', ';', 'D', 'D', 3};       //转换为新生儿模式，设置预充气压力为 70mmHg
//    public static byte pContinue5[] = {2, '2', '7', ';', ';', 'D', 'F', 3};             //开始 5 分钟的连续测量方式
//
//    public static byte pAC80[] = {2, '2', '8', ';', ';', 'E', '0', 3};                  //在成人/小儿模式时，设置预充气压力为 80mmHg
//    public static byte pAC100[] = {2, '2', '9', ';', ';', 'E', '1', 3};                 //在成人/小儿模式时，设置预充气压力为 100mmHg
//    public static byte pAC120[] = {2, '3', '0', ';', ';', 'D', '9', 3};                 //在成人/小儿模式时，设置预充气压力为 120mmHg
//    public static byte pAC140[] = {2, '3', '1', ';', ';', 'D', 'A', 3};                 //在成人/小儿模式时，设置预充气压力为 140mmHg
//    public static byte pAC160_[] = {2, '3', '2', ';', ';', 'D', 'B', 3};                //在成人/小儿模式时，设置预充气压力为 160mmHg
    public static byte pAC180[] = {2, '3', '3', ';', ';', 'D', 'C', 3};                //在成人/小儿模式时，设置预充气压力为 180mmHg
//    public static byte pAC200[] = {2, '3', '4', ';', ';', 'D', 'D', 3};                 //在成人/小儿模式时，设置预充气压力为 200mmHg
//
//    public static byte pAdult220[] = {2, '3', '5', ';', ';', 'D', 'E', 3};              //在成人模式时，设置预充气压力为 220mmHg
//    public static byte pAdult240[] = {2, '3', '6', ';', ';', 'D', 'F', 3};              //在成人模式时，设置预充气压力为 240mmHg
//
//    public static byte pNewBorn60[] = {2, '3', '7', ';', ';', 'E', '0', 3};             //在新生儿模式时，设置预充气压力为 60mmHg
//    public static byte pNewBorn80[] = {2, '3', '8', ';', ';', 'E', '1', 3};             //在新生儿模式时，设置预充气压力为 80mmHg
//    public static byte pNewBorn100_[] = {2, '3', '9', ';', ';', 'E', '2', 3};           //在新生儿模式时，设置预充气压力为 100mmHg
//    public static byte pNewBorn120_[] = {2, '4', '0', ';', ';', 'D', 'A', 3};           //在新生儿模式时，设置预充气压力为 120mmHg
//
//    public static byte pCycle120[] = {2, '4', '1', ';', ';', 'D', 'B', 3};              //设置成周期为 120 分钟的自动测量方式
//    public static byte pCycle180[] = {2, '4', '2', ';', ';', 'D', 'C', 3};              //设置成周期为 180 分钟的自动测量方式
//    public static byte pCycle240[] = {2, '4', '3', ';', ';', 'D', 'D', 3};              //设置成周期为 240 分钟的自动测量方式
//    public static byte pCycle480[] = {2, '4', '4', ';', ';', 'D', 'E', 3};              //设置成周期为 480 分钟的自动测量方式

//    public static byte pNewBorn145[] = {2, '4', '5', ';', ';', 'D', 'F', 3};            //在新生儿模式时，设置预充气压力为 145mmHg
    public static byte pChangeChildren100[] = {2, '4', '6', ';', ';', 'E', '0', 3};     //转换为小儿模式，设置预充气压力为 100mmHg
}

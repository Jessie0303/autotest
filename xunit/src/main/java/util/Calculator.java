package util;

public class Calculator {
    public static int result=1;

    // 加法
    public static int add(int x,int y) throws InterruptedException{
        result = x + y;
//        Thread.sleep(1000);
        return result;
    }

    // 加法
    public static int synAdd(int x,int y) throws InterruptedException{
        result = x + y;
        Thread.sleep(1000);
        return result;
    }

    // 减法
    public static int subtract(int x,int y) throws InterruptedException{
        result = x - y;
        Thread.sleep(1000);
        return result;
    }

    // 减法
    public static int synSubtract(int x,int y) throws InterruptedException{
        result = x - y;
        Thread.sleep(1000);
        return result;
    }

    // 乘法
    public static int multiply(int x,int y) throws InterruptedException{
        result = x * y;
        Thread.sleep(1000);
        return result;
    }

    // 除法
    public static int divide(int x,int y) throws InterruptedException{
        result = x / y;
        Thread.sleep(1000);
        return result;
    }

    public static void clear(){
        result = 0;
        System.out.println("当前结果已清零");
    }

    // 按步数加
    public static int count(int step) throws InterruptedException{
        int i = result;
        Thread.sleep(1000);
        result = i + step;
        return result;
    }

    // 同步
    public static synchronized int SynCount(int step) throws InterruptedException{
        int i = result;
        Thread.sleep(1000);
        result = i + step;
        return result;
    }
}

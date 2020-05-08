package democlass;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : duanruiqiang
 * create at:  2020/4/27  11:38 下午
 * @description:
 */
public class PingIp {
    public static void main(String[] args) throws InterruptedException {
        /**
         * corePoolSize核心线程数 1
         * maximumPoolSize最大线程数 2
         * keepAliveTime保持活跃时间 0
         * capacity有界队列的大小 3
         */
        ThreadPoolExecutor threadPoolExecutor = new
                ThreadPoolExecutor(10,
                30,
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(90));
        for (int i = 0; i < 83; i++) {
            threadPoolExecutor.execute(new ThreadTest("---"));
        }
        threadPoolExecutor.shutdown();
        try {
            while (!threadPoolExecutor.isTerminated()) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyTask myTask = MyTask.getMyTask();
        System.out.println("有异常的为"+myTask.getConcurrentHashMap().size());
        ConcurrentHashMap concurrentHashMap = myTask.getConcurrentHashMap();
        Enumeration keys = concurrentHashMap.keys();
        while (keys.hasMoreElements()){
            System.out.println(keys.nextElement().toString());
        }

//        /**
//         * 任务1不小于核心线程数，创建线程执行
//         */
//        threadPoolExecutor.execute(new ThreadTest("任务1"));
//        /**
//         * 任务2大于核心线程数，但是队列未满，入列，队列为1
//         */
//        threadPoolExecutor.execute(new ThreadTest("任务2"));
//        /**
//         * 任务3大于核心线程数，但是队列未满，入列，队列为2
//         */
//        threadPoolExecutor.execute(new ThreadTest("任务3"));
//        /**
//         * 任务4大于核心线程数，但是队列未满，入列，队列为3，此时队列已满
//         */
//        threadPoolExecutor.execute(new ThreadTest("任务4"));
//        /**
//         * 任务5队列已满，但是没有大于最大线程数，重新创建线程执行
//         */
//        threadPoolExecutor.execute(new ThreadTest("任务5"));
//        /**
//         * 任务6队列已满，大于了最大线程数，拒绝任务执行
//        */
//        threadPoolExecutor.execute(new ThreadTest("任务6"));

    }

}

class ThreadTest implements Runnable {
    private String threadName;
    private static String[] ips = {"192.168.0.10", "192.168.0.11", "192.168.0.12", "192.168.0.13", "192.168.0.14", "192.168.0.15", "192.168.0.16", "192.168.0.17", "192.168.0.18", "192.168.0.19", "192.168.0.20", "192.168.0.21", "192.168.0.22", "192.168.0.23", "192.168.0.24", "192.168.0.25", "192.168.0.26", "192.168.0.27", "192.168.0.28", "192.168.0.29", "192.168.0.30", "192.168.0.31", "192.168.0.32", "192.168.0.33", "192.168.0.34", "192.168.0.35", "192.168.0.36", "192.168.0.37", "192.168.0.38", "192.168.0.39", "192.168.0.40", "192.168.0.41", "192.168.0.42", "192.168.0.43", "192.168.0.44", "192.168.0.45", "192.168.0.46", "192.168.0.47", "192.168.0.48", "192.168.0.49", "192.168.0.50", "192.168.0.51", "192.168.0.190", "192.168.0.53", "192.168.0.54", "192.168.0.60", "192.168.0.61", "192.168.0.62", "192.168.0.63", "192.168.0.64", "192.168.0.65", "192.168.0.66", "192.168.0.67", "192.168.0.68", "192.168.0.69", "192.168.0.70", "192.168.0.71", "192.168.0.72", "192.168.0.73", "192.168.0.74", "192.168.0.75", "192.168.0.76", "192.168.0.77", "192.168.0.78", "192.168.0.79", "192.168.0.80", "192.168.0.81", "192.168.0.82", "192.168.0.83", "192.168.0.84", "192.168.0.85", "192.168.0.86", "192.168.0.87", "192.168.0.88", "192.168.0.89", "192.168.0.90", "192.168.0.91", "192.168.0.92", "192.168.0.93", "192.168.0.94", "192.168.0.221", "192.168.0.220", "192.168.0.219"};


    public ThreadTest(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        MyTask myTask = MyTask.getMyTask();
        int num = myTask.getNum();
        ConcurrentHashMap concurrentHashMap = myTask.getConcurrentHashMap();
        try {
            InetAddress addr = InetAddress.getByName(ips[num - 1]);
            if (addr.isReachable(5000)) {

                System.out.println(Thread.currentThread().getName() + threadName + "获取的num为" + num+"------ok");
            } else {
                concurrentHashMap.put(num,ips[num - 1]);
                System.out.println(Thread.currentThread().getName() + threadName + "获取的num为" + num+"------有问题");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}


class MyTask {
    private int num = 0;
    private static MyTask myTask = new MyTask();
    private ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    private LinkedList isOK = new LinkedList();
    private LinkedList notOK = new LinkedList();

    public static synchronized MyTask getMyTask() {
        return myTask;
    }

    public int getNum() {
        num++;
        return num;
    }

    public synchronized LinkedList getIsOK() {
        return isOK;
    }

    public void setIsOK(LinkedList isOK) {
        this.isOK = isOK;
    }

    public synchronized LinkedList getNotOK() {
        return notOK;
    }

    public void setNotOK(LinkedList notOK) {
        this.notOK = notOK;
    }

    public ConcurrentHashMap getConcurrentHashMap() {
        return concurrentHashMap;
    }

    public void setConcurrentHashMap(ConcurrentHashMap concurrentHashMap) {
        this.concurrentHashMap = concurrentHashMap;
    }
}

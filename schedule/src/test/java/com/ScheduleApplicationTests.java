package com;

import com.dao.TaskDao;
import com.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ScheduleApplicationTests {
    @Autowired
    private TaskDao taskDao;
    static int k = 0;
    int arr[] = {1, 2, 3, 4, 5};
    double maxPrice =100;


    static int M = 5;
    static int[] a= new int[]{1,2,3,4,5};
    static int[] b = new int[M];
    static int N = a.length;
    static List<String> list=new ArrayList<>();

    /**
     * m个数字中取n个的集合
     * @param m
     * @param n
     */
    static void C(int m,int n){
        int i,j;
        for(i=n;i<=m;i++) {
            b[n-1] = i-1;
            if(n>1) {
                C(i - 1, n - 1);
            }else {
                List<String> op= new ArrayList<>();
                for(j=0;j<=M-1;j++){
                    op.add(a[b[j]]+"");
                    System.out.print(a[b[j]] + "  ");
                }
                String s = String.join(",", op);
                list.add(s);
                System.out.println();
            }
        }
    }


    /**
     * 1.主程序入口
     * @throws InterruptedException
     */
    @Test
    void mainProcess() throws InterruptedException {
        permutations(arr, 0, 4);
        System.out.println("因此最小移动代价为："+maxPrice);
        System.out.println("最小移动代价任务执行顺序为：53241");
    }


    /**
     * 2.给出数组里的数字的全排序，并计算各排序的总移动代价
     * @param arr
     * @param m
     * @param n
     * @throws InterruptedException
     */
    public void permutations(int[] arr, int m, int n) throws InterruptedException {
        if (m == n) {
            k++;
            System.out.print("第" + k + "种任务执行顺序:");
            for (Integer i : arr) {
                System.out.print(i);
            }
            double price = Price(arr);
            System.out.println("，总移动代价为："+ price);
            if (price<maxPrice){
                maxPrice=price;
            }
        } else {
            for (int i = m; i <= n; i++) {
                int temp = arr[m];
                arr[m] = arr[i];
                arr[i] = temp;
                permutations(arr, m + 1, n);
                temp = arr[m];
                arr[m] = arr[i];
                arr[i] = temp;
            }
        }
    }

    /**
     * 3.给定一个数组，根据数组里的顺序，返回该顺序的总移动代价,并修改数据库里的数据
     *
     * @param arr
     * @return 返回总移动代价
     * @throws InterruptedException
     */
    public double Price(int[] arr) throws InterruptedException {
        double price = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            Task task1 = taskDao.getById(arr[i]);
            Task task2 = taskDao.getById(arr[i + 1]);
            //1.修改数据库中任务顺序
            task1.setNext_task(arr[i + 1]);

            //2.根据任务顺序计算每个任务的空载移动代价
            double emptyPrice = emptyPrice(task1, task2);
            task2.setEmpty_price(emptyPrice);
            taskDao.updateTasks(task1);
            taskDao.updateTasks(task2);

//            Thread.sleep(3000);
        }
        //3.最后一个任务的next_task设为100
        Task LastTask = taskDao.getById(arr[arr.length - 1]);
        LastTask.setNext_task(100);
        taskDao.updateTasks(LastTask);

        //4.第一个任务的空载移动代价设为（0,0）到该点的曼哈顿距离
        double empty=0;
        Task firstTask = taskDao.getById(arr[0]);
        String origin = firstTask.getOrigin();
        String[] strings = origin.split(",");

        int[] ints = StringToInt(strings);
        for (int i = 0; i < 2; i++) {
            int abs = Math.abs(ints[i]);
            empty+=abs;
        }
        firstTask.setEmpty_price(empty);
        taskDao.updateTasks(firstTask);

        //5.将所有任务的空载移动代价和负载移动代价相加，求得总移动代价
        List<Task> tasks = taskDao.selectAllTasks();
        for (Task task:tasks){
            double empty_price = task.getEmpty_price();
            double carry_price = task.getCarry_price();
            price+=empty_price+carry_price;
        }
        return price;
    }

    /**
     * 4.返回两个任务之间的空载移动代价，即任务1到任务2的空载代价
     *
     * @param task1
     * @param task2
     * @return
     */
    public double emptyPrice(Task task1, Task task2) {
        double emptyPrice = 0;
        //拿到任务1的终点和任务2的起点,算曼哈顿距离
        String destination = task1.getDestination();
        String origin = task2.getOrigin();
        String regex = ",";

        String[] split1 = destination.split(regex);
        String[] split2 = origin.split(regex);

        int[] ints1 = StringToInt(split1);
        int[] ints2 = StringToInt(split2);

        for (int i = 0; i < 2; i++) {
            int i1 = ints1[i] - ints2[i];
            emptyPrice += Math.abs(i1);
        }
        return emptyPrice;
    }

    /**
     * 5.字符串数组转int数组
     *
     * @param arr
     * @return
     */
    public int[] StringToInt(String[] arr) {
        int[] array = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            array[i] = Integer.parseInt(arr[i]);
        }
        return array;
    }

    /**
     * 6.删除arr1中包含arr2的元素，即返回一个arr2关于arr1的互补数组
     * @param arr1
     * @param arr2
     * @return
     */
    public static int[] implement(int[] arr1,int[] arr2){
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        for (Integer i:arr1){
            arrayList1.add(i);
        }
        for (Integer i:arr2){
            arrayList2.add(i);
        }
        for (Integer i:arrayList2){
            arrayList1.remove(i);
        }
        int[] arr=new int[arrayList1.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=arrayList1.get(i);
        }
        return arr;
    }

    /**
     * 测试程序
     * @throws InterruptedException
     */
    @Test
    void Test() throws InterruptedException {
        int[] arr = {1,2,3,4,5};
        System.out.println("当1号机器人任务顺序为：1,2,3时：移动代价为：41");
        System.out.println("当2号机器人任务顺序为：4,5时：移动代价为：30");
        System.out.println("总移动代价为：71");

//        for (Integer i:arr){
//            System.out.print(i);
//        }
//        System.out.println();
//        System.out.println("总移动代价为："+v);
    }

}

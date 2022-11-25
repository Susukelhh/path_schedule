package com;

import com.dao.RobotDao;
import com.dao.ScheduleDao;
import com.dao.TaskDao;
import com.domain.Robot;
import com.domain.Schedule;
import com.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DuoRobot {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private RobotDao robotDao;
    @Autowired
    private ScheduleDao scheduleDao;

    static int k = 0;
    static int k2 = 0;
    static int k3 = 1;

    double firstMinPrice =1000;
    double secondMinPrice =1000;

    double allCount=2000;

    static int M = 4;
    static int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
    static int[] b = new int[M];
    static int N = a.length;
    static List<String> list = new ArrayList<>();


    /**
     * 1.主程序入口
     *
     * @throws InterruptedException
     */
    @Test
    void mainProcess() throws InterruptedException {

        C(N, M);
        //1.得到list集合与ints数组集合，即一号机器人的执行任务顺序
        System.out.println(list);
        List<int[]> ints = stringToInt(list);
        int l=1;
        for (int[] ints1 : ints) {
            int[] ints2 = implement(a, ints1);

            //首先初始化数据库
            List<Task> tasks = taskDao.selectAllTasks();
            for (Task task:tasks){
                task.setRobot_id(0);
                taskDao.updateTasks(task);
            }
            permutations(ints1, 0, 3);
            permutations2(ints2, 0, 3);
            k3++;
            System.out.println("第"+l+"轮任务分配1号机器人最小移动代价值："+ firstMinPrice);
            System.out.println("第"+l+"轮任务分配2号机器人最小移动代价值："+ secondMinPrice);
            l++;
            double c= firstMinPrice + secondMinPrice;
            System.out.print("当1号机器人执行"+ints1[0]+"、"+ints1[1]+"、"+ints1[2]+"、"+ints1[3]+"号任务时，");
            System.out.println("2号机器人执行"+ints2[0]+"、"+ints2[1]+"、"+ints2[2]+"、"+ints2[3]+"号任务的最小移动代价值为："+c);

            firstMinPrice=1000;
            secondMinPrice=1000;
        }
    }

    /**
     * 字符串集合转成int数组集合
     *
     * @param list
     * @return
     */
    static List<int[]> stringToInt(List<String> list) {
        List<int[]> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            String[] split = s.split(",");
            int[] ints = new int[split.length];
            for (int j = 0; j < split.length; j++) {
                ints[j] = Integer.parseInt(split[j]);
            }
            arrayList.add(ints);
        }
        return arrayList;
    }


    /**
     * 2.给出数组里的数字的全排序，并计算各排序的总移动代价
     *
     * @param arr
     * @param m
     * @param n
     * @throws InterruptedException
     */
    public void permutations(int[] arr, int m, int n) throws InterruptedException {
        if (m == n) {
            k++;
            System.out.print("一号机器人第" + k + "种任务执行顺序:");
            for (Integer i : arr) {
                System.out.print(i);
            }
            double price = Price(arr, 1);
            if (price< firstMinPrice){
                firstMinPrice =price;

                Schedule schedule = new Schedule();
                schedule.setId(k3);
                schedule.setFirstTasks(arrToString(arr));
                schedule.setFirstPrice(firstMinPrice);
                Integer integer = scheduleDao.selectIfContainById(k3);
                if (integer==0){
                    scheduleDao.insertSchedule(schedule);
                }else {
                    scheduleDao.updateSchedule(schedule);
                }
            }
            System.out.println("，总移动代价为：" + price);
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

    public void permutations2(int[] arr, int m, int n) throws InterruptedException {
        if (m == n) {
            k2++;
            System.out.print("二号机器人第" + k2 + "种任务执行顺序:");
            for (Integer i : arr) {
                System.out.print(i);
            }
            double price = Price(arr, 2);
            if (price< secondMinPrice){
                secondMinPrice =price;

                Schedule scheduleById = scheduleDao.selectScheduleById(k3);
                scheduleById.setSecondPrice(secondMinPrice);
                scheduleById.setSecondTasks(arrToString(arr));
                scheduleById.setAllPrice(firstMinPrice+secondMinPrice);
                scheduleDao.updateSchedule(scheduleById);
            }
            System.out.println("，总移动代价为：" + price);
        } else {
            for (int i = m; i <= n; i++) {
                int temp = arr[m];
                arr[m] = arr[i];
                arr[i] = temp;
                permutations2(arr, m + 1, n);
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
    public double Price(int[] arr, int num) throws InterruptedException {
        double price = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            Task task1 = taskDao.getById(arr[i]);
            Task task2 = taskDao.getById(arr[i + 1]);
            //1.修改数据库中任务顺序
            task1.setNext_task(arr[i + 1]);
            if (num == 1) {
                task1.setRobot_id(1);
            } else {
                task1.setRobot_id(2);
            }
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
        if (num == 1) {
            LastTask.setRobot_id(1);
        } else {
            LastTask.setRobot_id(2);
        }
        taskDao.updateTasks(LastTask);

        //4.第一个任务的空载移动代价设为（0,0）到该点的曼哈顿距离/（10，10）到该点的曼哈顿距离
        double empty = 0;
        Task firstTask = taskDao.getById(arr[0]);
        String origin = firstTask.getOrigin();
        String[] strings = origin.split(",");

        int[] ints = StringToInt(strings);
        for (int i = 0; i < 2; i++) {
            if (num == 1) {
                int abs = Math.abs(ints[i]);
                empty += abs;
            } else {
                int abs = Math.abs(ints[i] - 10);
                empty += abs;
            }
        }
        firstTask.setEmpty_price(empty);
        taskDao.updateTasks(firstTask);

        //5.将机器人任务的空载移动代价和负载移动代价相加，求得总移动代价
        List<Task> tasks = taskDao.selectFirstTasks();
        List<Task> tasks2 = taskDao.selectSecondTasks();
        if (num == 1) {
            for (Task task : tasks) {
                double empty_price = task.getEmpty_price();
                double carry_price = task.getCarry_price();
                price += empty_price + carry_price;
            }
        } else {
            for (Task task : tasks2) {
                double empty_price = task.getEmpty_price();
                double carry_price = task.getCarry_price();
                price += empty_price + carry_price;
            }
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
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static int[] implement(int[] arr1, int[] arr2) {
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        for (Integer i : arr1) {
            arrayList1.add(i);
        }
        for (Integer i : arr2) {
            arrayList2.add(i);
        }
        for (Integer i : arrayList2) {
            arrayList1.remove(i);
        }
        int[] arr = new int[arrayList1.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arrayList1.get(i);
        }
        return arr;
    }

    /**
     * 7.从m个数字中取n个的集合
     *
     * @param m
     * @param n
     */
    static List<String> C(int m, int n) {
        int i, j;
        for (i = n; i <= m; i++) {
            b[n - 1] = i - 1;
            if (n > 1) {
                C(i - 1, n - 1);
            } else {
                List<String> op = new ArrayList<>();
                for (j = 0; j <= M - 1; j++) {
                    op.add(a[b[j]] + "");
                }
                String s = String.join(",", op);
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 8。将int数组里的数字用逗号拼接成字符串
     * @param arr
     * @return
     */
    String arrToString(int[] arr){
        String s="";
        for (int i = 0; i < arr.length; i++) {
            if (i<arr.length-1){
                s+=String.valueOf(arr[i])+",";
            }else {
                s+=String.valueOf(arr[i]);
            }
        }
        return s;
    }

    /**
     * 测试程序
     *
     * @throws InterruptedException
     */
    @Test
    void Test() throws InterruptedException {
        Integer integer = scheduleDao.selectIfContainById(1);
        System.out.println(integer);
    }

}

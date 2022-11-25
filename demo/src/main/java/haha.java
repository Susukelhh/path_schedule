public class haha {
    static int k = 0;

    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5};
        permutations(arr, 0, 4);
    }

    public static void permutations(int[] arr, int m, int n) {
        if (m == n) {
            k++;
            System.out.print(k + "种任务执行顺序:");
            for (int i = 0; i <= n; i++) {
                System.out.print(arr[i]);
            }
            System.out.println();
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
}
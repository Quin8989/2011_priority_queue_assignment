import java.util.PriorityQueue;


public class PQSort {
    static <E extends Comparable<E>> void heapSort(E [] a) {
        PriorityQueue<E> pQueue = new PriorityQueue<E>();
        for(E i : a) {
            System.out.println(i);
            pQueue.offer(i);
        }
        System.out.println(" ");
        for(int k = 0 ; k < a.length; k++) {
            System.out.println(pQueue.poll());
        }
        
    }
    

    static <E extends Comparable<E>> void heapSort2011(E[] a) {
        PriorityQueue2011<E> pQueue = new PriorityQueue2011<E>();
        for(E i : a) {
            System.out.println(i);
            pQueue.offer(i);
        }
        System.out.println(" ");
        for(int k = 0 ; k < a.length; k++) {
            System.out.println(pQueue.poll());
        }
        
    }
    public static void main(String[] args) {
        Integer[] arr = {1, 0, 3, 233333, 6, 5};
        String[] items = { "100", "300", "200", "500", "500", "1000"}; 
        heapSort(arr);
        heapSort(items);
        heapSort2011(arr);
        heapSort2011(items);
        
    }


}
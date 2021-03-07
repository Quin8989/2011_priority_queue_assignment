
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
        System.out.println("");
        
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
}
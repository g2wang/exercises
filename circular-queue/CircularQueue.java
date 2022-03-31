import java.util.Arrays;

/**
 * FIFO Cicular Queue
 */
public class CircularQueue {
    private int[] q;
    private int head = 0;
    private int rearExcl = 0;
    private int count = 0;

    public CircularQueue(int capacity) {
        q = new int[capacity];
    }

    public boolean enQueue(int i) {
        if (isFull()) throw new IllegalStateException("queue is full");
        q[rearExcl] = i;
        rearExcl = (rearExcl + 1) % q.length;
        count++;
        return true;
    }

    public int deQueue(int i) {
        if (isEmpty()) throw new IllegalStateException("queue is empty");
        int value = q[head];
        head = (head + 1) % q.length;
        count--;
        return value;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean isFull() {
        return size() == q.length;
    }

    public int front() {
        if (isEmpty()) throw new IllegalStateException("queue is empty");
        return q[head];
    }

    public int rear() {
        if (isEmpty()) throw new IllegalStateException("queue is empty");
        int lastIndex = rearExcl - 1;
        if (lastIndex < 0) lastIndex = q.length + lastIndex;
        return q[lastIndex];
    }

    public String toString() {
        return Arrays.toString(q);
    }
    
    public static void main(String[] args) {
        CircularQueue q = new CircularQueue(3);
        for (int i = 1; i < 4; i++) {
            q.enQueue(i);
        }
        System.out.println("q = " + q);
        System.out.println("front = " + q.front());
        System.out.println("rear = " + q.rear());
        System.out.println("size = " + q.size());
        System.out.println("isFull = " + q.isFull());
        System.out.println("isEmpty = " + q.isEmpty());
    }
}

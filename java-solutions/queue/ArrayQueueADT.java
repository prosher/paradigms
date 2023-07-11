package queue;

import java.util.Objects;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutableSubarray(s1, s2, len): forall i=1..len: a'[s1 + i - 1] = a[s2 + i - 1]
public class ArrayQueueADT {
    private int size;
    private int head;
    private Object[] array = new Object[4];

    // Pre: true
    // Post: R.n = 0
    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }

    // Здесь и далее в условии метода считается, что:
    // a = queue.a, n = queue.n, immutableSubarray = queue.immutableSubarray

    // Pre: queue != null
    // R = a' && n' = n && immutableSubarray(1, 1, n')
    public static Object[] toArray(ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        Object[] result = new Object[queue.size];
        if (queue.head + queue.size <= queue.array.length) {
            System.arraycopy(queue.array, queue.head, result, 0, queue.size);
        } else {
            System.arraycopy(queue.array, queue.head, result, 0, queue.array.length - queue.head);
            System.arraycopy(queue.array, 0, result,
                    queue.array.length - queue.head, queue.head + queue.size - queue.array.length);
        }
        return result;
    }

    // Pre: queue != null && element != null
    // Post: n' = n + 1 && a'[1] = element && immutableSubarray(2, 1, n)
    public static void push(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        Objects.requireNonNull(queue);
        ensureCapacity(queue, queue.size + 1);
        queue.head = (queue.head - 1 + queue.array.length) % queue.array.length;
        queue.array[queue.head] = element;
        queue.size++;
    }

    // Pre: queue != null && n > 0
    // Post: R = a[n] && n' = n && immutableSubarray(1, 1, n')
    public static Object peek(ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        if (isEmpty(queue)) {
            throw new IllegalArgumentException("Queue is empty");
        }
        return queue.array[(queue.head + queue.size - 1) % queue.array.length];
    }

    // Pre: queue != null && n > 0
    // Post: R = a[n] && n' = n - 1 && immutableSubarray(1, 1, n')
    public static Object remove(ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        Object result = peek(queue);
        queue.array[(queue.head + queue.size - 1) % queue.array.length] = null;
        queue.size--;
        return result;
    }

    // Pre: queue != null && element != null
    // Post: n' = n + 1 && a'[n'] = element && immutableSubarray(1, 1, n)
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        Objects.requireNonNull(queue);
        ensureCapacity(queue, queue.size + 1);
        queue.array[(queue.head + queue.size) % queue.array.length] = element;
        queue.size++;
    }

    // Pre: queue != null
    // Post: n' = n && immutable(1, 1, n)
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        Objects.requireNonNull(queue);
        if (queue.array.length < capacity) {
            Object[] newArray = new Object[2 * capacity];
            System.arraycopy(queue.array, queue.head,
                    newArray, 0, queue.array.length - queue.head);
            System.arraycopy(
                    queue.array,
                    0,
                    newArray,
                    queue.array.length - queue.head,
                    queue.head + queue.size - queue.array.length
            );
            queue.array = newArray;
            queue.head = 0;
        }
    }

    // Pre: queue != null && size > 0
    // Post: R = a[1] && n' = n && immutableSubarray(1, 1, n')
    public static Object element(ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        if (queue.size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return queue.array[queue.head];
    }

    // Pre: queue != null && size > 0
    // Post: R = a[1] && n' = n - 1 && immutableSubarray(1, 2, n')
    public static Object dequeue(ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        Object result = element(queue);
        queue.array[queue.head] = null;
        queue.head = (queue.head + 1) % queue.array.length;
        queue.size--;
        return result;
    }

    // Pre: queue != null
    // Post: R = n && n' = n && immutableSubarray(1, 1, n')
    public static int size(ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        return queue.size;
    }

    // Pre: queue != null
    // Post: R = (n = 0) && n' = n && immutableSubarray(1, 1, n')
    public static boolean isEmpty(ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        return size(queue) == 0;
    }

    // Pre: queue != null
    // Post: n' = 0
    public static void clear(ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        queue.array = new Object[4];
        queue.size = queue.head = 0;
    }
}

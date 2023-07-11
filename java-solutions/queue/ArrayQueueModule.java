package queue;

import java.util.Objects;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutableSubarray(s1, s2, len): forall i=1..len: a'[s1 + i - 1] = a[s2 + i - 1]
public class ArrayQueueModule {
    private static int size = 0;
    private static int head = 0;
    private static Object[] array = new Object[4];

    // Pre: true
    // R = a' && n' = n && immutableSubarray(1, 1, n')
    public static Object[] toArray() {
        Object[] result = new Object[size];
        if (head + size <= array.length) {
            System.arraycopy(array, head, result, 0, size);
        } else {
            System.arraycopy(array, head, result, 0, array.length - head);
            System.arraycopy(array, 0, result, array.length - head, head + size - array.length);
        }
        return result;
    }

    // Pre: element != null
    // Post: n' = n + 1 && a'[1] = element && immutableSubarray(2, 1, n)
    public static void push(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        head = (head - 1 + array.length) % array.length;
        array[head] = element;
        size++;
    }

    // Pre: n > 0
    // Post: R = a[n] && n' = n && immutableSubarray(1, 1, n')
    public static Object peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Queue is empty");
        }
        return array[(head + size - 1) % array.length];
    }

    // Pre: n > 0
    // Post: R = a[n] && n' = n - 1 && immutableSubarray(1, 1, n')
    public static Object remove() {
        Object result = peek();
        array[(head + size - 1) % array.length] = null;
        size--;
        return result;
    }


    // Pre: element != null
    // Post: n' = n + 1 && a'[n'] = element && immutableSubarray(1, 1, n)
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        array[(head + size) % array.length] = element;
        size++;
    }

    // Pre: true
    // Post: n' = n && immutableSubarray(1, 1, n')
    private static void ensureCapacity(int capacity) {
        if (array.length < capacity) {
            Object[] newArray = new Object[2 * capacity];
            if (head + size <= array.length) {
                System.arraycopy(array, head, newArray, 0, size);
            } else {
                System.arraycopy(array, head, newArray, 0, array.length - head);
                System.arraycopy(array, 0, newArray, array.length - head, head + size - array.length);
            }
            array = newArray;
            head = 0;
        }
    }

    // Pre: size > 0
    // Post: R = a[1] && n' = n && immutableSubarray(1, 1, n')
    public static Object element() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return array[head];
    }

    // Pre: size > 0
    // Post: R = a[1] && n' = n - 1 && immutableSubarray(1, 2, n')
    public static Object dequeue() {
        Object result = element();
        array[head] = null;
        head = (head + 1) % array.length;
        size--;
        return result;
    }

    // Pre: true
    // Post: R = n && n' = n && immutableSubarray(1, 1, n')
    public static int size() {
        return size;
    }

    // Pre: true
    // Post: R = (n = 0) && n' = n && immutableSubarray(1, 1, n')
    public static boolean isEmpty() {
        return size() == 0;
    }

    // Pre: true
    // Post: n' = 0
    public static void clear() {
        array = new Object[4];
        size = head = 0;
    }
}

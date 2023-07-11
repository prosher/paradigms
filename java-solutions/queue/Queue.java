package queue;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// Let: immutableSubarray(s1, s2, len): forall i=1..len: a'[s1 + i - 1] = a[s2 + i - 1]
public interface Queue {
    // Pre: element != null
    // Post: n' = n && immutableSubarray(1, 1, n')
    //      && (R = false && forall i=1..n a[n] != element
    //      || R = true && exists 1 <= i <= n : a[i] = element)
    boolean contains(Object element);

    // Pre: n > 0 && element != null
    // Post: immutableSubarray(1, 1, n')
    //      && (R = false && forall i=1..n a[n] != element && n' = n
    //      || R = true && exists 1 <= i <= n : a[i] = element && n' = n - 1
    //      && immutableSubarray(1, 1, i - 1) && immutableSubarray(i, i + 1, n - i))
    boolean removeFirstOccurrence(Object element);

    // Pre: true
    // R = a' && n' = n && immutableSubarray(1, 1, n')
    Object[] toArray();

    // Pre: element != null
    // Post: n' = n + 1 && a'[n'] = element && immutableSubarray(1, 1, n)
    void enqueue(Object element);

    // Pre: n > 0
    // Post: R = a[1] && n' = n && immutableSubarray(1, 1, n')
    Object element();

    // Pre: n > 0
    // Post: R = a[1] && n' = n - 1 && immutableSubarray(1, 2, n')
    Object dequeue();

    // Pre: true
    // Post: R = n && n' = n && immutableSubarray(1, 1, n')
    int size();

    // Pre: true
    // Post: R = (n = 0) && n' = n && immutableSubarray(1, 1, n')
    boolean isEmpty();

    // Pre: true
    // Post: n' = 0
    void clear();
}

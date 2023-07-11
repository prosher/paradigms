package queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MyArrayQueueTest {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        assert queue.isEmpty();
        checkContracts(queue);
        queue.clear();
        fill(queue, "q_");
        System.out.println(Arrays.toString(queue.toArray()));
        dump(queue);

        ArrayQueue deque = new ArrayQueue();
        assert deque.isEmpty();
        checkContractsDeque(deque);
        deque.clear();
        fillDeque(deque, "d_");
        System.out.println(Arrays.toString(deque.toArray()));
        dumpDeque(deque);
    }

    public static void fill(ArrayQueue queue, String prefix) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(prefix + i);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.dequeue());
        }
    }

    public static void fillDeque(ArrayQueue queue, String prefix) {
        for (int i = 0; i < 10; i++) {
            queue.push(prefix + i);
        }
    }

    public static void dumpDeque(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.remove());
        }
    }

    private static void checkContracts(ArrayQueue queue) {
        checkSize(queue);
        checkClear(queue);
        checkToArray(queue);
        checkEnqueue(queue);
        checkElement(queue);
        checkDequeue(queue);
    }

    private static void checkContractsDeque(ArrayQueue deque) {
        checkContracts(deque);
        checkPush(deque);
        checkPeek(deque);
        checkRemove(deque);
    }

    private static Object[] extractArray(ArrayQueue queue) {
        List<Object> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Object element = queue.dequeue();
            assert element != null;
            result.add(element);
        }
        return result.toArray();
    }

    private static void checkRemove(ArrayQueue deque) {
        Object[] preA = deque.toArray();
        int preN = preA.length;
        assert preN > 0;
        Object R = deque.remove();
        Objects.requireNonNull(R);
        Object[] postA = deque.toArray();
        int postN = postA.length;
        assert Objects.equals(R, preA[preN - 1])
                && preN - 1 == postN
                && immutableSubarray(postA, 0, preA, 0, postN);
    }

    private static void checkPeek(ArrayQueue deque) {
        deque.push(5);
        Object[] preA = deque.toArray();
        int preN = preA.length;
        assert preN > 0;
        Object R = deque.peek();
        Objects.requireNonNull(R);
        Object[] postA = deque.toArray();
        int postN = postA.length;
        assert Objects.equals(R, postA[postN - 1])
                && preN == postN
                && immutableSubarray(postA, 0, preA, 0, postN);
    }

    private static void checkPush(ArrayQueue deque) {
        Object[] preA = deque.toArray();
        int preN = preA.length;
        Object element = 5;
        Objects.requireNonNull(element);
        deque.push(element);
        Object[] postA = deque.toArray();
        int postN = postA.length;
        assert preN + 1 == postN
                && postA[0] == element
                && immutableSubarray(postA, 1, preA, 0, preN);
    }

    private static void checkToArray(ArrayQueue queue) {
        Object[] preA = queue.toArray();
        int preN = preA.length;
        Object[] R = extractArray(queue);
        Object[] postA = queue.toArray();
        int postN = postA.length;
        assert preN == postN && Arrays.equals(R, postA) && Arrays.equals(preA, postA);
    }

    private static void checkSize(ArrayQueue queue) {
        Object[] preA = queue.toArray();
        int preN = preA.length;
        int R = queue.size();
        Object[] postA = queue.toArray();
        int postN = postA.length;
        assert R == postN && postN == preN && Arrays.equals(preA, postA);
    }

    private static void checkClear(ArrayQueue queue) {
        queue.clear();
        assert queue.size() == 0;
    }

    private static void checkEnqueue(ArrayQueue queue) {
        Object[] preA = queue.toArray();
        int preN = preA.length;
        Object element = 5;
        Objects.requireNonNull(element);
        queue.enqueue(element);
        Object[] postA = queue.toArray();
        int postN = postA.length;
        assert preN + 1 == postN
                && postA[postN - 1] == element
                && immutableSubarray(postA, 0, preA, 0, preN);
    }

    private static void checkDequeue(ArrayQueue queue) {
        Object[] preA = queue.toArray();
        int preN = preA.length;
        assert preN > 0;
        Object R = queue.dequeue();
        Objects.requireNonNull(R);
        Object[] postA = queue.toArray();
        int postN = postA.length;
        assert Objects.equals(R, preA[0])
                && preN - 1 == postN
                && immutableSubarray(postA, 0, preA, 1, postN);
    }

    private static void checkElement(ArrayQueue queue) {
        Object[] preA = queue.toArray();
        int preN = preA.length;
        assert preN > 0;
        Object R = queue.element();
        Objects.requireNonNull(R);
        Object[] postA = queue.toArray();
        int postN = postA.length;
        assert Objects.equals(R, preA[0])
                && preN == postN
                && immutableSubarray(postA, 0, preA, 0, postN);
    }

    private static boolean immutableSubarray(Object[] a1, int s1, Object[] a2, int s2, int len) {
        for (int i = 0; i < len; i++) {
            if (!Objects.equals(a1[s1 + i], a2[s2 + i])) {
                return false;
            }
        }
        return true;
    }
}

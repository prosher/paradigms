package queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MyArrayQueueADTTest {
    public static void main(String[] args) {
        ArrayQueueADT queue = ArrayQueueADT.create();
        assert ArrayQueueADT.isEmpty(queue);
        checkContracts(queue);
        ArrayQueueADT.clear(queue);
        fill(queue, "q_");
        System.out.println(Arrays.toString(ArrayQueueADT.toArray(queue)));
        dump(queue);

        ArrayQueueADT deque = ArrayQueueADT.create();
        assert ArrayQueueADT.isEmpty(deque);
        checkContractsDeque(deque);
        ArrayQueueADT.clear(deque);
        fillDeque(deque, "d_");
        System.out.println(Arrays.toString(ArrayQueueADT.toArray(deque)));
        dumpDeque(deque);
    }

    public static void fill(ArrayQueueADT queue, String prefix) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.enqueue(queue, prefix + i);
        }
    }

    public static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(ArrayQueueADT.size(queue) + " " + ArrayQueueADT.dequeue(queue));
        }
    }

    public static void fillDeque(ArrayQueueADT queue, String prefix) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueADT.push(queue, prefix + i);
        }
    }

    public static void dumpDeque(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(ArrayQueueADT.size(queue) + " " + ArrayQueueADT.remove(queue));
        }
    }

    private static void checkContracts(ArrayQueueADT queue) {
        checkSize(queue);
        checkClear(queue);
        checkToArray(queue);
        checkEnqueue(queue);
        checkElement(queue);
        checkDequeue(queue);
    }

    private static void checkContractsDeque(ArrayQueueADT deque) {
        checkContracts(deque);
        checkPush(deque);
        checkPeek(deque);
        checkRemove(deque);
    }

    private static Object[] extractArray(ArrayQueueADT queue) {
        List<Object> result = new ArrayList<>();
        while (!ArrayQueueADT.isEmpty(queue)) {
            Object element = ArrayQueueADT.dequeue(queue);
            assert element != null;
            result.add(element);
        }
        return result.toArray();
    }

    private static void checkRemove(ArrayQueueADT deque) {
        Object[] preA = ArrayQueueADT.toArray(deque);
        int preN = preA.length;
        assert preN > 0;
        Object R = ArrayQueueADT.remove(deque);
        Objects.requireNonNull(R);
        Object[] postA = ArrayQueueADT.toArray(deque);
        int postN = postA.length;
        assert Objects.equals(R, preA[preN - 1])
                && preN - 1 == postN
                && immutableSubarray(postA, 0, preA, 0, postN);
    }

    private static void checkPeek(ArrayQueueADT deque) {
        ArrayQueueADT.push(deque, 5);
        Object[] preA = ArrayQueueADT.toArray(deque);
        int preN = preA.length;
        assert preN > 0;
        Object R = ArrayQueueADT.peek(deque);
        Objects.requireNonNull(R);
        Object[] postA = ArrayQueueADT.toArray(deque);
        int postN = postA.length;
        assert Objects.equals(R, postA[postN - 1])
                && preN == postN
                && immutableSubarray(postA, 0, preA, 0, postN);
    }

    private static void checkPush(ArrayQueueADT deque) {
        Object[] preA = ArrayQueueADT.toArray(deque);
        int preN = preA.length;
        Object element = 5;
        Objects.requireNonNull(element);
        ArrayQueueADT.push(deque, element);
        Object[] postA = ArrayQueueADT.toArray(deque);
        int postN = postA.length;
        assert preN + 1 == postN
                && postA[0] == element
                && immutableSubarray(postA, 1, preA, 0, preN);
    }

    private static void checkToArray(ArrayQueueADT queue) {
        Object[] preA = ArrayQueueADT.toArray(queue);
        int preN = preA.length;
        Object[] R = extractArray(queue);
        Object[] postA = ArrayQueueADT.toArray(queue);
        int postN = postA.length;
        assert preN == postN && Arrays.equals(R, postA) && Arrays.equals(preA, postA);
    }

    private static void checkSize(ArrayQueueADT queue) {
        Object[] preA = ArrayQueueADT.toArray(queue);
        int preN = preA.length;
        int R = ArrayQueueADT.size(queue);
        Object[] postA = ArrayQueueADT.toArray(queue);
        int postN = postA.length;
        assert R == postN && postN == preN && Arrays.equals(preA, postA);
    }

    private static void checkClear(ArrayQueueADT queue) {
        ArrayQueueADT.clear(queue);
        assert ArrayQueueADT.size(queue) == 0;
    }

    private static void checkEnqueue(ArrayQueueADT queue) {
        Object[] preA = ArrayQueueADT.toArray(queue);
        int preN = preA.length;
        Object element = 5;
        Objects.requireNonNull(element);
        ArrayQueueADT.enqueue(queue, element);
        Object[] postA = ArrayQueueADT.toArray(queue);
        int postN = postA.length;
        assert preN + 1 == postN
                && postA[postN - 1] == element
                && immutableSubarray(postA, 0, preA, 0, preN);
    }

    private static void checkDequeue(ArrayQueueADT queue) {
        Object[] preA = ArrayQueueADT.toArray(queue);
        int preN = preA.length;
        assert preN > 0;
        Object R = ArrayQueueADT.dequeue(queue);
        Objects.requireNonNull(R);
        Object[] postA = ArrayQueueADT.toArray(queue);
        int postN = postA.length;
        assert Objects.equals(R, preA[0])
                && preN - 1 == postN
                && immutableSubarray(postA, 0, preA, 1, postN);
    }

    private static void checkElement(ArrayQueueADT queue) {
        Object[] preA = ArrayQueueADT.toArray(queue);
        int preN = preA.length;
        assert preN > 0;
        Object R = ArrayQueueADT.element(queue);
        Objects.requireNonNull(R);
        Object[] postA = ArrayQueueADT.toArray(queue);
        int postN = postA.length;
        assert Objects.equals(R, preA[0])
                && preN == postN
                && immutableSubarray(postA, 0, preA, 0, postN);
    }

    private static boolean immutableSubarray(Object[] a1, int s1, Object[] a2, int s2, int len) {
        assert s1 + len <= a1.length && s2 + len <= a2.length;
        for (int i = 0; i < len; i++) {
            if (!Objects.equals(a1[s1 + i], a2[s2 + i])) {
                return false;
            }
        }
        return true;
    }
}

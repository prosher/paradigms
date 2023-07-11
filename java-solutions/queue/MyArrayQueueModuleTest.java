package queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MyArrayQueueModuleTest {
    public static void main(String[] args) {
        assert ArrayQueueModule.isEmpty();
        checkContractsDeque();
        ArrayQueueModule.clear();
        fill("d_");
        System.out.println(Arrays.toString(ArrayQueueModule.toArray()));
        dump();
    }

    public static void fill(String prefix) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue(prefix + i);
        }
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.dequeue());
        }
    }

    public static void fillDeque(String prefix) {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.push(prefix + i);
        }
    }

    public static void dumpDeque() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.remove());
        }
    }

    private static void checkContracts() {
        checkSize();
        checkClear();
        checkToArray();
        checkEnqueue();
        checkElement();
        checkDequeue();
    }

    private static void checkContractsDeque() {
        checkContracts();
        checkPush();
        checkPeek();
        checkRemove();
    }

    private static Object[] extractArray() {
        List<Object> result = new ArrayList<>();
        while (!ArrayQueueModule.isEmpty()) {
            Object element = ArrayQueueModule.dequeue();
            assert element != null;
            result.add(element);
        }
        return result.toArray();
    }

    private static void checkRemove() {
        Object[] preA = ArrayQueueModule.toArray();
        int preN = preA.length;
        assert preN > 0;
        Object R = ArrayQueueModule.remove();
        Objects.requireNonNull(R);
        Object[] postA = ArrayQueueModule.toArray();
        int postN = postA.length;
        assert Objects.equals(R, preA[preN - 1])
                && preN - 1 == postN
                && immutableSubarray(postA, 0, preA, 0, postN);
    }

    private static void checkPeek() {
        ArrayQueueModule.push(5);
        Object[] preA = ArrayQueueModule.toArray();
        int preN = preA.length;
        assert preN > 0;
        Object R = ArrayQueueModule.peek();
        Objects.requireNonNull(R);
        Object[] postA = ArrayQueueModule.toArray();
        int postN = postA.length;
        assert Objects.equals(R, postA[postN - 1])
                && preN == postN
                && immutableSubarray(postA, 0, preA, 0, postN);
    }

    private static void checkPush() {
        Object[] preA = ArrayQueueModule.toArray();
        int preN = preA.length;
        Object element = 5;
        Objects.requireNonNull(element);
        ArrayQueueModule.push(element);
        Object[] postA = ArrayQueueModule.toArray();
        int postN = postA.length;
        assert preN + 1 == postN
                && postA[0] == element
                && immutableSubarray(postA, 1, preA, 0, preN);
    }

    private static void checkToArray() {
        Object[] preA = ArrayQueueModule.toArray();
        int preN = preA.length;
        Object[] R = extractArray();
        Object[] postA = ArrayQueueModule.toArray();
        int postN = postA.length;
        assert preN == postN && Arrays.equals(R, postA) && Arrays.equals(preA, postA);
    }

    private static void checkSize() {
        Object[] preA = ArrayQueueModule.toArray();
        int preN = preA.length;
        int R = ArrayQueueModule.size();
        Object[] postA = ArrayQueueModule.toArray();
        int postN = postA.length;
        assert R == postN && postN == preN && Arrays.equals(preA, postA);
    }

    private static void checkClear() {
        ArrayQueueModule.clear();
        assert ArrayQueueModule.size() == 0;
    }

    private static void checkEnqueue() {
        Object[] preA = ArrayQueueModule.toArray();
        int preN = preA.length;
        Object element = 5;
        Objects.requireNonNull(element);
        ArrayQueueModule.enqueue(element);
        Object[] postA = ArrayQueueModule.toArray();
        int postN = postA.length;
        assert preN + 1 == postN
                && postA[postN - 1] == element
                && immutableSubarray(postA, 0, preA, 0, preN);
    }

    private static void checkDequeue() {
        Object[] preA = ArrayQueueModule.toArray();
        int preN = preA.length;
        assert preN > 0;
        Object R = ArrayQueueModule.dequeue();
        Objects.requireNonNull(R);
        Object[] postA = ArrayQueueModule.toArray();
        int postN = postA.length;
        assert Objects.equals(R, preA[0])
                && preN - 1 == postN
                && immutableSubarray(postA, 0, preA, 1, postN);
    }

    private static void checkElement() {
        Object[] preA = ArrayQueueModule.toArray();
        int preN = preA.length;
        assert preN > 0;
        Object R = ArrayQueueModule.element();
        Objects.requireNonNull(R);
        Object[] postA = ArrayQueueModule.toArray();
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

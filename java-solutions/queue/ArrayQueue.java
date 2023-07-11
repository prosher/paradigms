package queue;

import java.util.Objects;

public class ArrayQueue extends AbstractQueue {
    private int head;
    private Object[] array;

    public ArrayQueue() {
        this.size = 0;
        this.head = 0;
        this.array = new Object[4];
    }

    @Override
    public boolean containsImpl(Object element) {
        return find(element) != size;
    }

    private int find(Object element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[realIdx(i)])) {
                return i;
            }
        }
        return size;
    }

    @Override
    protected boolean removeFirstOccurrenceImpl(Object element) {
        int idx = find(element);
        for (int i = idx; i < size - 1; i++) {
            array[realIdx(i)] = array[realIdx(i + 1)];
        }
        return idx != size;
    }

    private int realIdx(int i) {
        return (head + i) % array.length;
    }

    @Override
    protected void fillArray(Object[] array) {
        if (head + size <= this.array.length) {
            System.arraycopy(this.array, head, array, 0, size);
        } else {
            System.arraycopy(this.array, head, array, 0, this.array.length - head);
            System.arraycopy(this.array, 0, array, this.array.length - head, head + size - this.array.length);
        }
    }

    public void push(Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(size + 1);
        head = (head - 1 + array.length) % array.length;
        array[head] = element;
        size++;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Queue is empty");
        }
        return array[realIdx(size - 1)];
    }

    public Object remove() {
        Object result = peek();
        array[realIdx(size - 1)] = null;
        size--;
        return result;
    }

    protected void enqueueTail(Object element) {
        ensureCapacity(size + 1);
        array[realIdx(size)] = element;
    }

    private void ensureCapacity(int capacity) {
        if (array.length < capacity) {
            Object[] newArray = new Object[2 * capacity];
            System.arraycopy(array, head, newArray, 0, array.length - head);
            System.arraycopy(array, 0, newArray, array.length - head, head + size - array.length);
            array = newArray;
            head = 0;
        }
    }

    @Override
    protected Object getHeadElement() {
        return array[head];
    }

    @Override
    protected void dequeueHead() {
        array[head] = null;
        head = realIdx(1);
    }

    @Override
    protected void clearAllData() {
        array = new Object[4];
        head = 0;
    }
}

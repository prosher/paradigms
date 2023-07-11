package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;

    @Override
    public boolean contains(Object element) {
        Objects.requireNonNull(element);
        return !isEmpty() && containsImpl(element);
    }

    protected abstract boolean containsImpl(Object element);

    @Override
    public boolean removeFirstOccurrence(Object element) {
        Objects.requireNonNull(element);
        boolean result = !isEmpty() && removeFirstOccurrenceImpl(element);
        size = result ? size - 1 : size;
        return result;
    }

    protected abstract boolean removeFirstOccurrenceImpl(Object element);

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        fillArray(result);
        return result;
    }

    protected abstract void fillArray(Object[] array);

    @Override
    public void enqueue(Object element) {
        Objects.requireNonNull(element);
        enqueueTail(element);
        size++;
    }

    protected abstract void enqueueTail(Object element);

    @Override
    public Object element() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return getHeadElement();
    }

    protected abstract Object getHeadElement();

    @Override
    public Object dequeue() {
        Object result = element();
        dequeueHead();
        size--;
        return result;
    }

    protected abstract void dequeueHead();

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        clearAllData();
        size = 0;
    }

    protected abstract void clearAllData();
}

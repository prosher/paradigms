package queue;

public class LinkedQueue extends AbstractQueue {
    private Node tail;
    private Node head;

    @Override
    public boolean containsImpl(Object element) {
        return findPrev(element) != null;
    }

    private Node findPrev(Object element) {
        if (element.equals(head.value)) {
            return tail;
        }
        Node current = head;
        for (int i = 0; i < size - 1; i++) {
            if (element.equals(current.next.value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    protected boolean removeFirstOccurrenceImpl(Object element) {
        Node prevNode = findPrev(element);
        if (prevNode != null) {
            if (equalsTail(prevNode)) {
                dequeueHead();
            } else if (equalsTail(prevNode.next)) {
                prevNode.next = null;
                tail = prevNode;
            } else {
                prevNode.next = prevNode.next.next;
            }
            return true;
        }
        return false;
    }

    private boolean equalsTail(Node node) {
        return node.next == null;
    }

    @Override
    protected void fillArray(Object[] array) {
        Node current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.value;
            current = current.next;
        }
    }

    protected void enqueueTail(Object element) {
        Node elementNode = new Node(element, null);
        if (tail == null) {
            tail = elementNode;
            head = tail;
        } else {
            tail.next = elementNode;
            tail = tail.next;
        }
    }

    @Override
    protected Object getHeadElement() {
        return head.value;
    }

    @Override
    protected void dequeueHead() {
        head = head.next;
        if (head == null) {
            tail = null;
        }
    }

    @Override
    protected void clearAllData() {
        head = null;
        tail = null;
    }

    private static class Node {
        private final Object value;
        private Node next;

        public Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" + "value=" + value + ", next=" + next + '}';
        }
    }
}

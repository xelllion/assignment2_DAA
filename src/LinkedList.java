public class LinkedList {
    private Node head;
    private int size;
    private long accessCount;

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public LinkedList() {
        head = null;
        size = 0;
        accessCount = 0;
    }

    public int size() {
        return size;
    }

    public void add(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;
        }

        size++;
    }

    public void add(int index, int value) {
        checkPositionIndex(index);

        Node newNode = new Node(value);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            newNode.next = current.next;
            current.next = newNode;
        }

        size++;
    }

    public int remove(int index) {
        checkElementIndex(index);

        if (index == 0) {
            int removedValue = head.value;
            head = head.next;
            size--;
            return removedValue;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        int removedValue = current.next.value;
        current.next = current.next.next;
        size--;

        return removedValue;
    }

    public int get(int index) {
        checkElementIndex(index);

        Node current = head;
        accessCount++;

        for (int i = 0; i < index; i++) {
            current = current.next;
            accessCount++;
        }

        return current.value;
    }

    public long getAccessCount() {
        return accessCount;
    }

    public void resetAccessCount() {
        accessCount = 0;
    }

    public boolean contains(int value) {
        Node current = head;

        while (current != null) {
            if (current.value == value) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // construct an empty deque
    private Node head = null;
    private Node tail = null;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private int size;

    public Deque() {
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Arguments are null");
        }
        size++;
        Node holdPrev = head;
        head = new Node();
        head.item = item;
        head.next = holdPrev;
        head.prev = null;
        if (holdPrev != null) {
            holdPrev.prev = head;
        }
        else {
            tail = head;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Arguments are null");
        }
        size++;
        Node holdPrev = tail;
        tail = new Node();
        tail.item = item;
        if (holdPrev != null) {
            holdPrev.next = tail;
        }
        else {
            head = tail;
        }
        tail.prev = holdPrev;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Queue Empty");
        }
        Item item = head.item;
        head = head.next;
	size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Queue Empty");
        }
        Item item = tail.item;
        tail = tail.prev;
	size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();/* not supported */
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("Queue Empty");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        dq.addFirst(1);
        dq.addLast(2);
        dq.addLast(3);
        for (Integer i : dq)
            System.out.println(i);
        Iterator<Integer> i = dq.iterator();
    }
}

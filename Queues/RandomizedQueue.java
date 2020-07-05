import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // construct an empty randomized queue
    private Item[] q;
    private int capacity;
    private int size;

    public RandomizedQueue() {
        capacity = 2;
        q = (Item[]) new Object[capacity];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (size == capacity) {
            resizeArray(2 * capacity);
        }
        q[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(size);
        Item item = q[randomIndex];
        q[randomIndex] = q[size - 1];
	q[size - 1] = null;
        size--;
        if (size > 0 && size == capacity / 4) resizeArray(capacity / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(size);
        return q[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        int iterCount = 0;
        boolean[] checkIndex = new boolean[capacity];

        public boolean hasNext() {
            return iterCount != size || size == 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();/* not supported */
        }

        public Item next() {
            if (size == 0 || iterCount == size) {
                throw new NoSuchElementException("Queue Empty");
            }
            int randomIndex = StdRandom.uniform(size);
            Item item = q[randomIndex];
            while (item == null || checkIndex[randomIndex] == true) {
                randomIndex = StdRandom.uniform(size);
                item = q[randomIndex];
            }
            checkIndex[randomIndex] = true;
            iterCount++;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> newQ = new RandomizedQueue<Integer>();
        newQ.enqueue(42);
        newQ.enqueue(43);
        newQ.enqueue(44);
        newQ.enqueue(45);
        newQ.enqueue(46);
        //System.out.println(newQ.sample());
        System.out.println(newQ.dequeue());
        System.out.println(newQ.dequeue());
        System.out.println(newQ.dequeue());
        for (Integer i : newQ)
            System.out.println(i);
        Iterator<Integer> i = newQ.iterator();
    }

    private void resizeArray(int newCapacity) {
        Item[] newArray = (Item[]) new Object[newCapacity];
        int newSize = 0;
        for (int i = 0; i < capacity; ++i) {
            if (q[i] != null) newArray[newSize++] = q[i];
        }
        q = newArray;
        capacity = newCapacity;
    }

}

package breakout;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;


public class Vector<E> extends AbstractList<E> implements List<E> {
    protected Object[] elementData;
    protected int elementCount;
    protected int capacityIncrement;
    
    public Vector(int initialCapacity, int capacityIncrement) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }
    
    public Vector(int initialCapacity) {
        this(initialCapacity, 0);
    }
    
    public Vector() {
        this(10);
    }
    
    @SuppressWarnings("unchecked")
	E elementData(int index) {
        return (E) elementData[index];
    }
    
	public E get(int index) {
		if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);

        return elementData(index);
	}
	
	 public synchronized int size() {
        return elementCount;
    }
	
	public synchronized boolean add(E e) {
        ensureCapacityHelper(elementCount + 1);
        elementData[elementCount++] = e;
        return true;
    }
	
	public synchronized boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            if (index >= elementCount) {
                throw new ArrayIndexOutOfBoundsException(index + " >= " +
                                                         elementCount);
            }
            else if (index < 0) {
                throw new ArrayIndexOutOfBoundsException(index);
            }
            int j = elementCount - index - 1;
            if (j > 0) {
                System.arraycopy(elementData, index + 1, elementData, index, j);
            }
            elementCount--;
            elementData[elementCount] = null;
            return true;
        }
        return false;
    }
	
	public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }
	
	private void ensureCapacityHelper(int minCapacity) {
        int oldCapacity = elementData.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = (capacityIncrement > 0) ?
                (oldCapacity + capacityIncrement) : (oldCapacity * 2);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }
    
}

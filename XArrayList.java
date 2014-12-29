import java.util.*;
/**
 * The class is for dynamical array
 */
public class XArrayList<E> implements Iterable {
	private E[] eles;
	private int capacity = 1;
	private int top = 0;
	public XArrayList() {
		eles = (E[]) new Object[capacity];
	}
	//insert an element
	public void add(E e) {
		if(top == capacity) {
			reSize(capacity*2);
		}
		eles[top++] = e;
	}
	//remove an element
	public void remove(E e) {
		int index = search(e);
		if(index == -1) return;
		remove(index);
		if(top < capacity / 4) {
			reSize(capacity/2);
		}
	}
	//remove an element from a specific index
	public void remove(int index) {
		if(index < 0 || index >= top) {
			throw new IndexOutOfBoundsException();
		}
		for(int i = index; i < top - 1; i++) {
			eles[i] = eles[i+1];
		}
		top--;
	}
	//get an element from a specific index
	public E get(int index) {
		if(index < 0 || index >= top) return null;
		return eles[index];
	}
	//set an element in a specific index
	public void set(int index, E e) {
		if(index < 0 || index >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		eles[index] = e;
	}
	//search the position of an element in the array
	public int search(E e) {
		for(int i = 0; i < top; i++) {
			if(eles[i].equals(e)) return i;
		}
		return -1;
	}
	//return size
	public int size() {
		return top;
	}
	//change the size of the interal array
	private void reSize(int newsize) {
		E[] newarray = (E[]) new Object[newsize];
		for(int i = 0; i < top; i++) {
			newarray[i] = eles[i];
		}
		eles = newarray;
		capacity = newsize;
	}
	//iterator
	public Iterator<E> iterator() {
		return new XArrayIterator();
	}
	class XArrayIterator implements Iterator<E> {
		private int index = 0;
		public boolean hasNext() {
			return index < top;
		}
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			return eles[index++];
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	public static void main(String[] args) {
		XArrayList<String> a = new XArrayList<String>();
		a.add("hello");
		a.add("word");
		a.add("c#");
		a.add("java");
		a.add("linux");
		a.add("perl");
		a.set(3,"Ada");
		print(a);

	}
	public static void print(XArrayList<String> a) {
		Iterator<String> it = a.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		} 
	}
}
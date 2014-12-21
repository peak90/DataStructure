import java.util.*;


/**
 * This file implements queue by circular array
 * use a variable num to trace the element number in the queue
 */


public class XArrayQueue<E> {
	private E[] eles;
	private int head;
	private int tail; //next position for the element
	private int num; //current element number in the queue!!!
	public XArrayQueue() {
		eles = (E[]) new Object[1];
		head = 0;
		tail = 0;
		num = 0;
	}
	//add an element
	//if full, resize the queue by double
	public void add(E e) {
		//full, resize
		if(num == eles.length) {
			resize(eles.length * 2);
		}
		eles[tail] = e;
		tail = (tail + 1) % eles.length;
		num++;
 	}
 	//poll an element from the head
	public E poll() {
		if(isEmpty()) return null;
		E e = eles[head];
		head = (head + 1) % eles.length;
		num--;
		if(num < eles.length / 4) {
			resize(eles.length / 2);
		}
		return e;
	}
	//peek the top element
	public E peek() {
		if(isEmpty()) return null;
		return eles[head];
	}
	public boolean isEmpty() {
		return num == 0;
	}
	public int size() {
		return num;
	}
	//resize the queue
	//create a new array
	private void resize(int newsize) {
		E[] neweles = (E[]) new Object[newsize];
		for(int i = 0; i < num; i++) {
			neweles[i] = eles[(i+head) % eles.length];
		}
		eles = neweles;
		head = 0;
		tail = num;
	}
	//iterator
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int h = head;
			private int i = 0;
			public boolean hasNext() {
				return i < num;
			}
			public E next() {
				E e = eles[h];
				h = (h + 1) % eles.length;
				i++;
				return e;
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	public static void main(String[] args) {

	}
}
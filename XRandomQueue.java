import java.util.*;

/**
 * This file implements random queue using 
 * dequeue random element
 * by Dianshi, 12/21/2014
 */

class EmptyQueueException extends RuntimeException {}

public class XRandomQueue<E> implements Iterable{
	private E[] eles;
	private int num;
	private int head;
	private int tail;
	public XRandomQueue() {
		num = 0;
		head = 0;
		tail = 0;
		eles = (E[]) new Object[1]; //initial size is 1
	}
	//add an element to the tail
	public void add(E e) {
		//queue is full, resize by double
		if(num == eles.length) {
			resize(eles.length*2);
		}
		eles[tail] = e;
		tail = (tail + 1) % eles.length;
		num++;
	}
	//poll an element from the head
	public E poll() {
		if(isEmpty()) throw new EmptyQueueException();
		Random r = new Random();
		int index = r.nextInt(num);
		swap(eles,head+index,tail-1);
		E e = eles[tail-1];
		eles[tail-1] = null;
		tail = (tail - 1 + eles.length) % eles.length;
		num--;
		if(num > 0 && num == eles.length / 4) {
			resize(eles.length / 2);
		}
		return e;
	}
	//peek the element at the head
	public E peek() {
		if(isEmpty()) throw new EmptyQueueException();
		return eles[head];
	}
	public boolean isEmpty() {
		return num == 0;
	}
	public int size() {
		return num;
	}
	public Iterator<E> iterator() {
		return new RandomQueueIterator();
	}
	class RandomQueueIterator implements Iterator<E> {
		private E[] elescopy;
		private int i;
		public RandomQueueIterator() {
			i = 0;
			elescopy = Arrays.copyOf(eles,num);//copy
			randomShuffle(elescopy);
		}
		public boolean hasNext() {
			return i < elescopy.length;
		}	
		public E next() {
			return elescopy[i++];
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		//random shuffle the array
		private void randomShuffle(E[] array) {
			Random r = new Random();
			for(int i = 1; i < array.length; i++) {
				int index = r.nextInt(i);
				XRandomQueue.this.swap(elescopy,index,i);
			}
		}
	}
	//swap two elements in the queue
	private void swap(E[] T, int i, int j) {
		E tmp = T[i];
		T[i] = T[j];
		T[j] = tmp;
	}
	//resize the queue
	private void resize(int newsize) {
		E[] neweles = (E[]) new Object[newsize];
		for(int i = 0; i < num; i++) {
			neweles[i] = eles[(i+head) % eles.length];
		}
		eles = neweles;
		head = 0;
		tail = num;
	}

	public static void main(String[] args) {
		XRandomQueue<Integer>  q = new XRandomQueue<Integer>();
		for(int i = 0; i < 100; i++) {
			q.add(i);
		}
		while(! q.isEmpty()) {
			System.out.println(q.poll());
		}
	}
}
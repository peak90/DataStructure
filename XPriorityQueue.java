import java.util.*;
/**
 * This file implements the priority Queue based on heap
 * 12/27/2014 By Dianshi
 */
class NotPositiveHeapSizeException extends RuntimeException{}
class EmptyHeapException extends RuntimeException {}

public class XPriorityQueue<E> {
	private Comparator<E> comp;
	private int capacity;
	private E[] objs;
	private int top;
	public XPriorityQueue(int size, Comparator<E> comp) {
		if(size <= 0) throw new NotPositiveHeapSizeException();
		capacity = size;
		this.comp = comp;
		objs = (E[]) new Object[size];
	}
	//add an element
	public void add(E e) {
		if(top == capacity) {
			resize(capacity * 2);
		}
		objs[top++] = e;
		heapifyUp(top-1);
	}
	private void heapifyUp(int index) {
		while(index > 0) {
			int parent = (index-1) / 2;
			if(comp.compare(objs[parent],objs[index]) > 0) {
				swap(objs,parent,index);
			} else {
				break;
			}
			index = parent;
		}
	}
	//poll an element
	public E poll() {
		if(isEmpty()) throw new EmptyHeapException();
		E e = objs[0];
		swap(objs,0,--top);
		heapifyDown(0);
		if(!isEmpty() && top == capacity / 4) {
			resize(capacity / 2);
		}
		return e;
	}
	private void heapifyDown(int index) {
		while(index < top) {
			int left = index*2+1;
			int right = index*2+2;
			int small = index;
			if(left < top && comp.compare(objs[left],objs[small]) < 0) {
				small = left;
			}
			if(right < top && comp.compare(objs[right],objs[small]) < 0) {
				small = right;
			}
			if(small == index) break;
			swap(objs,index,small);
			index = small;
		}
	}
	//peek the top element
	public E peek() {
		if(isEmpty()) throw new EmptyHeapException();
		return objs[0];
	}
	public int size() {
		return top;
	}
	public boolean isEmpty() {
		return size() == 0;
	}
	//resize the heap
	private void resize(int newsize) {
		E[] newobjs = (E[]) new Object[newsize];
		for(int i = 0; i < top; i++) {
			newobjs[i] = objs[i];
		}
		capacity = newsize;
		objs = newobjs;
	}
	//swap two elements in the array
	private void swap(E[] a, int i, int j) {
		E tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	public static void main(String[] args) {
		XPriorityQueue<String> q = new XPriorityQueue<String>(1,new Comparator<String>(){
			public int compare(String s1, String s2) {
				if(s1.length() == s2.length()) return 0;
				if(s1.length() < s2.length()) return -1;
				return 1;
			}
		});
		q.add("Ada");
		q.add("C#");
		q.add("C++");
		q.add("Matlab");
		q.add("Ruby");
		q.add("Python");
		q.add("Erlang");
		q.add("CX");
		q.add("B");
		while(!q.isEmpty()) {
			System.out.println(q.poll());
		}
	}
}
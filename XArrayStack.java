
import java.util.*;
/**
 * This file is the implementation of stack based on dynamical array
 */

public class XArrayStack<E> implements Iterable {
	private E[] eles;
	private int top  = 0;
	public XArrayStack() {
		eles = (E[]) new Object[1];
	}
	//push an element
	public void push(E e) {
		if(top == eles.length) {
			resize(eles.length * 2);
		}
		eles[top++] = e;
	}
	//pop an element
	public E pop() {
		if(isEmpty()) throw new EmptyStackException();
		E e = eles[--top];
		if(top >= 0 && top == eles.length / 4) {
			resize(eles.length / 2);
		}
		return e;
	}
	//resize the inner dynamical array
	private void resize(int newsize) {
		E[] neweles = (E[]) new Object[newsize];
		for(int i = 0; i < top; i++) {
			neweles[i] = eles[i];
		}
		eles = neweles;
	}
	//peek the top element
	public E peek() {
		if(isEmpty()) throw new EmptyStackException();
		return eles[top-1];
	}
	public boolean isEmpty() {
		return top == 0;
	}
	public int size() {
		return top;
	}
	//iterator
	public Iterator<E> iterator() {
		return new Iterator<E>(){
			private int index = 0;
			public boolean hasNext() {
				return index < top;
			}
			public E next() {
				return eles[index++];
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	public static void main(String[] args) {
	}	
}
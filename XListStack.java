import java.util.*;

/**
 * This file implements stack by double linked list
 */

public class XListStack<E> {
	class Node<E> {
		private E ele;
		private Node<E> next;
		private Node<E> pre; 
		public Node(E e) {
			ele = e;
			pre = null;
			next = null;
		}
	}
	private Node<E> head = null;
	//push an element in stack
	public void push(E e) {
		Node<E> n = new Node<E>(e);
		n.next = head;
		if(head != null) head.pre = n;
		head = n;
	}
	//pop an element from stack
	public E pop() {
		if(head == null) throw new EmptyStackException();
		Node<E> next = head.next;
		head.next = null;
		E e = head.ele;
		head = next;
		if(head != null) head.pre = null;
		return e;
	}	
	//peek the top element in the stack
	public E peek() {
		if(head == null) throw new EmptyStackException();
		return head.ele;
	}
	//check whether stack is empty
	public boolean isEmpty() {
		return head == null;
	}
	//Iterator
	public Iterator<E> iterator() {
		return new ListStackIterator();
	}
	class ListStackIterator implements Iterator<E> {
		private Node<E> h;
		public ListStackIterator() {
			h = head;
			while(h.next != null) {
				h = h.next;
			}
		}
		public boolean hasNext() {
			return h != null;
		}
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			E e = h.ele;
			h = h.pre;
			return e;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) {
	}
}
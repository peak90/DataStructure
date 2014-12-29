import java.util.*;

/**
 * This file implements Queue by linked list
 */

public class XListQueue<E> implements Iterable {
	class Node<E> {
		private E ele;
		private Node<E> next;
		public Node(E e) {
			ele = e;
			next = null;
		}
	}
	private Node<E> head = null;
	private Node<E> tail = null;
	private int num = 0;
	//add at tail
	public void add(E e) {
		Node<E> node = new Node<E>(e);
		if(head == null) {
			head = tail = node;
		} else {
			tail.next = node;
			tail = tail.next;
		}
		num++;
	}
	//poll at head
	public E poll() {
		if(head == null) return null;
		E e = head.ele;
		head = head.next;
		num--;
		return e;
	}
	//peak the top element
	public E peek() {
		if(head == null) return null;
		return head.ele;
	}
	public boolean isEmpty() {
		return num == 0;
	}
	public int size() {
		return num;
	}
	//iterator
	public Iterator<E> iterator() {
		return new Iterator<E>(){
			private Node<E> h = head;
			public boolean hasNext() {
				return h != null;
			}
			public E next() {
				if(!hasNext()) throw new NoSuchElementException();
				E e = h.ele;
				h = h.next;
				return e;
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	public static void main(String[] args) {
		XListQueue<String> q = new XListQueue<String>();
		for(int i = 0; i < 100; i++) {
			q.add(i+"D");
		}
		Iterator<String> it = q.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
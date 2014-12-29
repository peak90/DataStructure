import java.util.*;

/**
 * implemente a double linked list in java
 * 
 */
public class XLinkedList<E> implements Iterable {
	class Node<E> {
		private E ele;
		private Node<E> pre;
		private Node<E> next;
		public Node(E e) {
			ele = e;
			pre = null;
			next = null;
		}
	}
	private Node<E> head = null;
	private Node<E> tail = null;

	//add an element
	public void add(E e) {
		Node<E> n = new Node<E>(e);
		if(head == null) {
			head = tail = n;
		} else {
			tail.next = n;
			n.pre = tail;
			tail = n;
		}
	}
	//get an element
	public E get(int index) {
		Node<E> n = search(index);
		return n == null ? null : n.ele;
	}
	//remove an element
	public void remove(int index) {
		Node<E> n = search(index);
		if(null == n) throw new IndexOutOfBoundsException();
		remove(n);
	}
	//remove an element
	public void remove(E e) {
		Node<E> n = search(e);
		if(null == n) throw new IndexOutOfBoundsException();
		remove(n);
	}
	//remove an element
	private void remove(Node<E> node) {
		if(node == head) {
			head = head.next;
			if(head != null) head.pre = null;
		} else if(node == tail) {
			tail = tail.pre;
			tail.next = null;
		} else {
			node.pre.next = node.next;
			node.next.pre = node.pre;
		}
	}
	private Node<E> search(int index) {
		Node<E> h = head;
		int i = 0;
		while(h != null && i <= index) {
			i++;
			h = h.next;
		}
		return h;
	}
	private Node<E> search(E e) {
		Node<E> h = head;
		while(h != null) {
			if(h.ele.equals(e)) return h;
			h = h.next;
		}
		return h;
	}
	//iterator
	public Iterator<E> iterator() {
		return new Iterator<E>(){
			Node<E> p = head;
			public boolean hasNext() {
				return p != null;
			}
			public E next() {
				if(!hasNext()) throw new NoSuchElementException();
				E e = p.ele;
				p = p.next;
				return e;
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	public static void main(String[] args) {
		XLinkedList<String> ls = new XLinkedList<String>();
		ls.add("hello");
		ls.add("word");
		ls.add("xx");
		ls.remove("hello");
		Iterator<String> it = ls.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
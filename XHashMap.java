import java.util.*;

/**
 * This file implements hashmap with chaining to resolve collision
 * use double linked list
 * 12/29/2014 by Dianshi
 */
public class XHashMap<K,V>{
	private int M; //slot number
	private int N = 0; //elements number
	private DoubleLinkedList<K,V>[] slots;
	public XHashMap() {
		M = 10;//intialized as 10
		slots = (DoubleLinkedList<K,V>[]) new DoubleLinkedList[M];
		for(int i = 0; i < M; i++) {
			slots[i] = new DoubleLinkedList<K,V>();
		}
	}
	//put a pair
	public void put(K key, V val) {
		if(ldfactor() > 5) {
			resize(M * 2);
		}
		int index = hash(key.hashCode());
		//the key does not exists
		if(! slots[index].containsKey(key)) {
			N++;
		}
		slots[index].add(key,val);
	}
	public V get(K key) {
		int index = hash(key.hashCode());
		return slots[index].get(key);
	}
	//remove a pair
	public void remove(K key) {
		int index = hash(key.hashCode());
		if(slots[index].containsKey(key)) {
			slots[index].delete(key);
			N--;
		}
		if(N > 0 && ldfactor() < 0.25) {
			resize(M / 2);
		}
	}
	public Iterable<K> keySet() {
		Queue<K> queue = new LinkedList<K>();
		for(int i = 0; i < slots.length; i++) {
			for(K k : slots[i].keys()) {
				queue.add(k);
			}
		}
		return queue;
	}
	private double ldfactor() {
		return (double) N / (double) M;
	}
	//resize the hashmap
	private void resize(int newsize) {
		DoubleLinkedList<K,V>[] newslots = (DoubleLinkedList<K,V>[]) new DoubleLinkedList[newsize];
		for(int i = 0; i < newsize; i++) {
			newslots[i] = new DoubleLinkedList<K,V>();
		}
		M = newsize;
		for(int i = 0; i < slots.length; i++) {
			for(K key: slots[i].keys()) {
				int index = hash(key.hashCode());
				newslots[index].add(key,slots[i].get(key));
			}
		}
		slots = newslots;
	}
	//compute slot index
	private int hash(int hashcode) {
		return (hashcode & 0x7fffffff) % M;
	}
	public static void main(String[] args) {
		XHashMap<String,String> map = new XHashMap<String,String>();
		map.put("C++","Unix");
		map.put("C#","Microsoft");
		map.put("Python","Youtube");
		map.put("Perl","Amazon");
		map.put("Twitter","List iterator");
		map.put("Google","DAG");
		map.put("Facebook","PrintTree");
		map.put("Matlab","MA");
		for(String key : map.keySet()) {
			System.out.println(key+" "+map.get(key));
		}
	}
}

/**
 * Double linked list class
 */
class DoubleLinkedList<K,V> {
	class Node<K,V> {
		private K key;
		private V val;
		private Node<K,V> pre;
		private Node<K,V> next;
		public Node(K k, V v) {
			key = k;
			val = v;
			pre = null;
			next = null;
		}
	}
	private Node<K,V> head = null;
	private Node<K,V> tail = null;
	//add a pair
	public void add(K key,V val) {
		if(head == null) {
			head = tail = new Node<K,V>(key,val);
		} else {
			Node<K,V> node = search(key);
			//node with key exists
			if(node != null) {
				node.val = val; return;
			}
			//add a new node at tail
			Node<K,V> newnode = new Node<K,V>(key,val);
			newnode.pre = tail;
			tail.next = newnode;
			tail = tail.next;
		}
	}
	//delete a pair if exists
	public void delete(K key) {
		Node<K,V> node = search(key);
		if(node == null) return; //key does not exist
		if(node == head) {
			head = head.next;
			if(head != null) head.pre = null;
		} 
		else if(node == tail) {
			tail = tail.pre;
			tail.next = null;
		} else {
			node.pre.next = node.next;
			node.next.pre = node.pre;
		}
	}
	//get a key in the double linked list
	public V get(K key) {
		Node<K,V> node = search(key);
		return node == null ? null : node.val;
	}
	//keys
	public Iterable<K> keys() {
		Queue<K> q = new LinkedList<K>();
		Node<K,V> p = head;
		while(p != null) {
			q.add(p.key);
			p = p.next;
		}
		return q;
	}
	private Node<K,V> search(K key) {
		Node<K,V> p = head;
		while(p != null) {
			if(p.key.equals(key)) break;
			p = p.next;
		}
		return p;
	}
	public boolean containsKey(K key) {
		return search(key) != null;
	}
}

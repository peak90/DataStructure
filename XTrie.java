import java.util.*;

/**
*This file implements Trie data structure based on array
*12/22/2014 By Dianshi
**/

public class XTrie<V> {
	class Node<V> {
		private char ch;
		private boolean isdelete;
		private V val;
		private Node<V>[] nodes;
		private int freq;
		private static final int BRANCH = 256;
		public Node(char c, V v) {
			ch = c;
			val = v;
			isdelete = false;
			freq = 0;
			nodes =(Node<V>[]) new Node[BRANCH];
		}
	}
	private Node root;
	public XTrie() {
		root = new Node(' ',null);
	}
	//insert a pair in the trie
	public void insert(String key, V val) {
		if(key == null || key.length() == 0) {
			System.out.println("Key cannot be null or empty");
			return;
		}
		char[] chs = key.toCharArray();
		Node p = root;
		for(int i = 0; i < chs.length; i++) {
			if(p.nodes[chs[i]] == null) {
				p.nodes[chs[i]] = new Node(chs[i],null);
			}
			p = p.nodes[chs[i]];
			p.freq++;
		}
		p.val = val;
	}
	//get an element from trie
	public V get(String key) {
		if(key == null || key.length() == 0) {
			System.out.println("Key cannot be null or empty");
			return null;
		}
		Node p = root;
		char[] chs = key.toCharArray();
		for(int i = 0; i < chs.length; i++) {
			if(p.nodes[chs[i]] == null) return null;
			p = p.nodes[chs[i]];
		}
		return p.isdelete ? null : (V)p.val;
	}
	//delete an element from the trie
	public void delete(String key) {
		if(key == null || key.length() == 0) {
			System.out.println("Key cannot be null or empty");
			return;
		}
		if(get(key) == null) {
			System.out.println("Key "+key+" does not exist in the trie");
			return;
		}
		Node p = root;
		char[] chs = key.toCharArray();
		for(int i = 0; i < chs.length; i++) {
			p.nodes[chs[i]].freq--;
			Node tmp = p.nodes[chs[i]];
			//delete all node below this node
			if(tmp.freq == 0) {
				p.nodes[chs[i]] = null;
				return;
			}
			p = tmp;
		}
		p.isdelete = true;
	}
	//check whether a prefix is in the trie
	public boolean isPrefix(String key) {
		if(key == null || key.length() == 0) return false;
		char[] chs = key.toCharArray();
		Node p = root;
		for(int i = 0; i < chs.length; i++) {
			if(p.nodes[chs[i]] == null) return false;
			p = p.nodes[chs[i]];
		}
		return true;
	}
	public static void main(String[] args) {
		
	}
}

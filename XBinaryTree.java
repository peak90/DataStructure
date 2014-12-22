import java.util.*;


/**
 * This file implements a binary tree
 */

/**
 * Assume that parent pointer exists
 * do not use parent pointer in iterator
 */
public class XBinaryTree<K,V> {
	class Node {
		private K key;
		private V val;
		private Node left;
		private Node right;
		private Node parent;
		public Node(K k, V v) {
			key = k;
			val = v;
			left = null;
			right = null;
			parent = null;
		}
	}

	private Node root = null;
	private Comparator<K> comp;

	public XBinaryTree(Comparator<K> comp) {
		this.comp = comp;
	}

	//insert an element using iteration
	public void insert(K k, V v) {
		Node n = new Node(k,v);
		if(null == root) {
			root = n;
		} else {
			Node p = root;
			Node q = null;
			while(p != null) {
				q = p;
				if(comp.compare(p.key,k) >= 0) {
					p = p.left;
				} else {
					p = p.right;
				}
			}
			if(comp.compare(q.key,k) >= 0) {
				q.left = n;
			} else {
				q.right = n;
			}
			n.parent = q;
		}
	}

	//insert an element recursively
	public void insertRec(K k, V v) {
		root = insert(root,k,v);
	}
	private Node insert(Node node, K k, V v) {
		if(node == null) {
			return new Node(k,v);
		} else if(comp.compare(node.key,k) >= 0) {
			node.left = insert(node.left,k,v);
		} else {
			node.right = insert(node.right,k,v);
		}
		return node;
	}

	//delete an element using iteration.
	//parent pointer is needed.
	public void delete(K k) {
		Node node = search(k);
		if(node == null) {
			System.out.println("Key "+k+" does not exist in the tree");
			return;
		}
		if(node.left == null) {
			replaceTree(node,node.right);
		} else if(node.right == null) {
			replaceTree(node,node.left);
		} else {
			Node suc = sucessor(node);
			if(suc != node.right) {
				replaceTree(suc,suc.right);
				suc.right = node.right;
				node.parent = suc;
			}
			replaceTree(node,suc);
			suc.left = node.left;
			node.left.parent = suc;
		}
	}
	//replace subtree u with subtree v
	private void replaceTree(Node u, Node v) {
		if(u == root) {
			root = v;
		} else if(u == u.parent.left) {
			u.parent.left = v;
		} else if(u == u.parent.right) {
			u.parent.right = v;
		}
		if(v != null) {
			v.parent = v;
		}
	}
 	
 	//delete an element recursively
 	//no parent pointer is needed.
 	
 	public void deleteRec(K k) {
 		root = delete(root,k);
 	}
 	private Node delete(Node n, K k) {
 		if(n == null) {
 			System.out.println("Key "+k+" does not exist in the tree");
 			return null;
 		}
 		else if(comp.compare(n.key,k) > 0) {
 			n.left = delete(n.left,k);
 		}
 		else if(comp.compare(n.key,k) < 0) {
 			n.right = delete(n.right,k);
 		} else {
 			if(n.right == null) {
 				n = n.left;
 			} else {
	 			Node p = n.right;
	 			while(p.left != null) p = p.left;
	 			n.key = p.key;
	 			n.val = p.val;
	 			n.right = delete(n.right,p.key);
 			}
 		}
 		return n;
 	}
 	/**
 	 * search an element in the tree
 	 */
	private Node search(K k) {
		Node p = root;
		while(p != null) {
			if(comp.compare(p.key,k) == 0) return p;
			else if(comp.compare(p.key,k) >= 0) {
				p = p.left;
			} else {
				p = p.right;
			}
		}
		return null;
	}
	//find predecessor
	private Node predecessor(Node n) {
		if(n == null) return null;
		Node p = n.left;
		if(n != null) {
			while(p.right != null) p = p.right;
			return p;
		} else {
			while(p.parent != null && p.parent.left == p) {
				p = p.left;
			}
			return p.parent;
		}
	}
	//find sucessor
	private Node sucessor(Node n) {
		if(n == null) return null;
		Node p = n.right;
		if(p != null) {
			while(p.left != null) {
				p = p.left;
			}
			return p;
		} else {
			while(p.parent != null && p.parent.right == p) {
				p = p.parent;
			}
			return p.parent;
		}
	}
	/**
	 * pre order iterator
	 */
	public Iterator<K> preiterator() {
		return new PreIterator();
	}
	class PreIterator implements Iterator<K> {
		private Stack<Node> stack;
		public PreIterator() {
			stack = new Stack<Node>();
			if(root != null) {
				stack.push(root);
			}
		}
		public boolean hasNext() {
			return !stack.isEmpty();
		}
		public K next() {
			if(! hasNext()) throw new NoSuchElementException();
			Node p = stack.pop();
			if(p.right != null) stack.push(p.right);
			if(p.left != null) stack.push(p.left);
			return p.key;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	/**
	 * in order iterator
	 */
	public Iterator<K> initerator() {
		return new InIterator();
	}
	class InIterator implements Iterator<K> {
		private Stack<Node> stack;
		private Node p;
		public InIterator() {
			stack = new Stack<Node>();
			p = root;
		}
		public boolean hasNext() {
			return p != null || !stack.isEmpty();
		}
		public K next() {
			if(! hasNext()) throw new NoSuchElementException();
			while(p != null) {
				stack.push(p);
				p = p.left;
			}
			p = stack.pop();
			K k = p.key;
			p = p.right;
			return k;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	/**
	 * post order iterator
	 */
	public Iterator<K> postiterator() {
		return new PostIterator();
	}
	class PostIterator implements Iterator<K> {
		private Stack<Node> stack;
		private Node p;
		private Node pre;
		public PostIterator() {
			stack = new Stack<Node>();
			if(null != root) {
				stack.push(root);
			}
			p = null;
			pre = null;
		}
		public boolean hasNext() {
			return !stack.isEmpty();
		}
		public K next() {
			if(! hasNext()) throw new NoSuchElementException();
			while(true) {
				p = stack.peek();
				if(p == null) return null;
				if((p.left == null && p.right == null) || 
				  (pre != null && (pre == p.left || pre == p.right))) {
					pre = p;
					stack.pop();
					return p.key;
				} else {
					if(p.right != null) stack.push(p.right);
					if(p.left != null) stack.push(p.left);
				}
			}
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	//preorder traversal
	public void preOrder() {
		preOrder(root);
		System.out.println();
	}
	private void preOrder(Node root) {
		if(root == null) return;
		System.out.print(root.key+" ");
		preOrder(root.left);
		preOrder(root.right);
	}

	//inorder traversal
	public void inOrder() {
		inOrder(root);
		System.out.println();
	}
	private void inOrder(Node root) {
		if(root == null) return;
		inOrder(root.left);
		System.out.print(root.key+" ");
		inOrder(root.right);
	}

	//post order traversal
	public void postOrder() {
		postOrder(root);
		System.out.println();
	}
	private void postOrder(Node root) {
		if(root == null) return;
		postOrder(root.left);
		postOrder(root.right);
		System.out.print(root.key+" ");
	}


	public static void main(String[] args) {

	}
}
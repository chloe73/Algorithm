package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1991_트리순회 {
	
	static class Node {
		char val;
		Node left;
		Node right;
		
		public Node(char val, Node left, Node right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
	static Node head = new Node('A', null, null);

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			
			char root = s.charAt(0);
			char left = s.charAt(1);
			char right = s.charAt(2);
			
			insert(head, root, left, right);
		}
		
		preOrder(head);
		System.out.println();
		
		inOrder(head);
		System.out.println();
		
		postOrder(head);
		System.out.println();
	}
	
	private static void insert(Node temp, char root, char left, char right) {
		if(temp.val == root) {
			temp.left = (left == '.' ? null : new Node(left,null,null));
			temp.right = (right == '.' ? null :new Node(right, null, null));
		}
		else {
			if(temp.left != null) insert(temp.left, root, left, right);
			if(temp.right != null) insert(temp.right, root, left, right);
		}
	}

	private static void preOrder(Node node) {
		if(node == null) return;
		
		System.out.print(node.val);
		preOrder(node.left);
		preOrder(node.right);
	}
	
	private static void inOrder(Node node) {
		if(node == null) return;
		
		inOrder(node.left);
		System.out.print(node.val);
		inOrder(node.right);
	}
	
	private static void postOrder(Node node) {
		if(node == null) return;
		
		postOrder(node.left);
		postOrder(node.right);
		System.out.print(node.val);
	}
}

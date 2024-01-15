package algo.SW_DX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_암호문_3_LinkedList {
	
	static int NODE_MAX = 5000;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static class Node {
		int data;
		Node next;
		
		public Node(int data) {
			this.data = data;
			this.next = null;
		}
	}
	static class LinkedList {
		
		Node head;
		
		Node tail;
		
		Node[] nodeArr;
		
		int nodeCnt;
		
		public LinkedList() {
			head = null;
			nodeArr = new Node[NODE_MAX];
			nodeCnt = 0;
		}
		
		// 새로운 node 생성
		Node getNewNode(int data) {
			nodeArr[nodeCnt] = new Node(data);
			return nodeArr[nodeCnt++];
		}
		
		// 앞에서 idx개 이후 nums들 추가하기
		// I(삽입) x, y, s : 앞에서부터 x번째 암호문 바로 다음에 y개의 암호문을 삽입한다.
		// s는 덧붙일 암호문들이다.[ ex) I 3 2 123152 487651 ]
		void insert(int idx, int[] nums) {
			int st = 0;
			if(idx == 0) {
				// 맨 앞에 추가되는 경우
				if(head != null) {
					// 먼저 한 개만 새로 추가하고 head 값 조정하기
					Node newNode = getNewNode(nums[0]);
					newNode.next = head;
					head = newNode;
				}
				else {
					head = getNewNode(nums[0]);
				}
				
				idx = 1;
				st = 1;
			}
			
			Node cur = head;
			
			// idx개 만큼 이동하기
			for(int i=1;i<idx;i++) {
				cur = cur.next;
			}
			
			// nums 추가하기
			for(int i=st;i<nums.length;i++) {
				Node newNode = getNewNode(nums[i]);
				newNode.next = cur.next;
				cur.next = newNode;
				cur = newNode;
			}
			
			if(cur.next == null) {
				tail = cur;
			}
		}
		
		// D(삭제) x, y : 앞에서부터 x번째 암호문 바로 다음부터 y개의 암호문을 삭제한다.[ ex) D 4 4 ]
		void delete(int idx, int cnt) {
			Node cur = head;
			
			// 맨 앞이 삭제되는 경우 -> head가 재조정 되어야 함.
			if(idx == 0) {
				for(int i=0;i<cnt;i++) {
					cur = cur.next;
				}
				head = cur;
				return;
			}
			
			// idx개 만큼 이동하기
			for(int i=1;i<idx;i++) {
				cur = cur.next;
			}
			
			Node anchor = cur; // 삭제되기 직전 위치 기억하기
			for(int i=0;i<cnt;i++) {
				cur = cur.next;
			}
			anchor.next = cur.next;

			if(anchor.next == null) {
				tail = anchor;
			}
		}
		
		// A(추가) y, s : 암호문 뭉치 맨 뒤에 y개의 암호문을 덧붙인다. s는 덧붙일 암호문들이다. [ ex) A 2 421257 796813 ]
		// 맨 뒤에 data 추가
		void add(int data) {
			Node cur = tail;
			Node newNode = getNewNode(data);
			cur.next = newNode;
			tail = newNode;
		}
		
		void print() throws Exception {
			int cnt = 10;
			Node cur = head;
			while(cnt-- > 0) {
				bw.write(cur.data+" ");
				cur = cur.next;
			}
		}
	}

	public static void main(String[] args) throws Exception{
		StringTokenizer st;
		
		for(int tc=1;tc<=10;tc++) {
			bw.write("#"+tc+" ");
			
			LinkedList list = new LinkedList();
			int N = Integer.parseInt(br.readLine());
			int[] initArr = new int[N];
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				initArr[i] = Integer.parseInt(st.nextToken());
			}
			list.insert(0, initArr);
			
			int M = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<M;i++) {
				char cmd = st.nextToken().charAt(0);
				int x,y;
				
				switch (cmd) {
				case 'I':
					x = Integer.parseInt(st.nextToken());
					y = Integer.parseInt(st.nextToken());
					int[] temp = new int[y];
					for(int j=0;j<y;j++) {
						temp[j] = Integer.parseInt(st.nextToken());
					}
					list.insert(x, temp);
					break;
					
				case 'D':
					x = Integer.parseInt(st.nextToken());
					y = Integer.parseInt(st.nextToken());
					list.delete(x, y);
					break;
				case 'A':
					y = Integer.parseInt(st.nextToken());
					for(int j=0;j<y;j++) {
						list.add(Integer.parseInt(st.nextToken()));
					}
					break;
				}
			}
			list.print();
			bw.write("\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

}

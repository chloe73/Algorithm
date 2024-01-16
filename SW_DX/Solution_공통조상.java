package algo.SW_DX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution_공통조상 {
	
	static int V,E,A,B,result;
	static Node[] nodes;
	static ArrayList<Integer> aList, bList; // A,B 조상 리스트
	static class Node {
		List<Integer> children;
		int parent;
		
		Node(){
			this.children = new ArrayList<>();
			this.parent = 0;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			nodes = new Node[V+1];
			aList = new ArrayList<>();
			bList = new ArrayList<>();
			
			for(int i=0;i<V+1;i++) {
				nodes[i] = new Node();
			}
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<E;i++) {
				int parent = Integer.parseInt(st.nextToken());
				int child = Integer.parseInt(st.nextToken());
				nodes[parent].children.add(child);
				nodes[child].parent = parent;				
			}
			
			// A, B 조상 찾기
			traverse(A, aList);
			traverse(B, bList);
			
			for(int i=0;i<V;i++) {
				if(!aList.get(i).equals(bList.get(i))) break;
				result = aList.get(i);
			}
			
			bw.write(result+" "+find_subtree_size(result)+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}
	
	private static void traverse(int idx, ArrayList<Integer> list) {
		int parent = nodes[idx].parent;
		
		if(parent != 0) {
			// 부모 노드가 존재하면 계속 조상 찾기
			traverse(parent, list);
		}
		list.add(idx);
	}
	
	private static int find_subtree_size(int idx) {
		int cnt = 1;
		
		for(int child : nodes[idx].children) {
			cnt += find_subtree_size(child);
		}
		
		return cnt;
	}

}

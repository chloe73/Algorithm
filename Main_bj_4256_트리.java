package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_4256_트리 {
	
	static int T,N;
	static int[] pre,in;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		T = Integer.parseInt(br.readLine());
		for(int tc=0;tc<T;tc++) {
			N = Integer.parseInt(br.readLine());
			
			pre = new int[N+1];
			in = new int[N+1];
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				pre[i] = Integer.parseInt(st.nextToken());
			} // 전위 순회
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				in[i] = Integer.parseInt(st.nextToken());
			} // 중위 순회
			
			solve(0,0,N);
			
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	private static void solve(int root, int start, int end) {
		
//		root = pre[0] 이진트리의 루트노드를 시작으로 트리 순회를 시작한다.
//		if(in[i] == root) inorder 데이터에서 루트 노드의 위치를 찾는다
//		왼쪽트리는 [s~i)의 범위를 가지고 root노드는 pre[현재 루트 노드 idx+1]를 가진다.
//		오른쪽트리는 [i+1,e)의 범위를 가지고 root노드는 pre[현재 루트 노드 idx+i-s+1]을 가진다.
//		이렇게 트리 구조를 탐색하며 후위 순회 데이터를 저장해준다.
		
		int rootIdx = pre[root];
		
		for(int i=start;i<end;i++) {
			if(in[i] == rootIdx) {
				solve(root+1, start, i);
				solve(root+i+1-start,i+1,end);
				sb.append(rootIdx + " ");
			}
		}
		
	}

}

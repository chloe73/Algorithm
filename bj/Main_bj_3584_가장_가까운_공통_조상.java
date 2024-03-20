package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_3584_가장_가까운_공통_조상 {
	
	static int N, rootIdx;
	static ArrayList<Integer>[] list;
	static int[] depth, parent;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			N = Integer.parseInt(br.readLine());
			
			depth = new int[N+1];
			parent = new int[N+1];
			list = new ArrayList[N+1];
			for(int i=1;i<=N;i++) {
				list[i] = new ArrayList<>();
			}
			
//			그리고 그 다음 N-1개의 줄에 트리를 구성하는 간선 정보가 주어집니다. 
//			(당연히 정점이 N개인 트리는 항상 N-1개의 간선으로 이루어집니다!) A와 B는 1 이상 N 이하의 정수로 이름 붙여집니다.
			
			boolean[] rootCheck = new boolean[N+1];
			Arrays.fill(rootCheck, true);
			for(int i=0;i<N-1;i++) {
//				한 간선 당 한 줄에 두 개의 숫자 A B 가 순서대로 주어지는데, 이는 A가 B의 부모라는 뜻입니다. 
				StringTokenizer st = new StringTokenizer(br.readLine());
				int A = Integer.parseInt(st.nextToken());
				int B = Integer.parseInt(st.nextToken());
				list[A].add(B);
				rootCheck[B] = false;
			}
			
			rootIdx = 0;
			for(int i=1;i<=N;i++) {
				if(rootCheck[i]) {
					rootIdx = i;
					break;
				}
			}
			
			init(rootIdx, 1, 0);
			
//			테스트 케이스의 마지막 줄에 가장 가까운 공통 조상을 구할 두 노드가 주어집니다.
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			sb.append(LCA(a, b)+"\n");
		}
		System.out.println(sb.toString());
	}
	
	private static void init(int temp, int h, int p) {
		depth[temp] = h;
		parent[temp] = p;
		for(int next : list[temp]) {
			if(next != p) {
				init(next, h+1, temp);
			}
		}
	}

	private static int LCA(int a, int b) {
		int ah = depth[a];
		int bh = depth[b];
		
		// 1. 두 노드의 높이를 맞춰준다.
		while(ah > bh) {
			a = parent[a];
			ah--;
		}
		
		while(bh > ah) {
			b = parent[b];
			bh--;
		}
		
		// 2. 높이가 일치되면, 부모노드를 찾아준다.
		while(a != b) {
			a = parent[a];
			b = parent[b];
		}
		
		return a;
	}

}

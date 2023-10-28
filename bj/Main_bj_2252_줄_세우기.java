package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_2252_줄_세우기 {
	
	static int N,M;
	static int[] arr;
	static ArrayList<Integer>[] list;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new int[N+1];
		list = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			list[i] = new ArrayList<>();
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			list[a].add(b);
			arr[b]++;
		}
		
		solve();
		
		System.out.println(sb.toString());
		
		br.close();
	}

	private static void solve() {
		Queue<Integer> q = new LinkedList<>();
		
		for(int i=1;i<=N;i++) {
			if(arr[i] == 0) {
				q.add(i);
			}
		}
		
		while(!q.isEmpty()) {
			int temp = q.poll();
			sb.append(temp).append(" ");
			
			for(int i=0;i<list[temp].size();i++) {
				int next = list[temp].get(i);
				arr[next]--;
				if(arr[next] == 0) {
					q.add(next);
				}
			}
		}
	}

}

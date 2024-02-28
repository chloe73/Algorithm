package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Main_bj_2668_숫자고르기 {

	static int N;
	static int[] arr;
	static ArrayList<Integer> result;
	static boolean[] visited;
	static StringBuilder sb;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		
		arr = new int[N+1];
		
		for(int i=1;i<=N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		} // input end
		
		visited = new boolean[N+1];
		result = new ArrayList<>();
		for(int i=1;i<=N;i++) {
			visited[i] = true;
			dfs(i,i);
			visited[i] = false;
		}
		Collections.sort(result);
		sb.append(result.size()+"\n");
		for(int i : result) {
			sb.append(i+"\n");
		}
		System.out.println(sb.toString());
	}

	private static void dfs(int value, int idx) {
		if(!visited[arr[value]]) {
			visited[arr[value]] = true;
			dfs(arr[value], idx);
			visited[arr[value]] = false;
		}
		
		if(arr[value] == idx) {
			result.add(arr[value]);
		}
	}

}

package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_1068_트리 {
	
	static int N;
	static ArrayList<Integer>[] tree;
	static boolean[] isChecked;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		N = Integer.parseInt(br.readLine());
		tree = new ArrayList[N];
		for(int i=0;i<N;i++) {
			tree[i] = new ArrayList<>();
		}
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			int parent = Integer.parseInt(st.nextToken());
			
			if(parent == -1) continue;
			
			tree[parent].add(i);
		}
		
		isChecked = new boolean[N];
		int target = Integer.parseInt(br.readLine());
		isChecked[target] = true;
		
		solve(target);
		
		int result = 0;
		for(int i=0;i<N;i++) {
			if(isChecked[i]) continue;
			
			if(tree[i].contains(target) && tree[i].size()-1 == 0)
				result++;
				
			if(tree[i].size() == 0) result++;
		}
		
		bw.write(result+"");
		bw.flush();
		br.close();
		bw.close();
	}
	
	private static void solve(int tempIdx) {
		
		if(tree[tempIdx].size() > 0) {
			for(int next : tree[tempIdx]) {
				isChecked[next] = true;
				solve(next);
			}
		}
	}

}

package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_bj_4195_친구_네트워크 {
	
	static int[] parent, rank;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1;tc<=T;tc++) {
			int F = Integer.parseInt(br.readLine());
			HashMap<String, Integer> fMap = new HashMap<>();
			int num = 1;
			parent = new int[F*2+1];
			rank = new int[F*2+1];
			for(int i=0;i<=F*2;i++) {
				parent[i] = i;
				rank[i] = 1;
			}
			
			for(int i=0;i<F;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				String a = st.nextToken();
				String b = st.nextToken();
				
				if(!fMap.containsKey(a)) {
					fMap.put(a, num++);
					parent[fMap.get(a)] = fMap.get(a);
				}
				if(!fMap.containsKey(b)) {
					fMap.put(b, num++);
					parent[fMap.get(b)] = fMap.get(b);
				}
				
				int result = union(fMap.get(a), fMap.get(b));
				
				bw.write(result+"\n");
			}
		}
		
		bw.flush();
		br.close();
		bw.close();
	}
	
	public static int union(int x, int y) {
		x = find(x);
		y = find(y);
		
		if(x != y) {
			if(x > y) {
				parent[x] = y;
				rank[y] += rank[x];
				return rank[y];
			}
			else {
				parent[y] = x;
				rank[x] += rank[y];
			}
		}
		
		return rank[x];
	}

	public static int find(int x) {
		if(x == parent[x]) return x;
		
		return parent[x] = find(parent[x]);
	}
}

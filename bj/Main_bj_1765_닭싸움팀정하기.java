package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_1765_닭싸움팀정하기 {
	
	static int N,M,result;
	static ArrayList<Integer>[] f, e;
	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());

		f = new ArrayList[N+1];
		e = new ArrayList[N+1];
		for(int i=0;i<=N;i++) {
			f[i] = new ArrayList<Integer>();
			e[i] = new ArrayList<Integer>();
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			char type = st.nextToken().charAt(0);
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			if(type == 'F') {
				f[a].add(b);
				f[b].add(a);
			} else {
				e[a].add(b);
				e[b].add(a);
			}
		}
		
		for(int i=1;i<=N;i++) {
			visited = new boolean[N+1];
			findF(i,i,0);
		}
		
		visited = new boolean[N+1];
		result = 0;
		for(int i=1;i<=N;i++) {
			if(visited[i]) continue;
			
			result++;
			makeTeam(i);
		}
		
		System.out.println(result);
	}

	private static void makeTeam(int idx) {
		if(visited[idx]) return;
		
		visited[idx] = true;
		for(int friend : f[idx]) {
			makeTeam(friend);
		}
		
	}

	// 원수 관계로부터 친구 관계를 찾는 함수
	private static void findF(int start, int idx, int depth) {
		if(visited[idx]) return;
		
		if(depth == 2) {
			f[start].add(idx);
			return;
		}
		
		visited[idx] = true;
		for(int enemy : e[idx]) {
			findF(start,enemy,depth+1);
		}
		
	}

}

package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

// Gold 4.
public class Main_bj_1043_거짓말 {
	
	static int N,M,result;
	static int[] parent; // union - find를 위한 자료구조
	static ArrayList<Integer>[] party_people; // M개의 파티에 오는 사람들
	static boolean[] story; // 각 사람들이 들은 이야기가 진실인지 거짓인지를 확인하기 위한 자료구조

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// N,M 입력 받기
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 총 사람의 수
		M = Integer.parseInt(st.nextToken()); // 파티의 수
		story = new boolean[N+1];
		
		// union-find 초기화
		parent = new int[N+1];
		for(int i=1;i<=N;i++) {
			parent[i] = i;
		}
		
		// 진실을 아는 사람들 정보 입력 받기
		st = new StringTokenizer(br.readLine());
		int truthNum = Integer.parseInt(st.nextToken()); // 진실을 아는 사람들의 수
		if(truthNum > 0) { // 진실을 아는 사람들이 0이 아니라면 배열에 넣기
			for(int i=0;i<truthNum;i++) {
				int num = Integer.parseInt(st.nextToken());
				story[num] = true; // 
			}
		}
		
		// 각 파티 정보를 받기 위한 세팅
		party_people = new ArrayList[M];
		for(int i=0;i<M;i++) {
			party_people[i] = new ArrayList<>();
		}
		
		int value, pre = 0;
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int size = Integer.parseInt(st.nextToken()); // 각 파티에 오는 사람들 수
			
			// 파티에 오는 사람들 번호
			if(size > 0) {
				pre = Integer.parseInt(st.nextToken());
				party_people[i].add(pre);
			}
			
			for(int j=1;j<size;j++) {
				value = Integer.parseInt(st.nextToken());
				party_people[i].add(value);
				union(pre, value);
				pre = value;
			}
		} // input end
		
		for(int i=1;i<story.length;i++) {
			if(story[i]) {
				story[find(i)] = true;
			}
		}
		int parent;
		for(int i=0;i<M;i++) {
			if(party_people[i].size() > 0) {
				parent = find(party_people[i].get(0));
				if(!story[parent]) result++;
			}
		}
		
		System.out.println(result);
	}

	private static int find(int x) {
		if(parent[x] == x)
			return x;
		else 
			return find(parent[x]);
	}

	private static boolean union(int a, int b) {
		a = find(a);
		b = find(b);
		
		if(a != b) {
			if(a>b) parent[a] = b;
			else parent[b] = a;
			return true;
		}
		return false;
	}

}

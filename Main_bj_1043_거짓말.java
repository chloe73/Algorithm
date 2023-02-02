package algo;

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
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int size = Integer.parseInt(st.nextToken()); // 각 파티에 오는 사람들 수
			if(size > 0) {				
				for(int j=0;j<size;j++) {
					// 파티에 오는 사람들 번호
					int num = Integer.parseInt(st.nextToken());
					party_people[i].add(num);
				}
			}
		} // input end
		
		
		
		System.out.println(result);
	}

}

package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 슬라이딩 윈도우 || 투 포인터 
public class Main_bj_2531_회전초밥 {
	
	static int N,d,k,c,result;
	static int[] arr,visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		arr = new int[N];
		visited = new int[d+1]; // 초밥 종류 번호 index
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(br.readLine());
		} // input end
		
		solve();
		
	}

	private static void solve() {
		int count = 0; // 종류 수 체크
		for(int i=0;i<k;i++) {
			if(visited[arr[i]] == 0) count++;
			
			visited[arr[i]]++;
		}
		
		int maxLen = count;
		
		for(int i=1;i<N;i++) {
			if(maxLen <= count) {
				if(visited[c] == 0) { // 쿠폰 초밥을 안먹었다면 +1
					maxLen = count + 1;
				} else { // 이미 쿠폰 초밥을 먹었다면 그대로
					maxLen = count;
				}
			}
			
			int end = (i+k-1) % N;
			if(visited[arr[end]] == 0) count++;
			
			visited[arr[end]]++;
			
			visited[arr[i-1]]--;
			if(visited[arr[i-1]] == 0) count--;
		}
		
		System.out.println(maxLen);
	}

}

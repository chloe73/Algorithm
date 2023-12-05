package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_20922_겹치는_건_싫어 {
	
	static int N,K,result;
	static int[] arr;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		// 100,000 이하의 양의 정수로 이루어진 길이가 
		// N인 수열이 주어진다.  이 수열에서 같은 정수를 
		// K개 이하로 포함한 최장 연속 부분 수열의 길이를 구하는 프로그램을 작성해보자.
		
		int start = 0;
		int end = 0;
		
		// 각 정수가 몇번 나왔는지 체크하기 위한 변수
		int[] count = new int[100001];
		result = Integer.MIN_VALUE;
		
//		9 2
//		3 2 5 5 6 4 4 5 7
		
		while(end < N) {
			// 늘릴 수 있을 때까지 end 증가
			while(end < N && count[arr[end]] + 1 <= K) {
				count[arr[end]]++;
				end++;
			}
			
			// 더 이상 늘릴 수 없으면
			int cnt = end - start;
			result = Math.max(result, cnt);
			
			// start 이동
			count[arr[start]]--;
			start++;
		}
		
		System.out.println(result);
	}

}

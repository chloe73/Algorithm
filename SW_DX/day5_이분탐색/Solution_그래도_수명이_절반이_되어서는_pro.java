package algo.SW_DX.day5_이분탐색;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_그래도_수명이_절반이_되어서는_pro {
	
	private static int MAX_W = 200000;
	static int N,K;
	static int[] W,S;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			W = new int[N];
			S = new int[K];
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				W[i] = Integer.parseInt(st.nextToken());
			}
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<K;i++) {
				S[i] = Integer.parseInt(st.nextToken());
			} // input end
			
			int result = binarySearch();
			
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

	private static int binarySearch() {
		int left = 1;
		int right = MAX_W;
		int min = -1; // 
		
		while(left <= right) {
			// left 미만은 더 이상 확인할 필요가 없음. 그래서 left값에 더하는 것임.
			int mid = left + (right-left) / 2;
			
			if(isPossible(mid)) {
				right = mid - 1;
				min = mid;
			}
			else {
				left = mid + 1;
			}
		}
		
		return min;
	}

	private static boolean isPossible(int mid) {
		int idx = 0; // 덩어리 idx
		
		for(int i=0;i<N;i++) {
			if(W[i] > mid) continue;
			
			int size = i + S[idx];
			for(int j=i;j<size && j<N;j++) {
				if(W[j] > mid) {
					i = j;
					break;
				}
				
				if(j == size-1) {
					i = j;
					idx++;
				}
			}
			
			if(idx == K) return true;
		}
		
		return false;
	}

}

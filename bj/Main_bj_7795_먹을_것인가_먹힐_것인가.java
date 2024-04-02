package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_7795_먹을_것인가_먹힐_것인가 {
	
	static int[] A,B;
	// 이분탐색
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			A = new int[N];
			B = new int[M];
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				A[i] = Integer.parseInt(st.nextToken());
			}
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<M;i++) {
				B[i] = Integer.parseInt(st.nextToken());
			}
			Arrays.sort(B);
			
			int result = 0;
			
			for(int a : A) {
				int left = 0;
				int right = M-1;
				int index = 0;
				
				while(left <= right) {
					int mid = (left + right) / 2;
					
					if(a > B[mid]) {
						left = mid+1;
						index = mid+1;
					}
					else {
						right = mid-1;
					}
				}
				result += index;
			}
			
			sb.append(result+"\n");
		}

		System.out.println(sb.toString());
	}
	
		// 브루트포스
//		public static void main(String[] args) throws IOException{
//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			StringBuilder sb = new StringBuilder();
//			
//			int T = Integer.parseInt(br.readLine());
//			for(int tc=1;tc<=T;tc++) {
//				StringTokenizer st = new StringTokenizer(br.readLine());
//				int N = Integer.parseInt(st.nextToken());
//				int M = Integer.parseInt(st.nextToken());
//				
//				A = new int[N];
//				B = new int[M];
//				
//				st = new StringTokenizer(br.readLine());
//				for(int i=0;i<N;i++) {
//					A[i] = Integer.parseInt(st.nextToken());
//				}
//				Arrays.sort(A);
//				
//				st = new StringTokenizer(br.readLine());
//				for(int i=0;i<M;i++) {
//					B[i] = Integer.parseInt(st.nextToken());
//				}
//				Arrays.sort(B);
//				
//				int result = 0;
//				
//				for(int a : A) {
//					for(int b : B) {
//						if(a <= b) break;
//						result++;
//					}
//				}
//				
//				sb.append(result+"\n");
//			}
//
//			System.out.println(sb.toString());
//		}

}

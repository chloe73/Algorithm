package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_17266_어두운_굴다리 {

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      
        int N = Integer.parseInt(br.readLine()); // 굴다리 길이
        int M = Integer.parseInt(br.readLine()); // 가로등 개수
        int[] x = new int[M]; // 가로등 위치
        int result = 0; // 최소 높이

        
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i=0; i<M; i++) {
        	int point = Integer.parseInt(st.nextToken());
        	x[i] = point;
        }
        
        // 이분 탐색
        int low = 1; // 굴다리 최소 길이
        int high = N; // 굴다리 길이가 최대
        while(low <= high) {
        	int mid = (low+high)/2; // 중간값을 구해놓고
        	boolean flag = true; // while문을 멈출지 말지 결정할 플래그
        	
        	// mid 높이로 가능한지 굴다리를 쭉 돌아본다
        	int point = 0; // 이전에 가로등이 비추었던 위치
        	for (int i=0; i<x.length; i++) {
        		if (x[i] - mid <= point) {
        			point = x[i] + mid; // 가로등은 x[i]+mid까지 비출 수 있다.
        		}
        		else {
        			flag = false;
        		}
        	}
        	// 마지막 가로등부터 끝 지점도 확인해보자 (함정인듯)
        	if (N - point > 0) flag = false;
        	else flag = true;
	
        	if (flag) { // 모두를 비출 수 있음
        		result = mid; // 갱신
        		high = mid - 1; // 다음 단계로
        	} else { // 모두 비출 수 없음
        		low = mid + 1; // 다음 단계로
        	}
        }
        
        System.out.println(result);
        
	}
}
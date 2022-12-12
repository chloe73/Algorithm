package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_2933_미네랄 {
	
	static int R,C,N;
	static char[][] cave;
	static ArrayList<Integer> stick;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		cave = new char[R][C];
		for(int i=0;i<R;i++) {
			cave[i] = br.readLine().toCharArray();
		}
		
		stick = new ArrayList<>();
		N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			stick.add(Integer.parseInt(st.nextToken()));
		}
	}

}

package algo.SW_DX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_중위순회 {
	
	static char[] tree;
	static int N;
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = 10;
		
		for(int tc=1;tc<=10;tc++) {
			bw.append("#"+tc+" ");
			
			N = Integer.parseInt(br.readLine());
			tree = new char[N+1];
			for(int i=0;i<N;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				int idx = Integer.parseInt(st.nextToken());
				
				tree[idx] = st.nextToken().charAt(0);
			}
			
			inorder(1);
			bw.append("\n");
		}
		
		bw.flush();
		br.close();
		bw.close();
	}

	private static void inorder(int i) throws IOException {
		
		if(i > N) return;
		
		inorder(2*i);
		bw.append(tree[i]+"");
		inorder(2*i+1);
	}

}

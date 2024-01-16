package algo.SW_DX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_사칙연산_유효성_검사 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		for(int tc=1;tc<=10;tc++) {
			bw.append("#"+tc+" ");
			
			int N = Integer.parseInt(br.readLine());
			int result = 1;
			for(int i=0;i<N;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				st.nextToken();
				
				char cmd = st.nextToken().charAt(0);
				// 현재 노드가 단말 노드가 아닌 경우
				if(st.hasMoreTokens()) {
					// 단말 노드가 아닌데 숫자인 경우 => 불가능한 연산
					if(cmd >'0' && cmd <= '9') result = 0;
				}
				else {
					// 현재 노드가 단말 노드인 경우
					// cmd 값은 숫자여야 함.
					if(!(cmd >'0' && cmd <= '9')) result = 0;
				}
			}
			
			bw.append(result+"\n");
		}
		
		bw.flush();
		br.close();
		bw.close();
	}

}

package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_10430 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
		
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();
        
        int result1 = (A+B)%C;
        int result2 = ((A%C) + (B%C))%C;
        int result3 = (A*B)%C;
        int result4 = ((A%C) * (B%C))%C;
        sb.append(result1+"\n"+result2+"\n"+result3+"\n"+result4);
        System.out.println(sb.toString());
	}

}

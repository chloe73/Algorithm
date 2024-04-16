package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1008 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
		
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        double result = (double) A / B;
        System.out.println(result);
	}

}
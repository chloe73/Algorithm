package algo.SW_DX;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution_현석이의생일 {

    static int x,y;
    static long N,result;
    static String sN,sX,sY;
    static ArrayList<Integer> num;

    public static void main(String[] args) throws IOException {
    	System.setIn(new FileInputStream("input/input_현석이의생일.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int tc=1;tc<=T;tc++) {

            st = new StringTokenizer(br.readLine());
            sN = st.nextToken();
            sX = st.nextToken();
            sY = st.nextToken();
            N = Long.parseLong(sN);
            x = Integer.parseInt(sX);
            y = Integer.parseInt(sY);
//            String s = x + y;
//            System.out.println(Integer.parseInt(s));
            num = new ArrayList<>();
            result = -1;

            solve(0,"");
            
            sb.append("#"+tc+" ").append(result).append("\n");
        }
        System.out.println(sb.toString());
    }

	private static void solve(int idx, String s) {
		
		if(idx == sN.length()) {
			long number = Long.parseLong(s);
			if(number <= N) {
				result = Math.max(result, number);
			}
			return;
		}
		
		int now = sN.charAt(idx)-'0';
		
		if(now == y) {
			solve(idx+1,s+""+now);
		}
		
		else if(now == x) {
			solve(idx+1, s+""+now);
		}
		
		else if(now > y) {
			solve(idx+1,s+""+y);
		}
		
		else if(now < x) {
			return;
		}
		
		else if(x < now && now < y) {
			return;
		}
	}

}

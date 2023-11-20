package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_14238_출근_기록 {
	
	static int[][][][][] dp;
    static boolean flag = false;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new  BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        int a_cnt = 0;
        int b_cnt = 0;
        int c_cnt = 0;

        for(int i=0; i<input.length(); i++) {
            if(input.charAt(i)=='A')
                a_cnt++;

            else if(input.charAt(i)=='B')
                b_cnt++;

            else
                c_cnt++;
        }
        dp = new int[a_cnt+1][b_cnt+1][c_cnt+1][3][3];

        dfs(a_cnt, b_cnt, c_cnt, "", 0, 0);

        if(!flag)
            System.out.println(-1);
    }

    public static void dfs(int a, int b, int c, String s, int pre2, int pre) {
        if(flag) return;

        if(a==0 && b==0 && c==0) {
            System.out.println(s);
            flag = true;
            return;
        }

        if(dp[a][b][c][pre2][pre]==1) return;

        dp[a][b][c][pre2][pre] = 1;

        if(a>0)
            dfs(a-1, b, c, s+'A', pre, 0);

        if(b>0 && pre!=1)
            dfs(a, b-1, c, s+'B', pre, 1);

        if(c>0 && pre!=2 && pre2!=2)
            dfs(a, b, c-1,s+'C', pre, 2);
    }
}
package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1937_욕심쟁이판다 {

    static int N,result;
    static int[][] board;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static int[][] dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        board = new int[N][N];
        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        } // input end

        result = 0;
        dp = new int[N][N];

        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                result = Math.max(result,dfs(i,j));
            }
        }

        System.out.println(result);
    }

    private static int dfs(int x, int y) {

        if(dp[x][y] != 0) return dp[x][y];

        dp[x][y] = 1;
        for(int i=0;i<4;i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx<0 || ny<0 || nx>=N || ny>=N) continue;

            if(board[nx][ny] > board[x][y]) {
                dp[x][y] = Math.max(dp[x][y], dfs(nx,ny)+1);
            }
        }

        return dp[x][y];
    }
}

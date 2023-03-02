package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_bj_19236_청소년상어 {
	
	static int result; // 상어가 먹을 수 있는 물고기 번호의 최대합
    static int[] dx = {-1,-1,0,1,1,1,0,-1};
    static int[] dy = {0,-1,-1,-1,0,1,1,1};
    static class Fish {
        int x,y,dir;
        public Fish(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[][] board = new int[4][4];
        Fish[] fishList = new Fish[16];
        result = 0;
        for(int i=0;i<4;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<4;j++) {
                int a = Integer.parseInt(st.nextToken())-1;
                int b  = Integer.parseInt(st.nextToken())-1;

                board[i][j] = a;
                fishList[a] = new Fish(i,j,b);
            }
        }

        dfs(0,0,0,fishList,board);

        System.out.println(result);
    }

    private static void dfs(int sx, int sy, int sum, Fish[] fishList, int[][] board) {
        Fish[] temp_fish = new Fish[16];
        int[][] temp_board = new int[4][4];

        // 물고기 상태 복사
        for(int i=0;i<16;i++) {
            temp_fish[i] = new Fish(fishList[i].x,fishList[i].y,fishList[i].dir);
        }

        // board 상태 복사
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                temp_board[i][j] = board[i][j];
            }
        }

        // 상어 물고기 먹음
        int fishNum = temp_board[sx][sy];
        int sd = temp_fish[fishNum].dir;
        temp_fish[fishNum].x = -1;
        temp_fish[fishNum].y = -1;
        temp_fish[fishNum].dir = -1;
        temp_board[sx][sy] = -1;

        sum += fishNum+1;
        if(sum > result) result = sum;

        // 물고기 이동
        for(int i=0;i<16;i++) {
            if(temp_fish[i].x == -1) continue;

            int x = temp_fish[i].x;
            int y = temp_fish[i].y;
            int d = temp_fish[i].dir;

            int nx = x + dx[d];
            int ny = y + dy[d];
            int nd = d;

            while(nx<0 || ny<0 || nx>=4 || ny>=4 || (nx == sx && ny == sy)) {
                nd = (nd+1) % 8;
                nx = x + dx[nd];
                ny = y + dy[nd];
            }

            if(temp_board[nx][ny] != -1) {
                int target = temp_board[nx][ny];
                temp_fish[target].x = x;
                temp_fish[target].y = y;

                temp_fish[i].x = nx;
                temp_fish[i].y = ny;
                temp_fish[i].dir = nd;

                temp_board[x][y] = target;
                temp_board[nx][ny] = i;
            }
            else {
                temp_fish[i].x = nx;
                temp_fish[i].y = ny;
                temp_fish[i].dir = nd;

                temp_board[x][y] = -1;
                temp_board[nx][ny] = i;
            }
        }

        // 상어 이동
        for(int i=1;i<=3;i++) {
            int x = sx + dx[sd] * i;
            int y = sy + dy[sd] * i;
            if(x<0 || y<0 || x>=4 || y>=4) break;
            if(temp_board[x][y] != -1) {
                dfs(x,y,sum,temp_fish,temp_board);
            }
        }
    }
}
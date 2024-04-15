package algo.programmers;

import java.util.*;
import java.io.*;

class Solution_리코쳇_로봇 {
    
    static char[][] board;
    static int N,M,sx,sy;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    
    public int solution(String[] b) {
        int answer = 0;
        N = b.length;
        M = b[0].length();
        //System.out.println(N+", "+M);
        
        board = new char[N][M];
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                board[i][j] = b[i].charAt(j);
                if(board[i][j] == 'R') {
                    sx = i;
                    sy = j;
                }
            }
        }
        
        answer = bfs();
        
        return answer;
    }
    
    private static int bfs() {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        q.add(new int[] {sx,sy,0});
        visited[sx][sy] = true;
        
        while(!q.isEmpty()) {
            int[] temp = q.poll();
            int x = temp[0];
            int y = temp[1];
            int cnt = temp[2];
            
            if(board[x][y] == 'G') {
                //System.out.println("*** !"+cnt);
                return cnt;
            }
            
            for(int d=0;d<4;d++) {
                int nx = x;
                int ny = y;
                
                while(isValid(nx,ny) && board[nx][ny] != 'D') {
                    nx += dx[d];
                    ny += dy[d];
                }
                
                nx -= dx[d];
                ny -= dy[d];
                
                if(visited[nx][ny] || (x == nx && y == ny)) continue;
                
                visited[nx][ny] = true;
                //System.out.println(nx+", "+ny+", "+cnt);
                q.add(new int[] {nx,ny,cnt+1});
            }
        }
        
        return -1;
    }
    
    private static boolean isValid(int r, int c) {
        if(r<0 || c<0 || r>=N || c>=M) return false;
        return true;
    }
} 

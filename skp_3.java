package algo;

import java.io.*;
import java.util.*;

class Solution {

    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static boolean[][] visited,flower;
    static char[][] maps;
    static int n;
    static boolean flag;

    public int[] solution(String[][] boards) {
        int size = boards.length; // 총 input의 크기
        int[] answer = new int[size];

        for(int idx=0;idx<size;idx++) {
            n = boards[idx].length;
            maps = new char[n][n];
            visited = new boolean[n][n];
            flower = new boolean[n][n];
            // System.out.println(n);
            int startX = 0;
            int startY = 0;
            for(int i=0;i<n;i++) {
                maps[i] = boards[idx][i].toCharArray();
                for(int j=0;j<n;j++) {
                    if(maps[i][j] == '2') { // 캐릭터의 현재 위치 찾았다면
                        startX = i;
                        startY = j;
                        break;
                    }
                }
            }

            flower[startX][startY] = true;
            boolean result = solve(startX,startY);
            
            // System.out.println(flag);
            if(flag) answer[idx] = 1;
            else answer[idx] = 0;
        }

        return answer;
    }

    private static boolean solve(int i, int j) {

        visited[i][j] = true;
        boolean move = false; // 사방으로 모두 이동하지 못했을 때를 체크하기 위한 변수
        flower[i][j] = true;
        for(int d=0;d<4;d++) {
            int nx = i + dx[d];
            int ny = j + dy[d];

            if(nx< 0 || ny<0 || nx>=n || ny>=n) continue;

            if(visited[nx][ny]) continue;

            if(flower[nx][ny]) continue;

            if(maps[nx][ny] == '1') { // 꽃이 안심어진 길이라면 
                move = true;
                visited[nx][ny] = true;
                flower[nx][ny] = true;
                solve(nx,ny);
                visited[nx][ny] = false;
            }
        }

        if(!move) { // 현재 위치에서 4방으로 모두 이동이 불가하다면
            // System.out.println("========");
            boolean result = check();
            return result;
        }
        return true;
    }

    private static boolean check() {
        // 모든 길에 꽃이 심어졌는지 체크 !
        flag = true;
        outer:for(int a=0;a<n;a++) {
            for(int b=0;b<n;b++) {
                if(maps[a][b] == '1' && !flower[a][b]) {
                    flag = false;
                    // System.out.println(a + " , " + b);
                    break outer;
                }
            }
        }
        return flag;
    }
}
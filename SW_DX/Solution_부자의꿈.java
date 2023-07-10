package algo.SW_DX;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_부자의꿈 {
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input/input_부자의꿈.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int TC = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TC; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int Q = Integer.parseInt(st.nextToken());

            int[][] grid = new int[N][M];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int[] rowMax = new int[N];
            int[] colMax = new int[M];

            // 초기값 설정
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    rowMax[i] = Math.max(rowMax[i], grid[i][j]);
                    colMax[j] = Math.max(colMax[j], grid[i][j]);
                }
            }

            int safeCells = 0;

//            // 초기 안전한 셀 개수 계산
//            for (int i = 0; i < N; i++) {
//                for (int j = 0; j < M; j++) {
//                    if (grid[i][j] == rowMax[i] && grid[i][j] == colMax[j]) {
//                        safeCells++;
//                    }
//                }
//            }

            // 업데이트 수행
            for (int i = 0; i < Q; i++) {
                st = new StringTokenizer(br.readLine());
                int r = Integer.parseInt(st.nextToken()) - 1;
                int c = Integer.parseInt(st.nextToken()) - 1;
                int x = Integer.parseInt(st.nextToken());

                if (x > rowMax[r]) {
                    for (int j = 0; j < M; j++) {
                        if (grid[r][j] == rowMax[r]) {
                            grid[r][j] = x;
                        }
                        rowMax[r] = Math.max(rowMax[r], grid[r][j]);
                    }
                }

                if (x > colMax[c]) {
                    for (int j = 0; j < N; j++) {
                        if (grid[j][c] == colMax[c]) {
                            grid[j][c] = x;
                        }
                        colMax[c] = Math.max(colMax[c], grid[j][c]);
                    }
                }

                if (grid[r][c] == rowMax[r] && grid[r][c] == colMax[c]) {
                    safeCells++;
                }

                System.out.println(safeCells);
            }
            
            sb.append("#"+tc+" ").append(safeCells).append("\n");
        }
    }
}
# 프로그래머스 - lv2.퍼즐 조각 채우기

[문제 링크](https://school.programmers.co.kr/learn/courses/30/lessons/84021)

---

```java
import java.util.*;
import java.io.*;

class Solution {
    List<List<Point>> g = new ArrayList<>();
    List<List<Point>> t = new ArrayList<>();
    static int N;
    static class Point implements Comparable<Point> {
        int x,y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(Point o) { // 오름차순 정렬
            if(this.x == o.x) return this.y - o.y;
            return this.x - o.x;
        }
    }
    int[] dx = {-1,1,0,0};
    int[] dy = {0,0,-1,1};

    public int solution(int[][] game_board, int[][] table) {
        int answer = 0;

        N = game_board.length; // game_board 행, 열 길이

        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                if(game_board[i][j] == 1) game_board[i][j] = 0;
                else game_board[i][j] = 1;
            }
        }

        boolean[][] visited_g = new boolean[N][N];
        boolean[][] visited_t = new boolean[N][N];

        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                // table에서 블록 추출
                if(table[i][j] == 1 && !visited_t[i][j]) {
                    bfs(i, j, table, visited_t, t);
                }

                // game_board에서 빈칸 추출
                if(game_board[i][j] == 1 && !visited_g[i][j]) {
                    bfs(i, j, game_board, visited_g, g);
                }
            }
        }

        answer = compareBlock(t, g, answer);

        return answer;
    }

    public int compareBlock(List<List<Point>> table, List<List<Point>> board, int answer) {
        int table_size = table.size(); // 블럭이 들어있는 list
        int board_size = board.size(); //

        boolean[] visited = new boolean[board_size];

        for(int i=0;i<table_size;i++) {
            for(int j=0;j<board_size;j++) {
                if(visited[j] || table.get(i).size() != board.get(j).size()) continue;
                if(isRotate(table.get(i), board.get(j))) {
                    visited[j] = true;
                    answer += board.get(j).size();
                    break;
                }
            }
        }

        return answer;
    }

    public boolean isRotate(List<Point> table, List<Point> board) {
        Collections.sort(board);

        // 0도, 90도, 180도, 270도
        for(int i=0;i<4;i++) {
            Collections.sort(board);

            int cur_x = table.get(0).x;
            int cur_y = table.get(0).y;

            // 회전하면서 좌표 바뀌었기 때문에 (0,0) 기준으로 다시 세팅
            for(int j=0;j<table.size();j++) {
                table.get(j).x -= cur_x;
                table.get(j).y -= cur_y;
            }

            boolean flag = true;

            // board에 들어가는지 확인
            for(int j=0;j<board.size();j++) {
                if(board.get(j).x != table.get(j).x || board.get(j).y != table.get(j).y) {
                    flag = false;
                    break;
                }
            }

            if(flag) return true;
            else {
                // 90도 회전시키기 x,y -> y, -x
                for(int j=0;j<table.size();j++) {
                    int temp = table.get(j).x;
                    table.get(j).x = table.get(j).y;
                    table.get(j).y = -temp;
                }
            }

        }
        return false;
    }

    public void bfs(int x, int y, int[][] board, boolean[][] visited, List<List<Point>> list) {
        visited[x][y] = true;
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(x,y));

        List<Point> sub_list = new ArrayList<>();
        sub_list.add(new Point(0,0));

        while(!q.isEmpty()) {
            Point temp = q.poll();

            for(int i=0;i<4;i++) {
                int nx = temp.x + dx[i];
                int ny = temp.y + dy[i];

                if(nx<0 || ny<0 || nx>=N || ny>=N) continue;

                if(visited[nx][ny]) continue;

                if(board[nx][ny] == 1) {
                    q.add(new Point(nx,ny));
                    visited[nx][ny] = true;
                    sub_list.add(new Point(nx-x,ny-y)); // (0,0) 기준으로 마추기 위해
                }
            }
        }

        list.add(sub_list);
    }
}
```

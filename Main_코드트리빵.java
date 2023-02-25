package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_코드트리빵 {

	static int N,M,result,arrivedNum;
    static int[][] board;
    static boolean[][] visited;
    static boolean flag; // 격자에 사람들이 있는지 체크하는 변수
    // 방향 우선순위 : 상, 좌, 우, 하
    static int[] dx = {0,-1,0,0,1};
    static int[] dy = {0,0,-1,1,0};
    static class Person {
        int x,y; // 현재 위치
        int tx,ty; // 편의점 위치
        boolean arrived;
        public Person(int x, int y,int tx, int ty, boolean arrived) {
            this.x = x;
            this.y = y;
            this.tx = tx;
            this.ty = ty;
            this.arrived = arrived;
        }
    }
    static ArrayList<Person> waiting;
    static ArrayList<Integer> moving,removeList;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        board = new int[N][N];
        visited = new boolean[N][N];
        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        waiting = new ArrayList<>();
        waiting.add(new Person(-2,-2,-2,-2,false));
        moving = new ArrayList<>();
        removeList = new ArrayList<>();
        
        for(int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int tx = Integer.parseInt(st.nextToken())-1;
            int ty = Integer.parseInt(st.nextToken())-1;

            waiting.add(new Person(-1,-1,tx,ty,false));

            board[tx][ty] = 2; // 격자에 편의점 위치 2로 표시
        }

        result = 0;
        solve();

        System.out.println(result);
    }

    private static void solve() {
    	
        // 사람들이 아직 편의점에 도착하지 않았다면
        while(arrivedNum < M) { // 도착한 사람이 M명 미만이면 계속 진행
        	result++;
            // 현재 격자에 사람들이 있다면 1,2번 진행
            if(flag) {
            	// 1번. 편의점으로 한 칸 이동 -> 우선순위 : 상.좌.우.하
                // 현재 이동 중인 리스트 필요 !
                for(int i=0;i<moving.size();i++) {
                    move(moving.get(i));
                }

                // 2번. 만약 편의점에 도착했다면 이동을 멈추고 다른 사람이 못지나가게 표시
                moving.removeAll(removeList);
                removeList.clear();
                // 3번 진행
                if(result <= M)
                	find_baseCamp(result);
            }
            // 현재 격자에 사람들이 없다면 3번 진행
            else {
                flag = true;
                // result 시간에 해당하는 사람이 가려는 편의점과 가까운 베이스캠프로 이동함
                find_baseCamp(result);
            }
            
        }
    }
    
    private static void move(int num) {
    	// 현재 위치에서 편의점으로 향하는 최단거리 경로로 한 칸 이동
    	
    	// 현재 상황에서 갈 수 있는 최단경로 찾기
    	int sx = waiting.get(num).x;
    	int sy = waiting.get(num).y;
    	int ex = waiting.get(num).tx;
    	int ey = waiting.get(num).ty;
    	Queue<int[]> q = new LinkedList<>();
    	q.add(new int[] {sx,sy,0,0});
    	boolean[][] visit = new boolean[N][N];
    	copy(visit);
    	int dist = Integer.MAX_VALUE;
    	String direction = ""; // 최종 이동경로
    	
    	while(!q.isEmpty()) {
    		int[] temp = q.poll();
    		int x = temp[0];
    		int y = temp[1];
    		int cnt = temp[2];
    		int dir = temp[3]; // 이동 경로
    		
    		if(x == ex && y == ey) { // 편의점이라면
    			if(dist > cnt) {
    				dist = cnt;
    				direction = dir+ "";
    			} else if(dist == cnt) {
    				if(Double.parseDouble(direction) > dir) {
    					direction = dir + "";
    				}
    			}
    		}
    		
    		for(int i=1;i<=4;i++) {
    			int nx = x + dx[i];
    			int ny = y + dy[i];
    			
    			if(nx<0 || ny<0 || nx>=N || ny>=N) continue;

                if(visit[nx][ny]) continue;
                
                String s = dir+""+i;
                q.add(new int[] {nx,ny,cnt+1, (int) Double.parseDouble(s)});
                visit[nx][ny] = true;
    		}
    	}
    	
    	// 결정된 최단경로로 한 칸 이동
    	int d = direction.charAt(0)-'0';
    	waiting.get(num).x += dx[d];
    	waiting.get(num).y += dy[d];
//    	System.out.println(num+"번 사람 : "+waiting.get(num).x+" , "+ waiting.get(num).y);
    	// 한 칸 이동 후, 편의점에 도착했다면
    	if(waiting.get(num).x == waiting.get(num).tx && waiting.get(num).y == waiting.get(num).ty) {
    		arrivedNum++;
    		visited[waiting.get(num).x][waiting.get(num).y] = true;
    		removeList.add(num);
    	}
    }

    private static void find_baseCamp(int num) {
        // t분에 해당하는 사람이 가려는 편의점에서 가장 가까운 베이스캠프 찾아서 이동하기
        // 가장 가까운 베이스캠프가 여러 개라면 행이 작고 열이 작은 곳으로 이동

        // 목적지(편의점)의 위치
        int tx = waiting.get(num).tx;
        int ty = waiting.get(num).ty;

        Queue<int[]> q = new LinkedList<>();
        boolean[][] visit = new boolean[N][N];
        copy(visit);
        q.add(new int[] {tx,ty,0});
        visit[tx][ty] = true;
        int dist = Integer.MAX_VALUE;
        // 최종적으로 가야하는 베이스캠프의 위치
        int rx = Integer.MAX_VALUE;
        int ry = Integer.MAX_VALUE;

        while(!q.isEmpty()) {
            int[] temp = q.poll();
            int x = temp[0];
            int y = temp[1];
            int cnt = temp[2];

            if(board[x][y] == 1) {
                // 베이스캠프라면
                if(dist > cnt) {
                    rx = x;
                    ry = y;
                    dist = cnt;
                } else if(dist == cnt) {
                    // 행이 같다면
                    if(rx == x) ry = Math.min(ry,y);
                    // 행이 rx가 더 작으면
                    else if(rx < x) continue;
                    // x가 더 작으면
                    else if(rx > x) {
                        rx = x;
                        ry = y;
                    }
                }
            }

            for(int i=1;i<=4;i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(nx<0 || ny<0 || nx>=N || ny>=N) continue;

                if(visit[nx][ny]) continue;

                q.add(new int[] {nx,ny,cnt+1});
                visit[nx][ny] = true;
            }
        }

        // 가야 할 베이스캠프 위치가 선정됐다면
        // 앞으로 다른 사람들은 여기를 지나갈 수 없음
        visited[rx][ry] = true;
        // 현재 사람의 위치도 rx,ry로 업데이트
        waiting.get(num).x = rx;
        waiting.get(num).y = ry;
        moving.add(num);
//        System.out.println(rx + " , "+ ry);
    }

    private static void copy(boolean[][] arr) {
        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                arr[i][j] = visited[i][j];
            }
        }
    }

}

package algo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Solution_1868_파핑파핑_지뢰찾기{
	static char[][] map;
	static boolean[][] visited;
	static int[] dx = {0,1,0,-1, 1,1,-1,-1};
	static int[] dy = {1,0,-1,0, 1,-1,1,-1};
	static int N;
	static Queue<int[]> queue= new LinkedList<int[]>();
    
	public static void main(String[] args)throws Exception {
		System.setIn(new FileInputStream("input/input_1868.txt"));
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T=Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			N = Integer.parseInt(br.readLine());
			map= new char[N][N];
			visited = new boolean[N][N];
			for(int i=0;i<N;i++) {
				String tmp=br.readLine();
				for(int j=0 ; j<N; j++) {
					map[i][j]=tmp.charAt(j);
					if(map[i][j]=='*') visited[i][j]=true;

				}
			}
			// map에 주변 지뢰를 탐색
			for(int i=0 ; i<N ;i++) {
				for(int j=0; j<N ;j++) {
					int cnt=0;
					for(int k=0; k<8 ; k++) {
						int nx=i+dx[k];
						int ny=j+dy[k];
						if(nx<0 || ny<0 || nx>=N || ny>=N) continue;
						if(map[nx][ny]=='*') cnt+=1; 
					}		
					
					if(map[i][j]=='.')map[i][j]=(char)(cnt+'0');
				}
			}
			int total=0;
			//0 이 뭉쳐있는것들 방문처리, 0옆에 붙어있는거 방문처리
			for(int i=0 ; i<N ; i++) {
				for(int j=0 ;j<N; j++) {
					if(map[i][j]=='0' && visited[i][j]==false) {
						bfs(i,j);
						total+=1;
					}
				}
			}
			// 나머지 방문 안된것들만 더하기
			for(int i=0 ; i<N ; i++) {
				for(int j=0 ;j<N; j++) {
					if(visited[i][j]==false) {
						total+=1;
					}
				}
			}
			sb.append("#"+tc+" ").append(total).append("\n");
		}
		System.out.println(sb.toString());

	}
	private static void bfs(int i, int j) {
		// TODO Auto-generated method stub
		queue.add(new int[] {i,j});
		visited[i][j]=true;
		
		//bfs
		while(!queue.isEmpty()) {
			int[] cur=queue.poll();
			int x=cur[0];
			int y=cur[1];
			for(int k=0; k<8 ; k++) {
				int nx=x+dx[k];
				int ny=y+dy[k];
				if(nx<0 || ny<0 || nx>=N || ny>=N) continue;
				// 0일 경우
				if(visited[nx][ny]==false &&map[nx][ny]=='0') {
					visited[nx][ny]=true;
					queue.add(new int[] {nx,ny});
				}
				//0이 아닐경우
				else if(visited[nx][ny]==false && map[nx][ny]!='0') {
					visited[nx][ny]=true;
				}
			}
		}

	}

}
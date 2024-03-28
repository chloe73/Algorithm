package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_20006_랭킹전_대기열 {
	
	static int P,M;
	static PriorityQueue<Room> roomPq; // 현재 입장 가능한 room 정보
	static ArrayList<Room> roomList;
	static class Room implements Comparable<Room>{
		int madeTime; // 생성된 시간
		int level; // 처음 입장한 플레이어의 레벨을 기준으로 -10 +10
		ArrayList<Player> playerList;
		boolean isStarted;
		
		public Room(int madeTime, int level) {
			this.madeTime = madeTime;
			this.level = level;
			this.playerList = new ArrayList<>();
			this.isStarted = false;
		}
		
		public int compareTo(Room o) {
			return this.madeTime - o.madeTime;
		}
	}
	static class Player {
		int l;
		String n;
		public Player(int l, String n) {
			this.l = l;
			this.n = n;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		P = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		roomPq = new PriorityQueue<>();
		roomList = new ArrayList<>();
		
		int time = 0;
		for(int i=0;i<P;i++) {
			st = new StringTokenizer(br.readLine());
			
			int l = Integer.parseInt(st.nextToken());
			String n = st.nextToken();
			
			if(roomPq.isEmpty())	{
				Room room = new Room(time++, l);
				room.playerList.add(new Player(l, n));
				
				roomList.add(room);
				
				if(room.playerList.size() == M) {
					room.isStarted = true;
				}
				if(!room.isStarted)
					roomPq.add(room);
			}
			else {
				PriorityQueue<Room> pq = new PriorityQueue<>();
				
				boolean flag = false;
				while(!roomPq.isEmpty()) {
					Room tmp = roomPq.poll();
					
					// 들어갈 방을 찾은 경우
					if(tmp.level-10 <= l && l<= tmp.level+10) {
						flag = true;
						tmp.playerList.add(new Player(l, n));
						
						// 현재 들어간 방의 정원이 다 찬 경우 pq에 넣을 필요가 없음
						if(tmp.playerList.size() == M) {
							tmp.isStarted = true;
						}
						
						if(!tmp.isStarted) {
							pq.add(tmp);
						}
						
						break;
					}

					pq.add(tmp);
				}
				
				while(!roomPq.isEmpty()) {
					pq.add(roomPq.poll());
				}
				
				// 들어갈 방이 없거나 레벨이 맞는 방이 없는 경우
				if(!flag) {
					// 새로운 방 생성
					Room room = new Room(time++, l);
					room.playerList.add(new Player(l, n));
					
					roomList.add(room);
					
					if(room.playerList.size() == M) {
						room.isStarted = true;
					}
					if(!room.isStarted)
						pq.add(room);
				}
				
				roomPq = pq;
			}
		} // input end
		
		StringBuilder sb = new StringBuilder();
		// 방은 생성된 순서대로 출력한다.
		for(int i=0;i<roomList.size();i++) {
			Room tmp = roomList.get(i);
			
			// 방이 시작되었으면 Started!를 대기 중이면 Waiting!을 출력시킨다.
			if(tmp.isStarted) {
				sb.append("Started!"+"\n");
			}
			else {
				sb.append("Waiting!"+"\n");
			}
			
			// 방에 있는 플레이어들의 정보는 닉네임이 사전순으로 앞서는 플레이어부터 출력한다.
			tmp.playerList.sort((o1,o2) -> {
				return o1.n.compareTo(o2.n);
			});
			
			for(Player p : tmp.playerList) {
				sb.append(p.l+" "+p.n+"\n");
			}
		}
		
		System.out.println(sb.toString());
	}

}

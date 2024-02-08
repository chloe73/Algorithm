package algo.SW_DX.day18;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution_Pro_뉴스알림 {

	private static BufferedReader br;
    private static UserSolution usersolution = new UserSolution();
	
    private final static int INIT = 0;
    private final static int REGI = 1;
    private final static int OFFER = 2;
	private final static int CANCEL = 3;
	private final static int CHECK = 4;
	
    private static int gids[] = new int[30];
	private static int ansids[] = new int[3];
	private static int retids[] = new int[3];
	
    private static boolean run() throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");		
       
        int N, K, cmd, ans, ret;
		int time, num, uid, gid, nid, delay;
		
		int Q = Integer.parseInt(st.nextToken());
        boolean ok = false;

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            cmd = Integer.parseInt(st.nextToken());

            if (cmd == INIT) {
                N = Integer.parseInt(st.nextToken());
                K = Integer.parseInt(st.nextToken());

                usersolution.init(N, K);
                ok = true;
            } else if (cmd == REGI) {
                time = Integer.parseInt(st.nextToken());
                uid = Integer.parseInt(st.nextToken());
				num = Integer.parseInt(st.nextToken());				
				for (int m = 0; m < num; m++) {
					gids[m] = Integer.parseInt(st.nextToken());
				}

				usersolution.registerUser(time, uid, num, gids);
            } else if (cmd == OFFER) {
                time = Integer.parseInt(st.nextToken());
                nid = Integer.parseInt(st.nextToken());
				delay = Integer.parseInt(st.nextToken());
				gid = Integer.parseInt(st.nextToken());     
				ans = Integer.parseInt(st.nextToken());

				ret = usersolution.offerNews(time, nid, delay, gid);
				if (ans != ret) {
					ok = false;
				}
            }
			else if (cmd == CANCEL) {
				time = Integer.parseInt(st.nextToken());
				nid = Integer.parseInt(st.nextToken());

				usersolution.cancelNews(time, nid);
            }
			else if (cmd == CHECK) {
				time = Integer.parseInt(st.nextToken());
				uid = Integer.parseInt(st.nextToken());
				
				ret = usersolution.checkUser(time, uid, retids);
				
				ans = Integer.parseInt(st.nextToken());
				num = ans;
				if (num > 3) num = 3;
				for (int m = 0; m < num; m++) {
					ansids[m] = Integer.parseInt(st.nextToken());
				}
				if (ans != ret) {
					ok = false;
				}
				else {
					for (int m = 0; m < num; m++) {
						if (ansids[m] != retids[m]) {
							ok = false;
						}
					}
				}
            }
			else ok = false;
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {
        int T, MARK;

        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");
        T = Integer.parseInt(stinit.nextToken());
        MARK = Integer.parseInt(stinit.nextToken());

        long beforeTime = System.currentTimeMillis();
        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }
        long afterTime = System.currentTimeMillis(); 
    	long secDiffTime = (afterTime - beforeTime)/1000;
    	System.out.println("시간차이(m) : "+secDiffTime);
        br.close();
    }
}

class UserSolution {
	
	static HashMap<Integer, ArrayList<Integer>> channelMap;
	static HashMap<Integer, ArrayList<News>> userMap;
	static HashMap<Integer, News> newsMap;
	static PriorityQueue<News> newsPq;
	static class News implements Comparable<News>{
		int id, alarmTime, channelNum;
		boolean isDeleted, isSend;
		public News(int id, int alarmTime, int channelNum) {
			this.id = id;
			this.alarmTime = alarmTime;
			this.channelNum = channelNum;
			isDeleted = false;
			isSend = false;
		}
		
		public int compareTo(News o) {
			// mUID 유저가 같은 시간대에 2개 이상의 뉴스 알림을 받은 경우, 뉴스 ID 가 큰 경우 더 최신이다.
			if(this.alarmTime == o.alarmTime)
				return  this.id - o.id;
			return this.alarmTime-o.alarmTime;
		}
	}
	
	void init(int N, int K) {
		// 각 테스트 케이스의 처음에 호출된다.
		// N 명의 유저와 뉴스 알림 서비스를 제공하는 K 개의 뉴스 채널이 있다.
		// 현재 시각은 0 이고, 제공된 뉴스는 없다.
		userMap = new HashMap<>();
		channelMap = new HashMap<>();
		newsMap = new HashMap<>();
		newsPq = new PriorityQueue<>();
	}
	
	private void sendAlarm(int time) {
		
		while(!newsPq.isEmpty()) {
			if(newsPq.peek().alarmTime > time) return;
			
			News temp = newsPq.poll();
			
			if(temp.isDeleted) continue;
			
			for(int user : channelMap.get(temp.channelNum)) {
				userMap.get(user).add(temp);
			}
		}
	}

	void registerUser(int mTime, int mUID, int mNum, int mChannelIDs[]) {
		
		if(!userMap.containsKey(mUID)) {
			userMap.put(mUID, new ArrayList<>());
		}
		
		// mTime 시각에 mUID 유저는 뉴스 알림을 받기 위해 mNum 개의 뉴스 채널 mChannelIDs[] 에 각각 등록한다.
		// mTime 시각에 유저에게 보내지는 뉴스 알림이 있는 경우 먼저 알림을 보낸 후, mUID 유저를 뉴스 채널에 등록한다.
		sendAlarm(mTime);
		
		// mChannelIDs[] 뉴스 채널들은 서로 중복되지 않는다.
		// mUID 는 중복으로 주어질 수 있지만, 동일 유저에게 등록되는 뉴스 채널은 서로 중복되지 않는다.
		for(int i=0;i<mNum;i++) {
			if(!channelMap.containsKey(mChannelIDs[i])) {
				channelMap.put(mChannelIDs[i], new ArrayList<>());
			}
			
			channelMap.get(mChannelIDs[i]).add(mUID);
		}
		
	}

	int offerNews(int mTime, int mNewsID, int mDelay, int mChannelID) {
		// mTime 시각에 mDelay 시간이 있는 mNewsID 뉴스가 mChannelID 뉴스 채널에 제공되고, mChannelID 뉴스 채널에 알림을 등록한 유저의 수를 반환한다.
		// mChannelID 뉴스 채널은 (mTime + mDelay) 시각에 뉴스 알림을 등록한 유저들에게 mNewsID 뉴스 알림을 보낸다.
		// mChannelID 뉴스 채널에 알림 등록한 유저가 1 명 이상 있음이 보장된다.
		// mNewsID 는 중복으로 주어지지 않는다.
		News temp = new News(mNewsID, mTime+mDelay, mChannelID);
		newsMap.put(mNewsID, temp);
		newsPq.add(temp);

		// mChannelID 뉴스 채널에 알림 등록한 유저의 수
		return channelMap.get(mChannelID).size();
	}

	void cancelNews(int mTime, int mNewsID) {
		// mTime 시각에 mNewsID 뉴스가 취소되어 삭제된다.
		// mNewsID 뉴스는 offerNews() 에서 제공된 뉴스이다.
		// mNewsID 뉴스가 유저들에게 뉴스 알림이 보내 졌으면 유저에게 있는 mNewsID 뉴스 알림도 삭제되어야 한다.
		// mNewsID 뉴스는 이미 취소되어 삭제된 뉴스일 수도 있다.
		 newsMap.get(mNewsID).isDeleted = true;
	}

	int checkUser(int mTime, int mUID, int mRetIDs[]) {
		// mTime 시각에 mUID 유저가 받은 뉴스 알림의 개수를 반환하고, 
		// mUID 유저가 받은 뉴스 알림이 없을 경우 0 을 반환한다.
		// registerUser() 에서 알림을 등록한 유저임이 보장된다.
		 
		int ret = 0;
		
		// mTime 시각에 유저에게 보내지는 뉴스 알림이 있는 경우 먼저 알림을 보낸 후, 유저가 받은 뉴스 알림의 개수를 반환한다.
		sendAlarm(mTime);
		
		// userMap 에서 알림 확인할 때 각 뉴스의 isDeleted 값 확인하기
		// 만약 삭제된 알림인 경우에는 pass
		int idx = 0;
		ArrayList<News> list = userMap.get(mUID);
		int size = list.size();
		while(size > 0) {
			News temp = list.get(size-1);
			
			if(temp.isDeleted) {
				size--;
				continue;
			}
			
			ret++;
			size--;
			if(idx < 3) {
				mRetIDs[idx++] = temp.id;
			}
		}
		
		// 함수 호출 후, mUID 유저가 받은 뉴스 알림은 모두 삭제되어 알림의 개수는 0 이 된다.
		userMap.get(mUID).clear();
		
		//  mUID 유저가 받은 뉴스 알림의 개수 (삭제된 뉴스 알림은 제외 )
		return ret;
	}
}
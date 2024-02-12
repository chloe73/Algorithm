package algo.SW_DX.day4_Heap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution_Pro_Social_Media {

	private static int mSeed;
	private static int pseudo_rand() {
		mSeed = mSeed * 431345 + 2531999;
		return mSeed & 0x7FFFFFFF;
	}

	private static int follow_status[][] = new int[1005][1005];
	private static int answer_score;
	private static int n; // n >= 5 && n <= 1000
	private static int end_timestamp;
	private static int follow_ratio; // follow_ratio >= 1 && follow_ratio <= 10000
	private static int make_ratio; // make_ratio >= 1 && make_ratio <= 10000
	private static int like_ratio; // like_ratio >= 1 && like_ratio <= 10000
	private static int get_feed_ratio; // get_feed_ratio >= 1 && get_feed_ratio <= 10000
	private static int post_arr[] = new int[200000];
	private static int total_post_cnt;
	private static int min_post_cnt;
	
	private static BufferedReader br;
	private static UserSolution user = new UserSolution();

	private static boolean run() throws Exception {
		int uId1;
		int uId2;
		int pId;
		int pIdList[] = new int[10];
		int ans_pIdList[] = new int[10];
		int rand_val;
		boolean ret = true;

		StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");
		mSeed = Integer.parseInt(stdin.nextToken());
		n = Integer.parseInt(stdin.nextToken());
		end_timestamp = Integer.parseInt(stdin.nextToken());
		follow_ratio = Integer.parseInt(stdin.nextToken());
		make_ratio = Integer.parseInt(stdin.nextToken());
		like_ratio = Integer.parseInt(stdin.nextToken());
		get_feed_ratio = Integer.parseInt(stdin.nextToken());
		
		user.init(n);

		for (uId1 = 1; uId1 <= n; uId1++)
		{
			follow_status[uId1][uId1] = 1;
			int m = n / 10 + 1;
			if (m > 10)
				m = 10;
			for (int i = 0; i < m; i++)
			{
				uId2 = uId1;
				while (follow_status[uId1][uId2] == 1)
				{
					uId2 = pseudo_rand() % n + 1;
				}
				user.follow(uId1, uId2, 1);
				follow_status[uId1][uId2] = 1;
			}
		}
		min_post_cnt = total_post_cnt = 1;

		for (int timestamp = 1; timestamp <= end_timestamp; timestamp++)
		{
			rand_val = pseudo_rand() % 10000;
			if (rand_val < follow_ratio)
			{
				uId1 = pseudo_rand() % n + 1;
				uId2 = pseudo_rand() % n + 1;
				int lim = 0;
				while (follow_status[uId1][uId2] == 1 || uId1 == uId2)
				{
					uId2 = pseudo_rand() % n + 1;
					lim++;
					if (lim >= 5)
						break;
				}
				if (follow_status[uId1][uId2] == 0)
				{
					user.follow(uId1, uId2, timestamp);
					follow_status[uId1][uId2] = 1;
				}
			}
			rand_val = pseudo_rand() % 10000;

			if (rand_val < make_ratio)
			{
				uId1 = pseudo_rand() % n + 1;
				post_arr[total_post_cnt] = timestamp;

				user.makePost(uId1, total_post_cnt, timestamp);
				total_post_cnt += 1;
			}

			rand_val = pseudo_rand() % 10000;

			if (rand_val < like_ratio && total_post_cnt - min_post_cnt > 0)
			{
				while (post_arr[min_post_cnt] < timestamp - 1000 && min_post_cnt < total_post_cnt)
					min_post_cnt++;

				if (total_post_cnt != min_post_cnt)
				{
					pId = pseudo_rand() % (total_post_cnt - min_post_cnt) + min_post_cnt;
					user.like(pId, timestamp);
				}
			}

			rand_val = pseudo_rand() % 10000;
			if (rand_val < get_feed_ratio && total_post_cnt > 0)
			{
				uId1 = pseudo_rand() % n + 1;
				user.getFeed(uId1, timestamp, pIdList);
				stdin = new StringTokenizer(br.readLine(), " ");

				for (int i = 0; i < 10; i++)
				{
					ans_pIdList[i] = Integer.parseInt(stdin.nextToken()); 
				}

				for (int i = 0; i < 10; i++)
				{
					if (ans_pIdList[i] == 0)
						break;

					if (ans_pIdList[i] != pIdList[i])
					{
						ret = false;
					}
				}
			}
		}

		return ret;
	}
	
	public static void main(String[] args) throws Exception {
//		System.setIn(new java.io.FileInputStream("eval_input.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc;
		StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");
		tc = Integer.parseInt(stdin.nextToken());
		answer_score = Integer.parseInt(stdin.nextToken());

		for (int t = 1; t <= tc; t++) {
			int score;
			for (int i = 0; i < 1005; i++)
				for (int j = 0; j < 1005; j++)
					follow_status[i][j] = 0;

			if (run())
				score = answer_score;
			else
				score = 0;

			System.out.println("#" + t + " " + score);
		}
	}
}

// 10개 테스트케이스를 합쳐서 C++의 경우 1초 / Java의 경우 2초
class UserSolution {
	
	static HashMap<Integer, User> userMap; // key : uID
	static HashMap<Integer, Integer> postUserMap; // key : pID, value : uID
	static HashMap<Integer, Post> postMap; // total post data // key : pID
	static class User {
		int id;
		ArrayList<Post> postList;
		HashSet<Integer> followSet;
		
		public User(int id) {
			this.id = id;
			followSet = new HashSet<>();
			postList = new ArrayList<>();
		}
	}
	static class Post implements Comparable<Post>{
		int uid,pid,like,timestamp;
		
		public Post(int uid, int pid, int like, int timestamp) {
			this.uid = uid;
			this.pid = pid;
			this.like = like;
			this.timestamp = timestamp;
		}
		
		public int compareTo(Post o) {
			if(this.like == o.like) return o.timestamp - this.timestamp;
			return o.like - this.like;
		}
	}
	static class Post2 implements Comparable<Post2>{
		int uid,pid,like,timestamp;
		
		public Post2(int uid, int pid, int like, int timestamp) {
			this.uid = uid;
			this.pid = pid;
			this.like = like;
			this.timestamp = timestamp;
		}
		public int compareTo(Post2 o) {
			return o.timestamp - this.timestamp;
		}
	}
	
	public void init(int N) {
		// 각 testcase 시작 시 초기화를 위해 1번 호출된다.

		userMap = new HashMap<>();
		postUserMap = new HashMap<>();
		postMap = new HashMap<>();
		
		// Parameters
		// N: 사용자 수 (2 ≤ N ≤ 1,000)
	}

	public void follow(int uID1, int uID2, int timestamp) {
		// “uID1” 사용자가 “uID2” 사용자를 “follow” 한다.
		// “uID1” 사용자는 “uID2” 사용자의 모든 게시글을 볼 수 있다.
		
		if(!userMap.containsKey(uID1)) {
			userMap.put(uID1, new User(uID1));
		}
		userMap.get(uID1).followSet.add(uID2);
		
		if(!userMap.containsKey(uID2)) {
			userMap.put(uID2, new User(uID2));
		}
		
		// Parameters
		// uID1, uID2 : 사용자의 id (1 ≤ uID1, uID2 ≤ N)
		// timestamp : 현재 시점의 “timestamp” (1 ≤ timestamp ≤ 100,000)
	}

	public void makePost(int uID, int pID, int timestamp) {
		// “uID” 사용자가 “pID” 게시글을 게시한다.

		if(!userMap.containsKey(uID)) {
			userMap.put(uID, new User(uID));
		}
		
		postUserMap.put(pID, uID);
		Post post = new Post(uID, pID, 0, timestamp);
		postMap.put(pID, post);
		userMap.get(uID).postList.add(post);
		
		// Parameters
		// uID : 사용자의 ID (1 ≤ uID ≤ N)
		// pID : 게시글의 ID ( 1 부터 오름차순으로 주어진다. )
		// timestamp : 현재 시점의 “timestamp” (1 ≤ timestamp ≤ 100,000)
	}

	public void like(int pID, int timestamp) {
		// “pID” 게시글에 “like” 를 1 번 추가 한다.
		// “pID” 는 makePost() 에서 전달되었던 값으로만 주어 진다.
		
		postMap.get(pID).like++;
		
		// Parameters
		// pID : “like” 를 추가할 게시글의 pID
		// timestamp : 현재 시점의 “timestamp” (1 ≤ timestamp ≤ 100,000)
	}

	public void getFeed(int uID, int timestamp, int pIDList[]) {
		// 현재 “timestamp” 를 기준으로 “uID” 사용자에게 보여지는 최대 10 개의 게시글의 “pID” 들을 찾아 우선순위의 내림차순으로 “pIDList[]” 배열에 저장하여 반환 한다.

		PriorityQueue<Post> pq = new PriorityQueue<>(); // 1000초 이내인 것들
		PriorityQueue<Post2> pq2 = new PriorityQueue<>(); // 1000초 지난 것들
		
		// 본인 게시물 포함
		for(int i=userMap.get(uID).postList.size()-1;i>=0;i--) {
			Post p = userMap.get(uID).postList.get(i);
			
			if(timestamp - p.timestamp <= 1000) {
				pq.add(p);
			}
			else {
				pq2.add(new Post2(p.uid, p.pid, p.like, p.timestamp));
			}
		}
		
		// uID가 follow한 user 정보
		for(int follow : userMap.get(uID).followSet) {
			
			for(int i=userMap.get(follow).postList.size()-1;i>=0;i--) {
				Post p = userMap.get(follow).postList.get(i);
				
				if(timestamp - p.timestamp <= 1000) {
					pq.add(p);
				}
				else {
					pq2.add(new Post2(p.uid, p.pid, p.like, p.timestamp));
				}
			}
		}
		
		int cnt = 0;
		
		while(!pq.isEmpty()) {
			pIDList[cnt++] = pq.poll().pid;
			if(cnt == 10) break;
		}
		
		if(cnt < 10 && pq2.size() > 0) {
			while(!pq2.isEmpty()) {
				pIDList[cnt++] = pq2.poll().pid;
				if(cnt == 10) break;
			}
		}
		// Parameters
		// uID : 사용자의 id (1 ≤ uID ≤ N)
		// timestamp : 현재 시점의 timestamp (1 ≤ timestamp ≤ 100,000)
		// pIDList[] : 보여지는 게시글의 pID 들을 저장하는 배열
	}
}
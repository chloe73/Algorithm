package algo.SW_DX.day24;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeSet;

class Solution_Pro_단어미로 {
	private static BufferedReader br;
    private static UserSolution usersolution = new UserSolution();
	
    private final static int INIT = 0;
    private final static int ADD = 1;
    private final static int SET = 2;
	private final static int MOVE = 3;
	private final static int CHANGE = 4;
	
	private static final int MAX_LENGTH = 11 + 1;
	
    private static int dir[] = new int[3];
	private static char mWord[] = new char[MAX_LENGTH];
	private static char mRetWord[] = new char[MAX_LENGTH];
	
	private static void String2Char(String s, char[] b) {
		int n = s.length();
		for (int i = 0; i < n; ++i)
			b[i] = s.charAt(i);
		for (int i = n; i < MAX_LENGTH; ++i)
			b[i] = '\0';
	}
	
    private static boolean run() throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");		
       
		int cmd, ans, ret, id;
		
		int Q = Integer.parseInt(st.nextToken());
        boolean ok = false;

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            cmd = Integer.parseInt(st.nextToken());

            if (cmd == INIT) {
                usersolution.init();
                ok = true;
            } else if (cmd == ADD) {
				id = Integer.parseInt(st.nextToken());
            	String2Char(st.nextToken(), mWord);
                dir[0] = Integer.parseInt(st.nextToken());
                dir[1] = Integer.parseInt(st.nextToken());
                dir[2] = Integer.parseInt(st.nextToken());

				usersolution.addRoom(id, mWord, dir);
            } else if (cmd == SET) {
            	String2Char(st.nextToken(), mWord);

				usersolution.setCurrent(mWord);
            }
			else if (cmd == MOVE) {
				dir[0] = Integer.parseInt(st.nextToken());
				ans = Integer.parseInt(st.nextToken());

				ret = usersolution.moveDir(dir[0]);
				if (ret != ans) {
					ok = false;
				}
            }
			else if (cmd == CHANGE) {
				String2Char(st.nextToken(), mWord);
				String2Char(st.nextToken(), mRetWord);
                dir[0] = Integer.parseInt(st.nextToken());
                dir[1] = Integer.parseInt(st.nextToken());
                dir[2] = Integer.parseInt(st.nextToken());
                usersolution.changeWord(mWord, mRetWord, dir);
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

        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }

        br.close();
    }
}

class UserSolution {
	
	static String temp; // 현재 위치로 설정된 방
	static HashMap<String, TreeSet<Word>> dictionary; // 각 단어의 문자열 집합
	static HashMap<String, Word> wordMap;// 모든 단어 집합
	static class Word implements Comparable<Word>{
		int id;
		String w;
		String front, mid, end;
		boolean isDead;
		
		public Word(int id, String w, String front, String mid, String end, boolean isDead) {
			this.id = id;
			this.w = w;
			this.front = front;
			this.mid = mid;
			this.end = end;
			this.isDead = isDead;
		}
		
		public int compareTo(Word o) {
			return this.w.compareTo(o.w); // 문자열 사전 순 정렬
		}
	}
	
	void init() {
		// 각 테스트 케이스의 처음에 호출된다.
		// 단어 미로에 방의 개수는 0 이다.
		// 현재 위치로 설정된 방이 없다.
		temp = "";
		wordMap = new HashMap<>();
		dictionary = new HashMap<>();
	}

	void addRoom(int mID, char mWord[], int mDirLen[]) {
		// 각 테스트 케이스에서 addRoom() 함수의 호출 횟수는 최대 30,000 이다.
		
		// 단어 미로에 방 번호가 mID 이고, mWord[] 단어가 있는 방이 추가된다.
		// mDirLen[0]은 앞 방향 숫자를, mDirLen[1]은 중간 방향 숫자를, mDirLen[2]는 뒤 방향 숫자를 나타낸다.
		// 앞 방향 숫자, 뒤 방향 숫자는 2 또는 4 이고, 중간 방향 숫자는 3 이다.
		// mID는 맨 처음 호출할 때 1이고 그 다음 호출할 때마다 1씩 증가한다.
		// mWord[]는 ‘\0’으로 끝나는 영어 소문자로 구성된 문자열이고, ‘\0’ 제외한 길이가 11이다.
		// mWord[]는 단어 미로에 있는 단어와 중복되지 않는다.
		
		String key = "";
		for(char c : mWord) {
			if(c == '\0') break;
			
			key += c;
		}
		
		int f = mDirLen[0];
		String front = "";
		String a1 = "";
		String a2 = "";
		for(int i=0;i<4;i++) {
			if(i <= 1) {
				a1 += key.charAt(i);
				a2 += key.charAt(i);				
			}
			else {
				a2 += key.charAt(i);
			}
			
			if(i == 1 && f == 2) {
				front = a1;
				
			}
			if(i == 3 && f == 4) {
				front = a2;
				break;
			}
		}
		
		String mid = "";
		for(int i=4;i<7;i++) {
			mid += key.charAt(i);
		}
		
		int e = mDirLen[2];
		String end = "";
		String b1 = ""; // 끝에 2개
		String b2 = ""; // 끝에 4개
		for(int i=7;i<11;i++) {
			if(i <= 8) {
				b2 += key.charAt(i);				
			}
			else {
				b1 += key.charAt(i);
				b2 += key.charAt(i);
			}
			
			if(i == 10 && e == 2) {
				end = b1;
				break;
			}
			if(i == 10 && e == 4) {
				end = b2;
				break;
			}
		}
				
		Word word = new Word(mID, key, front, mid, end, false);
		wordMap.put(key, word);
		
		// dictionary에 추가
		if(!dictionary.containsKey(a1)) {
			dictionary.put(a1, new TreeSet<>());
		}
		dictionary.get(a1).add(word);
		
		if(!dictionary.containsKey(a2)) {
			dictionary.put(a2, new TreeSet<>());
		}
		dictionary.get(a2).add(word);
		
		if(!dictionary.containsKey(mid)) {
			dictionary.put(mid, new TreeSet<>());
		}
		dictionary.get(mid).add(word);
		
		if(!dictionary.containsKey(b1))	{
			dictionary.put(b1, new TreeSet<>());
		}
		dictionary.get(b1).add(word);
		
		if(!dictionary.containsKey(b2)) {
			dictionary.put(b2, new TreeSet<>());
		}
		dictionary.get(b2).add(word);
		
	}

	void setCurrent(char mWord[]) {
		// 각 테스트 케이스에서 setCurrent() 함수의 호출 횟수는 최대 500 이다.
		
		// 단어 미로에 mWord[] 가 있는 방이 현재 위치가 된다.
		// mWord[]는 ‘\0’으로 끝나는 영어 소문자로 구성된 문자열이고, ‘\0’ 제외한 길이가 11이다.
		// mWord[] 단어가 미로에 있음이 보장된다.
		
		temp = "";
		for(char c : mWord) {
			if(c == '\0') break;
			
			temp += c;
		}
	}

	int moveDir(int mDir) {
		// 각 테스트 케이스에서 moveDir() 함수의 호출 횟수는 최대 50,000 이다.
		
		// 단어 미로의 현재 위치 방에서 mDir 방향으로 이동하고, 이동한 경우 이동한 방의 번호를 반환한다.
		// mDir 방향으로 이동 가능한 방이 없어 이동이 실패할 경우 0 을 반환한다.
		// 이동할 때 현재 위치 방은 제외되어, 다시 현재 위치 방으로 이동하지 않는다.
		// mDir = 0, 앞 방향으로 이동한다.
		// mDir = 1, 중간 방향으로 이동한다.
		// mDir = 2, 뒤 방향으로 이동한다.
		// 이동 가능한 방이 여러 개 일 경우, 방의 단어가 사전 순으로 가장 빠른 방으로 이동한다.
		// 현재 위치로 설정된 방이 있음이 보장된다.
		int ret = 0;
		
		Word tempLoc = wordMap.get(temp);
		PriorityQueue<Word> pq = new PriorityQueue<>();
		
		if(mDir == 0) {
			String key = tempLoc.front;
			if(dictionary.containsKey(key)) {
				for(Word w : dictionary.get(key)) {
					if(w.w.equals(temp)) continue;
					if(w.w.endsWith(key) && !w.isDead) {
						pq.add(w);
					}
				}
			}
			else return 0;
		}
		else if(mDir == 1) {
			String key = tempLoc.mid;
			
			if(dictionary.containsKey(key)) {
				for(Word w : dictionary.get(key)) {
					if(w.w.equals(temp)) continue;
					if(w.mid.equals(key) && !w.isDead) {
						pq.add(w);
					}
				}
			}
			else return 0;
		}
		else {
			String key = tempLoc.end;
			
			if(dictionary.containsKey(key)) {
				for(Word w : dictionary.get(key)) {
					if(w.w.equals(temp)) continue;
					if(w.w.startsWith(key) && !w.isDead) {
						pq.add(w);
					}
				}
			}
			else return 0;
		}
		
		if(!pq.isEmpty()) {
			Word next = pq.poll();
			ret = next.id;
			temp = next.w;
		}
		
		return ret;
	}

	void changeWord(char mWord[], char mChgWord[], int mChgLen[]) {
		// 각 테스트 케이스에서 changeWord() 함수의 호출 횟수는 최대 3,000 이다.
		
		// 단어 미로에서 단어가 mWord[] 인 방을 찾고, 그 방의 단어를 mChgWord[] 로 변경한다.
		// 또한, mChgLen[0]은 앞 방향 숫자로, mChgLen[1]은 중간 방향 숫자로, mChgLen[2]는 뒤 방향 숫자로 변경한다.
		// 단어 변경으로 단어 미로의 이동은 변경 될 수 있다.
		// 앞 방향 숫자, 뒤 방향 숫자는 2 또는 4 이고, 중간 방향 숫자는 3 이다.
		// mChgWord[]는 ‘\0’으로 끝나는 영어 소문자로 구성된 문자열이고, ‘\0’ 제외한 길이가 11이다.
		// 단어 미로에 mWord[] 의 방이 있음이 보장된다.
		// mChgWord[]는 mWord[]와 다르고, 단어 미로에 있는 단어와 중복되지 않는다.
		// 방 번호는 변경되지 않는다.
		
		// 기존 단어
		String key = "";
		for(char c : mWord) {
			if(c == '\0') break;
			
			key += c;
		}
		
		// 새로 변경할 단어
		String after = "";
		for(char c : mChgWord) {
			if(c == '\0') break;
			
			after += c;
		}
		
		// 만약 바꾸려는 단어가 현재 위치라면 temp도 변경해줘야 함.
		if(temp.equals(key))
			temp = after;
		
		// 기존 방 번호
		int beforeId = wordMap.get(key).id;
		
		int f = mChgLen[0];
		String front = "";
		String a1 = "";
		String a2 = "";
		for(int i=0;i<4;i++) {
			if(i <= 1) {
				a1 += after.charAt(i);
				a2 += after.charAt(i);				
			}
			else {
				a2 += after.charAt(i);
			}
			
			if(i == 1 && f == 2) {
				front = a1;
			}
			if(i == 3 && f == 4) {
				front = a2;
				break;
			}
		}
		
		String mid = "";
		for(int i=4;i<7;i++) {
			mid += after.charAt(i);
		}
		
		int e = mChgLen[2];
		String end = "";
		String b1 = ""; // 끝에 2개
		String b2 = ""; // 끝에 4개
		for(int i=7;i<11;i++) {
			if(i <= 8) {
				b2 += after.charAt(i);				
			}
			else {
				b1 += after.charAt(i);
				b2 += after.charAt(i);
			}
			
			if(i == 10 && e == 2) {
				end = b1;
				break;
			}
			if(i == 10 && e == 4) {
				end = b2;
				break;
			}
		}
		
		Word word = new Word(beforeId, after, front, mid, end, false);
		wordMap.put(after, word);
		wordMap.get(key).isDead = true;
		
		// dictionary에 추가
		if(!dictionary.containsKey(a1)) {
			dictionary.put(a1, new TreeSet<>());
		}
		dictionary.get(a1).add(word);
		
		if(!dictionary.containsKey(a2)) {
			dictionary.put(a2, new TreeSet<>());
		}
		dictionary.get(a2).add(word);
		
		if(!dictionary.containsKey(mid)) {
			dictionary.put(mid, new TreeSet<>());
		}
		dictionary.get(mid).add(word);
		
		if(!dictionary.containsKey(b1))	{
			dictionary.put(b1, new TreeSet<>());
		}
		dictionary.get(b1).add(word);
		
		if(!dictionary.containsKey(b2)) {
			dictionary.put(b2, new TreeSet<>());
		}
		dictionary.get(b2).add(word);
		
	}
}
package algo.SW_DX.day11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;

class Solution_Pro_성적조회 {
	private final static int CMD_INIT = 100;
	private final static int CMD_ADD = 200;
	private final static int CMD_REMOVE = 300;
	private final static int CMD_QUERY = 400;

	private final static UserSolution usersolution = new UserSolution();

	private static void String2Char(char[] buf, String str) {
		for (int k = 0; k < str.length(); ++k)
			buf[k] = str.charAt(k);
		buf[str.length()] = '\0';
	}
	private static boolean run(BufferedReader br) throws Exception {
		int q = Integer.parseInt(br.readLine());

		int id, grade, score;
		int cmd, ans, ret;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			cmd = Integer.parseInt(st.nextToken());
			switch (cmd) {
				case CMD_INIT:
					usersolution.init();
					okay = true;
					break;
				case CMD_ADD:
					char[] gender = new char[7];
					id = Integer.parseInt(st.nextToken());
					grade = Integer.parseInt(st.nextToken());
					String2Char(gender, st.nextToken());
					score = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.add(id, grade, gender, score);
					if (ret != ans)
						okay = false;
					break;
				case CMD_REMOVE:
					id = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.remove(id);
					if (ret != ans)
						okay = false;
					break;
				case CMD_QUERY:
					int gradeCnt, genderCnt;
					int[] gradeArr = new int[3];
					char[][] genderArr = new char[2][7];
					gradeCnt = Integer.parseInt(st.nextToken());
					for (int j = 0; j < gradeCnt; ++j) {
						gradeArr[j] = Integer.parseInt(st.nextToken());
					}
					genderCnt = Integer.parseInt(st.nextToken());
					for (int j = 0; j < genderCnt; ++j) {
						String2Char(genderArr[j], st.nextToken());
					}
					score = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.query(gradeCnt, gradeArr, genderCnt, genderArr, score);
					if (ret != ans)
						okay = false;
					break;
				default:
					okay = false;
					break;
			}
		}
		return okay;
	}

	public static void main(String[] args) throws Exception {
		int TC, MARK;

		//System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		TC = Integer.parseInt(st.nextToken());
		MARK = Integer.parseInt(st.nextToken());

		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(br) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		br.close();
	}
}

class UserSolution {
	
	static HashMap<Integer, int[]> studentMap;
	static TreeSet<Student>[][] board;
	static class Student implements Comparable<Student>{
		int id, grade, gender, score;
		public Student(int id, int grade, int gender, int score) {
			this.id = id;
			this.grade = grade;
			this.gender = gender;
			this.score = score;
		}
		public int compareTo(Student o) {
			if(this.score == o.score) return this.id - o.id;
			return this.score - o.score;
		}
	}
	
	public void init() {
		board = new TreeSet[3][2];
		for(int i=0;i<3;i++) {
			for(int j=0;j<2;j++) {
				board[i][j] = new TreeSet<>();
			}
		}
		
		studentMap = new HashMap<>();
		
		return;
	}

	public int add(int mId, int mGrade, char mGender[], int mScore) {
		int grade = mGrade-1;
		int gender = mGender[0] == 'm' ? 0 : 1;
		studentMap.put(mId, new int[] {grade, gender, mScore});
		board[grade][gender].add(new Student(mId, mGrade, gender, mScore));
		
		// mGrade학년 mGender인 학생 중에서 점수가 가장 높은 학생의 ID를 반환한다.
		
		return board[grade][gender].last().id;
	}

	public int remove(int mId) {
		// 학생 ID가 mId인 학생의 기록을 삭제한다.
		// 시스템에 mId 학생의 점수가 기록되어 있지 않다면, 0을 반환한다.
		if(!studentMap.containsKey(mId)) return 0;
		
		int[] temp = studentMap.get(mId);
		int grade = temp[0];
		int gender = temp[1];
		int score = temp[2];
		
		board[grade][gender].remove(new Student(mId, grade, gender, score));
		studentMap.remove(mId);

		// 삭제 후에, 학년과 성별이 동일한 학생이 없다면, 0을 반환한다.
		if(board[grade][gender].size() == 0) return 0;

		// 삭제 후에 mId 학생의 학년과 성별이 동일한 학생 중에서 점수가 가장 낮은 학생의 ID를 반환한다.
		// 점수가 가장 낮은 학생이 여러 명이라면, 그 중에서 ID가 가장 작은 값을 반환한다.
		return board[grade][gender].first().id;
	}

	public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
		// mGrade 학년 집합과 mGender 성별 집합에 속하면서, 점수가 mScore 이상인 학생 중에서 점수가 가장 낮은 학생의 ID를 반환한다.
		// 점수가 가장 낮은 학생이 여러 명이라면, 그 중에서 ID가 가장 작은 값을 반환한다.
		Student ret = new Student(0, 0, 0, Integer.MAX_VALUE);
		
		for(int i=0;i<mGradeCnt;i++) {
			int grade = mGrade[i]-1;
			for(int j=0;j<mGenderCnt;j++) {
				int gender = mGender[j][0] == 'm' ? 0 : 1;
				Student s = board[grade][gender].higher(new Student(0, grade, gender, mScore));
				if(s != null) {
					if(s.compareTo(ret) < 0) {
						ret = s;
					}
				}
			}
		}
		
		return ret.id;
	}
}
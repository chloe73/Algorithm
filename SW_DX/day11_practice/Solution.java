package algo.SW_DX.day11_practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeSet;

class Solution {
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
	
	/*
	우선순위 (remove, query에서는 같은 기준)
	add에서만 우선순위 아예 반대임
	1. 점수 가장 낮은 학생
	2. Id 가장 낮은 학생
	////////
	3. 점수 가장 높은 학생
	4. Id 가장 높은 학생
	*/
	
	static HashMap<Integer, Student> studentMap;
	static TreeSet<Student>[][] studentBoard;
	static class Student implements Comparable<Student> {
		int id,grade,gender,score;
		
		public Student(int id, int grade, int gender, int score) {
			this.id = id;
			this.grade = grade;
			this.gender = gender;
			this.score = score;
		}
		
		public int compareTo(Student o) {
			// 점수가 같으면 가장 낮은 id return
			if(this.score == o.score)
				return this.id - o.id;
			// 점수가 가장 낮은 학생 return
			return this.score - o.score;
		}
	}
	
	public void init() {
		// 각 테스트 케이스의 처음에 호출된다.
		// 등록된 성적이 없는 상태가 된다.
		
		studentMap = new HashMap<>();
		studentBoard = new TreeSet[4][2];
		for(int i=0;i<4;i++) {
			for(int j=0;j<2;j++) {
				studentBoard[i][j] = new TreeSet<>();
			}
		}
		
		return;
	}

	public int add(int mId, int mGrade, char mGender[], int mScore) {
		
		// 학생 ID가 mId이고, 학년이 mGrade, 성별이 mGender인 학생의 점수 mScore를 추가한다.
		// 성별의 경우, 남성은 “male”, 여성은 “female”로 주어지고, ‘＼0’ 문자로 끝나는 문자열이다.
		// mGrade학년 mGender인 학생 중에서 점수가 가장 높은 학생의 ID를 반환한다.
		// 점수가 가장 높은 학생이 여러 명이라면, 그 중에서 ID가 가장 큰 값을 반환한다.
		// 시스템에 이미 점수가 기록되어 있는 학생의 ID는 입력으로 주어지지 않는다. 하지만 기록이 삭제된 후에 다시 추가될 수는 있다.
		int gender = mGender[0] == 'm' ? 0 : 1;
		Student s = new Student(mId, mGrade, gender, mScore);
		
		studentMap.put(mId, s);
		studentBoard[mGrade][gender].add(s);

		// Returns
		// mGrade학년 mGender인 학생 중에서 점수가 가장 높은 학생의 ID를 반환한다.
		return studentBoard[mGrade][gender].last().id;
	}

	public int remove(int mId) {
		
		// 학생 ID가 mId인 학생의 기록을 삭제한다.
		// 시스템에 mId 학생의 점수가 기록되어 있지 않다면, 0을 반환한다.
		if(!studentMap.containsKey(mId)) return 0;
		
		// 삭제 후에 mId 학생의 학년과 성별이 동일한 학생 중에서 점수가 가장 낮은 학생의 ID를 반환한다.
		// 점수가 가장 낮은 학생이 여러 명이라면, 그 중에서 ID가 가장 작은 값을 반환한다.
		// 삭제 후에, 학년과 성별이 동일한 학생이 없다면, 0을 반환한다.
		int grade = studentMap.get(mId).grade;
		int gender = studentMap.get(mId).gender;
		studentBoard[grade][gender].remove(studentMap.get(mId));
		studentMap.remove(mId);
		
		if(studentBoard[grade][gender].size() == 0 ) return 0;
		
		return studentBoard[grade][gender].first().id;
	}

	public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
		
		// mGrade 학년 집합과 mGender 성별 집합에 속하면서, 점수가 mScore 이상인 학생 중에서 점수가 가장 낮은 학생의 ID를 반환한다.
		// 점수가 가장 낮은 학생이 여러 명이라면, 그 중에서 ID가 가장 작은 값을 반환한다.
		
		// mGradeCnt 개의 학년이 mGrade 배열로 주어진다. 예를 들어, 1학년과 3학년이라면 {1, 3}로 주어진다.
		// mGenderCnt 개의 성별이 mGender 배열로 주어진다. 예를 들어, 남성이라면 {“male”}로 주어지고, 남성과 여성이라면 {“male”, “female”}로 주어진다.
		// 점수가 mScore 이상인 학생이 없다면, 0을 반환한다.

		ArrayList<Integer> genderList = new ArrayList<>();
		for(int i=0;i<mGenderCnt;i++) {
			genderList.add(mGender[i][0] == 'm' ? 0 : 1);
		}
		
		TreeSet<Student> ret = new TreeSet<>();
		for(int i=0;i<mGradeCnt;i++) {
			int grade = mGrade[i];
			for(int gender : genderList) {
				// TreeSet higher 메소드
				// higher(Object o)
				// return : Object
				// 지정된 객체보다 큰 값을 가진 객체 중 제일 가까운 값의 객체를 반환.없으면 null.
				Student temp = studentBoard[grade][gender].higher(new Student(0, 0, 0, mScore));
				
				if(temp == null) continue;
				
				ret.add(temp);
			}
		}
		
		if(ret.size() == 0) return 0;

		return ret.first().id;
	}
}
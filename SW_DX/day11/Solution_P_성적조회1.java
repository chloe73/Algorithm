package algo.SW_DX.day11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Solution_P_성적조회1 {
	private final static int CMD_INIT = 100;
	private final static int CMD_ADD = 200;
	private final static int CMD_REMOVE = 300;
	private final static int CMD_QUERY = 400;

	private final static UserSolution2 usersolution = new UserSolution2();

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
		
		double before = System.currentTimeMillis();
		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(br) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}
		double after = System.currentTimeMillis();
		double time = (after-before) / 1000;
		System.out.println("time : "+ time);
		br.close();
	}
}

class UserSolution2 {
	
	static HashMap<Integer, Student> studentMap;
	static TreeSet<Student>[][] studentSet;
	static class Student implements Comparable<Student>{
		int id, grade, gender, score;
		public Student(int id, int grade, int gender, int score) {
			this.id = id;
			this.grade = grade;
			this.gender = gender;
			this.score = score;
		}
		public int compareTo(Student o) {
			// 점수가 가장 낮은 학생이 여러 명이라면, 그 중에서 ID가 가장 작은 값을 반환한다.
			if(this.score == o.score)
				return this.id - o.id;
			return this.score - o.score;
		}
	}
	
	public void init() {

		// 각 테스트 케이스의 처음에 호출된다.
		// 등록된 성적이 없는 상태가 된다.
		studentMap = new HashMap<>();
		studentSet = new TreeSet[4][2];
		for(int i=1;i<=3;i++) {
			for(int j=0;j<2;j++) {
				studentSet[i][j] = new TreeSet<>();
			}
		}
		
		return;
	}

	public int add(int mId, int mGrade, char mGender[], int mScore) {

		int gender = mGender[0] == 'm' ? 0 : 1;
		Student s = new Student(mId, mGrade, gender, mScore);
		studentSet[mGrade][gender].add(s);
		studentMap.put(mId, s);
		
		// mGrade학년 mGender인 학생 중에서 점수가 가장 높은 학생의 ID를 반환한다.
		Student ret = studentSet[mGrade][gender].last();
		return ret.id;
	}

	public int remove(int mId) {
		// 학생 ID가 mId인 학생의 기록을 삭제한다.
		
		// 시스템에 mId 학생의 점수가 기록되어 있지 않다면, 0을 반환한다.
		if(!studentMap.containsKey(mId)) return 0;

		Student target = studentMap.get(mId);
		
		studentSet[target.grade][target.gender].remove(target);
		studentMap.remove(mId);
		
		// 삭제 후에, 학년과 성별이 동일한 학생이 없다면, 0을 반환한다.
		if(studentSet[target.grade][target.gender].isEmpty()) return 0;
		
		// 삭제 후에 mId 학생의 학년과 성별이 동일한 학생 중에서 점수가 가장 낮은 학생의 ID를 반환한다.
		// 점수가 가장 낮은 학생이 여러 명이라면, 그 중에서 ID가 가장 작은 값을 반환한다.
		return studentSet[target.grade][target.gender].first().id;
	}

	public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
		// mGrade 학년 집합과 mGender 성별 집합에 속하면서, 점수가 mScore 이상인 학생 중에서 점수가 가장 낮은 학생의 ID를 반환한다.
		// 점수가 가장 낮은 학생이 여러 명이라면, 그 중에서 ID가 가장 작은 값을 반환한다.
		Student ret = new Student(0, 0, 0, Integer.MAX_VALUE);
		
		for(int i=0;i<mGradeCnt;i++) {
			int grade = mGrade[i];
			for(int j=0;j<mGenderCnt;j++) {
				int gender = mGender[j][0] == 'm' ? 0 : 1;
				
				Student s = studentSet[grade][gender].higher(new Student(0, grade, gender, mScore));
				
				if(s != null && s.compareTo(ret) < 0)
					ret = s;
			}
		}
		return ret.id;
	}
}
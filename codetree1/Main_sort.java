package algo.codetree1;

import java.util.PriorityQueue;

public class Main_sort {
	
	static class Person implements Comparable<Person> {
		int age;
		String name;
		@Override
		public String toString() {
			return "Person [age=" + age + ", name=" + name + "]";
		}
		public Person(int age, String name) {
			this.age = age;
			this.name = name;
		}
		public int compareTo(Person o) {
			if(this.age == o.age) {
				return this.name.compareTo(o.name); // 문자열 사전 순 정렬
			}
			return this.age - o.age;
		}
	}

	public static void main(String[] args) {
		Person a = new Person(18, "danny");
		Person b = new Person(25, "sally");
		Person c = new Person(22, "chloe");
		Person d = new Person(22, "chris");
		Person e = new Person(25, "daniel");
		
		PriorityQueue<Person> pq = new PriorityQueue<>();
		pq.add(a);
		pq.add(b);
		pq.add(c);
		pq.add(d);
		pq.add(e);
		
		while(!pq.isEmpty()) {
			System.out.println(pq.poll().toString());
		}
	}

}

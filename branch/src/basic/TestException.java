package basic;

import example.test.heysung;

public class TestException {

	public TestException() throws Exception{
		System.out.println("���쿪ʼ ");
		System.out.print("\007"); 
		System.out.println("\7"); 
	
			throw new Exception("��������쳣 ");
	}
	protected int age;
	String name;

	int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static void main(String[] args) {
		TestException great=null;
		try {
			great = new TestException();
		} 
		
		catch (Exception e) {
			// TODO: handle exception
		}
		if (great == null)
			System.out.println("����ΪNULL ");

	}
}

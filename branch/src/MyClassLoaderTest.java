import java.lang.reflect.*;;

public class MyClassLoaderTest {

	public void run() {
		try {
			// ÿ�ζ�������һ���µ��������
			CustomCL cl = new CustomCL("../swap", new String[] { "Foo" });
			Class cls = cl.loadClass("Foo");
			Object foo = cls.newInstance();

			Method m = foo.getClass().getMethod("sayHello", new Class[] {});
			m.invoke(foo, new Object[] {});

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	 public static void main(String[] args) throws Exception {
		 
	 
	 }

}

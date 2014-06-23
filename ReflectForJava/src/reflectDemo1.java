import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/*a)�����޲����Ĺ��췽����

1.����Class�����newInstance()����:

Class<?> classType = ClassClass.forName("java.lang.String");

Object object = classTpye.newInstance();

         2.����Class�����Constructor�����newInstance()����������һ���յ�Class����������Ϊ������

Class<?> classType = ClassClass.forName("java.lang.String");

Constructor cons = classType.getConstructor(new Class[]{});  

Object object =cons.newInstance(new Object[]{});

b)�����в����Ĺ��췽����

1.����Class�����Constructor�����newInstance()����������һ���ɱ䳤��Class����������Ϊ��������������String,int����������
Class<?> classType = ClassClass.forName("java.lang.String");

Constructor cons = classType.getConstructor(new Class[]{String.class, int.class});

  Object object =
cons.newInstance(new Object[]{"hello",3});

getDeclaredMethod*()��ȡ�������������������з���������public��protected��private������
getMethod*()��ȡ����������й��з�������Ͱ������������public�������ʹӻ���̳еġ��ӽӿ�ʵ�ֵ�����public����
		 */
class user{
	public String name;
	public int age;
	private String password;
	
	public user(String name, int age) {
		this.name = name;
		this.age = age;
		this.password = name + age;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String toString(){
		return "name: "+ name + " age: " + age;
	}
	
	private String printPassWord(){
		return password;
	}
}	
public class reflectDemo1 {

	public static void main(String[] args) throws Exception{
		// class forName used to find class ��ȡclass����
		Class<?> userClass = Class.forName("user");
		
		// create Constructor use params ��Ҫ����������һ��string һ��int 
		Constructor userCon = userClass.getConstructor(new Class[]{String.class,int.class});
		
		// create newInstance used to instance obj
		Object userObject = userCon.newInstance(new Object[]{"SGAME",23});
		
	// 1. invoke the methor ��ȡ�޲����ķ���
	 /* @param obj  the object the underlying method is invoked from
     * @param args the arguments used for the method call
     * @return the result of dispatching the method represented 
     * name: SGAME age: 23
    	 */
	Method toStringMethod =	userClass.getMethod("toString", new Class[]{});
	String result = (String) toStringMethod.invoke(userObject, new Object[]{});
	System.out.println(result);
	
	
	//2 ��ȡ�в����ķ���
	//name: SGAME age: 18
	Method setAgeMethod = userClass.getMethod("setAge", new Class[]{int.class});
	setAgeMethod.invoke(userObject, new Object[]{18});
	
	String resultAfterSetAge = (String) toStringMethod.invoke(userObject, new Object[]{});
	System.out.println(resultAfterSetAge);
	
	/* 3 .������˽�з�����ʱ�򱨴�û�������ķ���
	 * Exception in thread "main" java.lang.NoSuchMethodException: user.printPassWord()
	at java.lang.Class.getMethod(Unknown Source)
	at reflectDemo1.main(reflectDemo1.java:97)
	���Ա���ʹ��getDeclaredMethod ����ȡ˽�еķ�����Ȼ�����ÿ��Է��ʡ�Ĭ����false
		*/
	Method getPassword = userClass.getDeclaredMethod("printPassWord", new Class[]{});
	//��ֹprintPassWord�����ķ��ʿ��Ƽ��  
	getPassword.setAccessible(true);  
	String printPassword = (String) getPassword.invoke(userObject, new Object[]{});
	System.out.println(printPassword);
	
	/*4 ���ʹ��в��� �޸Ĳ���
	 * out:18 19
	 */
	Field ageField = userClass.getField("age");
	int age = (Integer) ageField.get(userObject);
	ageField.set(userObject, 19);
	int ageAfter = (Integer) ageField.get(userObject);
	System.out.println(age+" "+ageAfter);
	
	/*
	 * 5.����˽�в���
	 * ʹ��getField
	 * Exception in thread "main" java.lang.NoSuchFieldException: password
	at java.lang.Class.getField(Unknown Source)
	out:SGAME23 password
	 */
	Field passwordField = userClass.getDeclaredField("password");
	passwordField.setAccessible(true);
	String password = (String) passwordField.get(userObject);
	passwordField.set(userObject, "password");
	String passwordAfter = (String) passwordField.get(userObject);
	System.out.println(password+" "+passwordAfter);
	}
}

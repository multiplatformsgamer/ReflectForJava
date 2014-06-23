import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/*a)调用无参数的构造方法：

1.调用Class对象的newInstance()方法:

Class<?> classType = ClassClass.forName("java.lang.String");

Object object = classTpye.newInstance();

         2.调用Class对象的Constructor对象的newInstance()方法，传递一个空的Class对象数组作为参数：

Class<?> classType = ClassClass.forName("java.lang.String");

Constructor cons = classType.getConstructor(new Class[]{});  

Object object =cons.newInstance(new Object[]{});

b)调用有参数的构造方法：

1.调用Class对象的Constructor对象的newInstance()方法，传递一个可变长的Class对象数组作为参数，本例传递String,int两个参数：
Class<?> classType = ClassClass.forName("java.lang.String");

Constructor cons = classType.getConstructor(new Class[]{String.class, int.class});

  Object object =
cons.newInstance(new Object[]{"hello",3});

getDeclaredMethod*()获取的是类自身声明的所有方法，包含public、protected和private方法。
getMethod*()获取的是类的所有共有方法，这就包括自身的所有public方法，和从基类继承的、从接口实现的所有public方法
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
		// class forName used to find class 获取class对象
		Class<?> userClass = Class.forName("user");
		
		// create Constructor use params 需要两个参数，一个string 一个int 
		Constructor userCon = userClass.getConstructor(new Class[]{String.class,int.class});
		
		// create newInstance used to instance obj
		Object userObject = userCon.newInstance(new Object[]{"SGAME",23});
		
	// 1. invoke the methor 获取无参数的方法
	 /* @param obj  the object the underlying method is invoked from
     * @param args the arguments used for the method call
     * @return the result of dispatching the method represented 
     * name: SGAME age: 23
    	 */
	Method toStringMethod =	userClass.getMethod("toString", new Class[]{});
	String result = (String) toStringMethod.invoke(userObject, new Object[]{});
	System.out.println(result);
	
	
	//2 获取有参数的方法
	//name: SGAME age: 18
	Method setAgeMethod = userClass.getMethod("setAge", new Class[]{int.class});
	setAgeMethod.invoke(userObject, new Object[]{18});
	
	String resultAfterSetAge = (String) toStringMethod.invoke(userObject, new Object[]{});
	System.out.println(resultAfterSetAge);
	
	/* 3 .当访问私有方法的时候报错，没有这样的方法
	 * Exception in thread "main" java.lang.NoSuchMethodException: user.printPassWord()
	at java.lang.Class.getMethod(Unknown Source)
	at reflectDemo1.main(reflectDemo1.java:97)
	所以必须使用getDeclaredMethod 来获取私有的方法，然后设置可以访问。默认是false
		*/
	Method getPassword = userClass.getDeclaredMethod("printPassWord", new Class[]{});
	//禁止printPassWord方法的访问控制检查  
	getPassword.setAccessible(true);  
	String printPassword = (String) getPassword.invoke(userObject, new Object[]{});
	System.out.println(printPassword);
	
	/*4 访问共有参数 修改参数
	 * out:18 19
	 */
	Field ageField = userClass.getField("age");
	int age = (Integer) ageField.get(userObject);
	ageField.set(userObject, 19);
	int ageAfter = (Integer) ageField.get(userObject);
	System.out.println(age+" "+ageAfter);
	
	/*
	 * 5.访问私有参数
	 * 使用getField
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

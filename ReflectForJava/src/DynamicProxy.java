import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/*
 * 代理，传进去一个实例，让代理类去处理事情。
 * 动态代理类，因为有实例了。new user()。在调用实例的方法的时候执行的
 * beforeDoing 和afterDoing。这样就在调用某方法的时候，执行了其他的过程，比如记录日志，记录执行时间等。
 */
public class DynamicProxy implements InvocationHandler {

	// 需要被代理类的引用
	private Object object;

	// 通过构造方法传入引用
	public DynamicProxy(Object object) {
		this.object = object;
	}

	// 返回de是object的接口类型，object 类型不可以
	// 定义一个工厂类，去生成动态代理
	public Object getProxy() {
		// 通过Proxy类的newProxyInstance方法动态的生成一个动态代理，并返回它
		return Proxy.newProxyInstance(object.getClass().getClassLoader(), object
				.getClass().getInterfaces(), this);
	}

	// 重写的invoke方法，这里处理真正的方法调用
	public Object invoke(Object obj, Method method, Object[] args)
			throws Throwable {
		
		beforeDoing();
		
		Object invoke = method.invoke(object, args);
		
		afterDoing();
		
		return invoke;
	}
	
	public void beforeDoing() {
		System.out.println("before ............");
	}
	
	public void afterDoing() {
		System.out.println("after ............."+"\n");
	}
}
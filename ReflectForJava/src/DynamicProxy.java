import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/*
 * ��������ȥһ��ʵ�����ô�����ȥ�������顣
 * ��̬�����࣬��Ϊ��ʵ���ˡ�new user()���ڵ���ʵ���ķ�����ʱ��ִ�е�
 * beforeDoing ��afterDoing���������ڵ���ĳ������ʱ��ִ���������Ĺ��̣������¼��־����¼ִ��ʱ��ȡ�
 */
public class DynamicProxy implements InvocationHandler {

	// ��Ҫ�������������
	private Object object;

	// ͨ�����췽����������
	public DynamicProxy(Object object) {
		this.object = object;
	}

	// ����de��object�Ľӿ����ͣ�object ���Ͳ�����
	// ����һ�������࣬ȥ���ɶ�̬����
	public Object getProxy() {
		// ͨ��Proxy���newProxyInstance������̬������һ����̬������������
		return Proxy.newProxyInstance(object.getClass().getClassLoader(), object
				.getClass().getInterfaces(), this);
	}

	// ��д��invoke���������ﴦ�������ķ�������
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
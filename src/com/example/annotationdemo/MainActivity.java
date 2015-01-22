package com.example.annotationdemo;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		try {
//			Object obj = Class.forName(Student.class.getName()).newInstance();
//			Method[] methods = obj.getClass().getMethods();
//			for (Method method : methods) {
//				if (method.isAnnotationPresent(ValueBind.class)) {
//					System.out.println(method.getName());
//					ValueBind valueBind = method.getAnnotation(ValueBind.class);
//					String value = String.valueOf(valueBind.value());
//					String type = String.valueOf(valueBind.type());
//					System.out.println(value);
//					System.out.println(type);
//					if (type.equals("STRING")) {
//						method.invoke(obj, new String[] { value });
//					}
//				}
//			}
//			Object o = Class.forName(B.class.getName()).newInstance();
//			Method[] ms = obj.getClass().getMethods();
//			for (Method method : ms) {
////				if (method.isAnnotationPresent(Override.class)) {
////				}
//				System.out.println("11 " + method.getName());
//				Annotation[] an = method.getAnnotations();
//				for (Annotation annotation : an) {
//					System.out.println("name " + annotation.toString());
//					System.out.println("type " + annotation.annotationType());
//				}
//			}
//			Student student = (Student) obj;
//			System.out.println(student.getId());
//
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}

class A{
	public void a(){
		
	}
}

class B extends A{
	@Override
	public void a() {
		super.a();
	}
}

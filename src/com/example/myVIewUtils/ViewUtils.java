package com.example.myVIewUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewUtils {
	public static void inject(Activity activity){
		try {
			bindFiled(activity);//绑定控件
			bindMethod(activity);//给Button绑定点击事件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void bindFiled(Activity activity) throws IllegalAccessException, IllegalArgumentException {
		/*
		 * 1. 找到字节码中的属性(先找到所有的属性，然后再挑出声明了@ViewInject的属性)
		 */
		Field[] fields = activity.getClass().getDeclaredFields();
		for(Field field : fields){
			ViewInject viewInject = field.getAnnotation(ViewInject.class);
			if (viewInject==null) {
				continue;
			}
			int id = viewInject.value();
			//从Activity对象中执行findviewById找到控件
			View view = activity.findViewById(id);
			//把view设置给当前activity的field对象
			field.setAccessible(true);
			field.set(activity, view);
		}
		
	}

	private static void bindMethod(final Activity activity) {
		/*
		 * 1. 先获取Activity字节码中的方法
		 */
		Class clazz = activity.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		for(final Method method : methods){
			OnClick onClick = method.getAnnotation(OnClick.class);
			if (onClick==null) {
				continue;
			}
			int id = onClick.value();
			/*
			 * 2. 根据自定义注解上面的id。去findViewById
			 */
			final View view = activity.findViewById(id);
			/*
			 * 3. 给View绑定点击事件
			 */
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					/*
					 * 4. 反射调用Activity中的method
					 */
					method.setAccessible(true);
					try {
						method.invoke(activity, view);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
		}
	}
}

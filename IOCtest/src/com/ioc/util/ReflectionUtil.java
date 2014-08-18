package com.ioc.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class ReflectionUtil {
	private ReflectionUtil() {
	}

	public static Object getInstance(String type) {
		Object obj = null;
		try {
			obj = Class.forName(type).newInstance();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static Field[] getFields(Object obj) {
		return obj.getClass().getDeclaredFields();
	}

	public static Method[] getMethods(Object obj) {
		return obj.getClass().getDeclaredMethods();
	}

	public static Method getMethod(Object obj, String methodName) {
		Method[] methods = getMethods(obj);
		Method ret = null;
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				ret = m;
				break;
			}
		}
		return ret;
	}

	public static Object invoke(Object obj, Method method, Object... params) {
		Object ret = null;
		try {
			ret = method.invoke(obj, params);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static Field getDeclaredField(final Object obj,
			final String fieldName) {
		return getDeclaredField(obj.getClass(), fieldName);
	}

	@SuppressWarnings("rawtypes")
	private static Field getDeclaredField(final Class clazz,
			final String filedName) {
		for (Class superClass = clazz; superClass != Object.class; superClass = clazz
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(filedName);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getDeclaredField(obj, fieldName);
		if (field == null) {
			throw new IllegalArgumentException(fieldName
					+ " is not exist in the obj:" + obj);
		}
		makeAccessible(field);
		Object ret = null;
		try {
			ret = field.get(obj);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static Object getProperty(Object obj, String propertyName) {
		if (propertyName == null || propertyName.length() < 1) {
			return obj;
		}
		int pos = propertyName.indexOf(".");
		if (pos < 0) {
			return getFieldValue(obj, propertyName);
		} else {
			String baseProperty = propertyName.substring(0, pos);
			String childProperty = propertyName.substring(pos + 1);
			Object target = getFieldValue(obj, baseProperty);
			return getProperty(target, childProperty);
		}
	}

	private static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	public static Class getSuperGenericType(final Class clazz) {
		return getSuperGenericType(clazz, 0);
	}

	@SuppressWarnings("rawtypes")
	public static Class getSuperGenericType(final Class clazz, final int index) {
		Type genericType = clazz.getGenericSuperclass();
		if (!(genericType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genericType)
				.getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 提取集合中的对象的属性,组合成List.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List fetchElementPropertyToList(final Collection collection,
			final String propertyName) throws Exception {

		List list = new ArrayList<>();

		for (Object obj : collection) {
			list.add(PropertyUtils.getProperty(obj, propertyName));
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性,组合成由分割符分隔的字符串.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 * @param separator
	 *            分隔符.
	 */

	@SuppressWarnings("rawtypes")
	public static String fetchElementPropertyToString(
			final Collection collection, final String propertyName,
			final String separator) throws Exception {
		List list = fetchElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 将list的数据通过分割符组成字符串
	 * 
	 * @author huwanshan
	 * @date 2010-12-11 下午07:50:35
	 * @param valueList
	 * @param separator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String toReadableString(List valueList, String separator) {
		return StringUtils.join(valueList, separator);
	}

}

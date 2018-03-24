/*
 * Created on 2003/05/22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.wits.util;

import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @version
 * $Revision: 1.1.1.1 $ <br>
 * $Date: 2005/08/15 06:10:55 $
 * @author Administrator
 *
 */
public class ResourceLoader {

	private static ResourceLoader loader = new ResourceLoader();

	private ResourceLoader() {
	}

	public static ResourceLoader getInstance() {
		return loader;
	}

	public Properties getProp(String fileName) throws IOException {
		Properties prop = new Properties();
		prop.load(getInputStream(fileName));
		return prop;
	}

	public InputStream getInputStream(String fileName) {
		Class theClass = this.getClass();
		ClassLoader theLoader = theClass.getClassLoader();

		return theLoader.getResourceAsStream(fileName);
	}

	public Object getClassInstance(String className)
		throws
			InstantiationException,
			IllegalAccessException,
			ClassNotFoundException {
		Class theClass = this.getClass();
		ClassLoader theLoader = theClass.getClassLoader();
		return theLoader.loadClass(className).newInstance();
		//		return Class.forName(className).newInstance();
	}

	public Object getClassInstance(String className, Object[] args)
		throws
			ClassNotFoundException,
			IllegalArgumentException,
			InstantiationException,
			IllegalAccessException,
			InvocationTargetException,
			SecurityException,
			NoSuchMethodException {
		Class theClass = this.getClass();
		ClassLoader theLoader = theClass.getClassLoader();

		Class[] type = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			type[i] = args[i].getClass();
		}

		Class cls = theLoader.loadClass(className);
		Constructor construct = cls.getConstructor(type);
		Object o = construct.newInstance(args);
		return o;
	}

}

package tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import plugin.PluginCore;
import plugin.PluginManager;

public class ManagerTests {

	PluginManager m;
	PluginCore c;

	@Before
	public void setUp() throws Exception {
		c = new PluginCore();
		m = new PluginManager(c);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadBundle() throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException {
		Path path = EasyMock.createMock(Path.class);
		Method method = PluginManager.class.getDeclaredMethod("loadBundle",Path.class);
		method.setAccessible(true);
		try {
			method.invoke(m, path);
		} catch (InvocationTargetException e) {
			System.out.println(e.getLocalizedMessage());
		}
		Assert.assertEquals(m.getPathToPlugin().size(), 0);
	}

	@Test
	public void testUnloadBundle() throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException {
		Path path = EasyMock.createMock(Path.class);
		Method method = PluginManager.class.getDeclaredMethod("unloadBundle",Path.class);
		method.setAccessible(true);
		try {
			method.invoke(m, path);
		} catch (InvocationTargetException e) {
			System.out.println(e.getLocalizedMessage());
		}
		Assert.assertEquals(m.getPathToPlugin().size(), 0);
	}

}
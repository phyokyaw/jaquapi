package net.phyokyaw.jaquapi;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class TestInitialContextFactory implements InitialContextFactory {

	public static class MapContext implements Context {
		private final Map<String, Object> map = new HashMap<String, Object>();

		@Override
		public void unbind(String name) throws NamingException {
		}

		@Override
		public void unbind(Name name) throws NamingException {
		}

		@Override
		public void rename(String oldName, String newName) throws NamingException {
		}

		@Override
		public void rename(Name oldName, Name newName) throws NamingException {
		}

		@Override
		public Object removeFromEnvironment(String propName) throws NamingException {
			return null;
		}

		@Override
		public void rebind(String name, Object obj) throws NamingException {
		}

		@Override
		public void rebind(Name name, Object obj) throws NamingException {
		}

		@Override
		public Object lookupLink(String name) throws NamingException {
			return null;
		}

		@Override
		public Object lookupLink(Name name) throws NamingException {
			return null;
		}

		@Override
		public Object lookup(String name) throws NamingException {
			return map.get(name);
		}

		@Override
		public Object lookup(Name name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
			return null;
		}

		@Override
		public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
			return null;
		}

		@Override
		public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
			return null;
		}

		@Override
		public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
			return null;
		}

		@Override
		public NameParser getNameParser(String name) throws NamingException {
			return null;
		}

		@Override
		public NameParser getNameParser(Name name) throws NamingException {
			return null;
		}

		@Override
		public String getNameInNamespace() throws NamingException {
			return null;
		}

		@Override
		public Hashtable<?, ?> getEnvironment() throws NamingException {
			return null;
		}

		@Override
		public void destroySubcontext(String name) throws NamingException {
		}

		@Override
		public void destroySubcontext(Name name) throws NamingException {
		}

		@Override
		public Context createSubcontext(String name) throws NamingException {
			return null;
		}

		@Override
		public Context createSubcontext(Name name) throws NamingException {
			return null;
		}

		@Override
		public String composeName(String name, String prefix) throws NamingException {
			return null;
		}

		@Override
		public Name composeName(Name name, Name prefix) throws NamingException {
			return null;
		}

		@Override
		public void close() throws NamingException {
		}

		@Override
		public void bind(String name, Object obj) throws NamingException {
			map.put(name, obj);
		}

		@Override
		public void bind(Name name, Object obj) throws NamingException {
			map.put(name.toString(), obj);
		}

		@Override
		public Object addToEnvironment(String propName, Object propVal) throws NamingException {
			return null;
		}
	}
	private static Context defaultContext = null;

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
		if (defaultContext == null) {
			defaultContext = new MapContext();
		}
		return defaultContext;
	}
}

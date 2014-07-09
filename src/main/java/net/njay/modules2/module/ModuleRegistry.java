package net.njay.modules2.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.njay.modules2.exception.InvalidModuleException;

public class ModuleRegistry {

	private static HashMap<Class<? extends Module>, ModuleInfo> modules = new HashMap<Class<? extends Module>, ModuleInfo>();

    /**
     * Register a class type in the registry, all modules in the registry will be given the xml doc
     *
     * @param clazz
     * @throws InvalidModuleException
     */
	public static void register(Class<? extends Module> clazz) throws InvalidModuleException{
		if (!clazz.isAnnotationPresent(ModuleInfo.class))
			throw new InvalidModuleException("Module: " + clazz.getName() + " is missing @ModuleInfo annotation!");
		modules.put(clazz, clazz.getAnnotation(ModuleInfo.class));
	}

    /**
     * @return registry of registered modules
     */
	public static HashMap<Class<? extends Module>, ModuleInfo> getPossibleModules(){
		return modules;
	}
}

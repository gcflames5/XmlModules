package net.njay.modules2.module;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.njay.modules2.exception.InvalidModuleException;

import org.jdom2.Document;

public class ModuleContainer {
    private HashMap<Module, ModuleInfo> modules = new HashMap<Module, ModuleInfo>();

    /**
     * Constructor.
     *
     * @param doc XML Document that will be passed to all modules
     */
    public ModuleContainer(Document doc) {
        for(Entry<Class<? extends Module>, ModuleInfo> info : ModuleRegistry.getPossibleModules().entrySet()) {
            try {
                addModule(info.getKey(), doc);
            } catch (InvalidModuleException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Give the current document to a new module and enable it
     *
     * @param clazz Class of the module
     * @param doc Document to pass to the module
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvalidModuleException
     */
    public void addModule(Class<? extends Module> clazz, Document doc) throws InstantiationException, IllegalAccessException, InvalidModuleException{
        if (!clazz.isAnnotationPresent(ModuleInfo.class))
            throw new InvalidModuleException("Module: " + clazz.getClass().getName() + " does not have @ModuleInfo tag!");
        ModuleInfo info = clazz.getAnnotation(ModuleInfo.class);
        for (Class<? extends Module> dependency : info.requires()){
            addModule(dependency, doc);
        }
        if (!registered(info)) {
            Module module = callParse(clazz, doc);
            if (module != null)
                modules.put(module, info);
        }
    }

    /**
     * Call the module's parse method
     *
     * @param clazz Class of the module
     * @param doc Document to pass to the module
     * @param <T> Generic Type: Type of Module
     * @return New module instance (null if module rejected document)
     * @throws InvalidModuleException Module doesn't have a proper static parse method
     */
    public <T extends Module> T callParse(Class<?> clazz, Document doc) throws InvalidModuleException{
    	try{
    		for (Method m : clazz.getMethods())
    			if (m.getName().equals("parse"))
    				return (T) m.invoke(null, this, doc);
    	}catch(Exception e){e.printStackTrace(); throw new InvalidModuleException("Invalid Parse method in: " + clazz.getCanonicalName()); }
		throw new InvalidModuleException("No static parse method in: " + clazz.getName());
    }

    /**
     * Get a instance of a loaded module
     *
     * @param clazz Class of the desired module
     * @return Instance of desired module (if loaded), returns null if no loaded module matching that class
     */
    public <T extends Module> T getModule(Class<? extends Module> clazz) {
        for(Entry<Module, ModuleInfo> entry : modules.entrySet()) {
            if (entry.getKey().getClass().equals(clazz))
                return (T) entry.getKey();
        }
        return null;
    }

    /**
     * Enable all modules (call onEnable())
     */
    public void enableModules() {
        for(Module module : modules.keySet())
            module.onEnable();
    }

    /**
     * Disable all modules (call onDisable())
     */
    public void disableModules() {
        for(Module module : modules.keySet())
            module.onDisable();
    }

    private boolean registered(ModuleInfo info){
        for (Map.Entry<Module, ModuleInfo> entry : modules.entrySet()){
            if (entry.getValue().name().equals(info.name()))
                return true;
        }
        return false;
    }
}

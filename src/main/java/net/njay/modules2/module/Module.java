package net.njay.modules2.module;

/**
 * Template for modules
 */
public class Module{

    protected ModuleContainer container;

    public Module(ModuleContainer container) {
        this.container = container;
    }

    /**
     * Called when ModuleContainer.enableModules() is called
     */
	public void onEnable(){}

    /**
     * Called when ModuleContainer.disableModules() is called
     */
	public void onDisable(){}

    /**
     * @return The container that the module is in.
     */
    public ModuleContainer getContainer() {
        return container;
    }
}
package net.njay.modules2.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Provides information about each Module class
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

    /**
     * @return Name of the module
     */
	String name();

    /**
     * @return Short description of module's function (optional)
     */
	String desc() default "";

    /**
     * @return Array of Modules that need to be loaded prior (Optional)
     */
	Class<? extends Module>[] requires() default {};

}

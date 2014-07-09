XmlModules
=========

[Click Here for JavaDocs](http://docs.njay.net/xmlmodules/)

Setup
========

```java
try {
    ModuleRegistry.register(MaxBuildHeightModule.class);
} catch (InvalidModuleException e) {
    //handle exception
}

// Create the XML File from the String and generate a doc (for example purposes)
String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><maxbuildheight>38</maxbuildheight></root>";
SAXBuilder sb = new SAXBuilder();
Document doc = sb.build(new StringReader(xml)); //You can load an external source if needed

// Creating the container from the doc iterates through everything we registered, and calls its parse method
container = new ModuleContainer(doc);

// Enabling the modules calls onEnable on each module, this is not required but recommended (especially if using an outside module)
container.enableModules();

//Fetch values from the module
MaxBuildHeightModule buildMod = container.getModule(MaxBuildHeightModule.class);
System.out.println("The max build height defined in the module is " + buildMod.getMaxBuildHeight());

// Disabling the modules calls onDisable on each module, this is not required but recommended (especially if using an outside module)
container.disableModules();
```

Creating a Module (Example)
======

Requirements: extends Module class, @ModuleInfo(name="ExampleName"), and a static parse method that returns a new instance of the class and takes in a ModuleContainer and a Document as parameters

```java
import net.njay.modules2.module.Module;
import net.njay.modules2.module.ModuleContainer;
import net.njay.modules2.module.ModuleInfo;

import org.jdom2.Document;

@ModuleInfo(name = "MaxBuildHeightModule" /*desc="Fetches the maximum build height requires={RequiredModule.class, AnotherRequiredModule.class}*/)
public class MaxBuildHeightModule extends Module {

    private int maxBuildHeight;

    public MaxBuildHeightModule(int maxBuildHeight) {
        this.maxBuildHeight = maxBuildHeight;
    }
    
    @Override
    public void onEnable() {
        System.out.println("Enabling MaxBuildModule");
    }

    @Override
    public void onDisable() {
        System.out.println("Disabling MaxBuildModule");
    }

    public int getMaxBuildHeight() {
        return maxBuildHeight;
    }

    public static Module parse(ModuleContainer container, Document doc) {
        return new MaxBuildHeightModule(Integer.valueOf(doc.getRootElement().getChild("maxbuildheight").getTextNormalize()));
    }
}
```
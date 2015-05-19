package de.colenet.docker.tdd

import groovy.lang.GroovyShell;

import org.codehaus.groovy.control.CompilerConfiguration

import java.io.File;

/**
 * Runner for Container Tests.
 *
 */
class ContainerTestRunner {
	
	final private GroovyShell sh = createShell(DelegatingScript.class)
	
	final private DockerSpecificationDSL dslDelegate

	/**
	 * Constructor.
	 * 
	 * @param dslDelegate DSL delegate
	 */
	ContainerTestRunner(DockerSpecificationDSL dslDelegate) {
		this.dslDelegate = dslDelegate
	}
	
	/**
	 * runs the test file.
	 * 
	 * @param specFile container test file
	 */
	void runSpecFile(File specFile) {
		DelegatingScript script = (DelegatingScript)sh.parse(specFile)
		script.setDelegate(dslDelegate)
		script.run()
	}

	private GroovyShell createShell(Class scriptBaseClass) {
		CompilerConfiguration cc = new CompilerConfiguration()
		cc.setScriptBaseClass(scriptBaseClass.name)
		GroovyShell sh = new GroovyShell(this.class.classLoader,new Binding(),cc)
	}

}

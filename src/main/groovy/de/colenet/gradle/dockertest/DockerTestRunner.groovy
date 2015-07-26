package de.colenet.gradle.dockertest

import org.codehaus.groovy.control.CompilerConfiguration

class DockerTestRunner {

	final private GroovyShell sh = createShell(DelegatingScript.class)

	final private DockerTestDSL dsl;

	DockerTestRunner(DockerTestDSL dsl) {
		this.dsl = dsl;
	}

	void runSpecFile(File specFile) {
		DelegatingScript script = (DelegatingScript)sh.parse(specFile)
		script.setDelegate(dsl)
		script.run()
	}

	private GroovyShell createShell(Class scriptBaseClass) {
		CompilerConfiguration cc = new CompilerConfiguration()
		cc.setScriptBaseClass(scriptBaseClass.name)
		GroovyShell sh = new GroovyShell(this.class.classLoader,new Binding(),cc)
	}
}

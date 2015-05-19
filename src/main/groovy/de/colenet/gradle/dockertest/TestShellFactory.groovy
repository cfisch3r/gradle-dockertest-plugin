package de.colenet.gradle.dockertest

import org.codehaus.groovy.control.CompilerConfiguration

class TestShellFactory {

	GroovyShell create() {
		CompilerConfiguration cc = new CompilerConfiguration()
		cc.setScriptBaseClass(DelegatingScript.class.name)
		return new GroovyShell(this.class.classLoader,new Binding(),cc)
	}
}

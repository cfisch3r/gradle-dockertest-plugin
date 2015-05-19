package de.colenet.gradle.dockertest

import spock.lang.Specification

class TestShellFactoryTest extends Specification {

	def "shell"() {
		setup:
		def source = '''
			println "hello"
			
		'''
		def sut = new TestShellFactory()
		when:
		GroovyShell shell = sut.create()
		DelegatingScript script = (DelegatingScript)shell.parse(source)
		script.setDelegate(new Object())
		script.run()
		then:
		shell != null
	}
}

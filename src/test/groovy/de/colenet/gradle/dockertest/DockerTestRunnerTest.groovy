package de.colenet.gradle.dockertest

import spock.lang.Specification

class DockerTestRunnerTest extends Specification {

	private DockerTestRunner sut

	private DockerTestDSL dsl

	def setup() {
		dsl = Mock(DockerTestDSL)
		sut = new DockerTestRunner(dsl)
	}

	def "runSpecFile calls DSL method"() {
		setup:
		def specFile = new File(getClass().getClassLoader().getResource("test.spec").getFile());

		when:
		sut.runSpecFile(specFile)
		then:
		1 * dsl.specification(_ as String)
	}
}

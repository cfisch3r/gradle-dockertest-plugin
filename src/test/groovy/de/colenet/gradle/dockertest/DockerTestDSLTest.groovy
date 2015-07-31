package de.colenet.gradle.dockertest

import spock.lang.Specification

class DockerTestDSLTest extends Specification {
	private DockerTestDSL sut

	def setup() {
		sut = new DockerTestDSL(null)
	}

	def "specification return Test specification"() {
		when:
		def specification = sut.specification("specification")
		then:
		specification != null
	}
}

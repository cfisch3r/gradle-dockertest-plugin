package de.colenet.gradle.dockertest

import org.codehaus.groovy.transform.tailrec.VariableReplacedListener.*
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification

class DockerTestContainerTaskTest extends Specification {

	private DockerTestContainerTask sut

	def testRunner = Mock(DockerTestRunner)

	def setup() {
		def project = setUpProjectWithExtension()
		sut = project.task('testContainer', type: DockerTestContainerTask)
	}


	def "task is created"() {
		expect:
		sut instanceof DockerTestContainerTask
	}

	def "task should call testrunner with spec file."() {

		when:
		sut.execute()

		then:
		1 * testRunner.runSpecFile(_ as File)
	}

	def "task should call testrunner with spec file from Property when set."() {
		setup:
		def specFileName = 'testfile.spec'
		sut.specFile = specFileName

		when:
		sut.execute()

		then:
		1 * testRunner.runSpecFile({it.name == specFileName})
	}

	private org.gradle.api.Project setUpProjectWithExtension() {
		def project = ProjectBuilder.builder().build()
		project.extensions.create("dockertest", DockerTestExtension)
		project.dockertest.testrunner = testRunner
		return project
	}
}
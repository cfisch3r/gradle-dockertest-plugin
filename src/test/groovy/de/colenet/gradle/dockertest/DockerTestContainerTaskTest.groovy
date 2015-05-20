package de.colenet.gradle.dockertest

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification

class DockerTestContainerTaskTest extends Specification {

	private static final String CONTAINER_ID = '123'

	private DockerTestContainerTask sut

	private Project project

	def testRunner = Mock(DockerTestRunner)

	def testScriptDelegate = Mock(TestScriptDelegate)


	def setup() {
		project = ProjectBuilder.builder().build()
		sut = project.task('testContainer', type: DockerTestContainerTask)
		sut.testRunner = testRunner
		def testScriptDelegateFactory = Mock(TestScriptDelegateFactory)
		testScriptDelegateFactory.create(CONTAINER_ID) >> testScriptDelegate
		sut.testScriptDelegateFactory = testScriptDelegateFactory
	}

	def "task is created"() {
		expect:
		sut instanceof DockerTestContainerTask
	}

	def "task should call testrunner with spec files and testscriptdelegate when executed."() {
		setup:
		def specFile = Mock(File)
		sut.specFiles = [specFile]
		sut.containerId  = CONTAINER_ID

		when:
		sut.execute()

		then:
		1 * testRunner.runSpecFile(testScriptDelegate, specFile)
	}
}

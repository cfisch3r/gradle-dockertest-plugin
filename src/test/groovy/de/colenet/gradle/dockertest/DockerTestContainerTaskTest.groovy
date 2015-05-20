package de.colenet.gradle.dockertest

import org.codehaus.groovy.transform.tailrec.VariableReplacedListener.*
import org.gradle.api.logging.Logger
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification

class DockerTestContainerTaskTest extends Specification {

	private static final String CONTAINER_ID = '123'

	private static final String REPORT_DIR_PATH = '/report'

	private static final String SPEC_FILE_PATH = '/spec/test.spec'

	private DockerTestContainerTask sut

	def testRunner = Mock(DockerTestRunner)

	def testScriptDelegate = Mock(TestScriptDelegate)

	def reporter = Mock(DockerTestReporter)

	def specFile = Mock(File)

	def setup() {
		def project = ProjectBuilder.builder().build()
		sut = project.task('testContainer', type: DockerTestContainerTask)
		sut.testRunner = testRunner
		def testScriptDelegateFactory = Mock(TestScriptDelegateFactory)
		testScriptDelegateFactory.create(CONTAINER_ID) >> testScriptDelegate
		sut.testScriptDelegateFactory = testScriptDelegateFactory
		def dockerTestReporterFactory = Mock(DockerTestReporterFactory)
		dockerTestReporterFactory.create(_ as Logger,REPORT_DIR_PATH) >> reporter
		sut.reporterFactory = dockerTestReporterFactory
		sut.reportDirPath = REPORT_DIR_PATH



		specFile.absolutePath >> SPEC_FILE_PATH
		sut.specFiles = [specFile]
		sut.containerId  = CONTAINER_ID
	}

	def "task is created"() {
		expect:
		sut instanceof DockerTestContainerTask
	}

	def "task should call testrunner with spec files and testscriptdelegate when executed."() {

		when:
		sut.execute()

		then:
		1 * testRunner.runSpecFile(testScriptDelegate, specFile)
	}

	def "task should log test start and summary"() {

		when:
		sut.execute()

		then:
		1 * reporter.logTestStart(SPEC_FILE_PATH)
		1 * reporter.logSummary()
	}
}

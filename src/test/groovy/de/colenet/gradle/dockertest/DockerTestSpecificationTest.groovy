package de.colenet.gradle.dockertest

import static org.assertj.core.api.Assertions.*

import org.codehaus.groovy.transform.tailrec.VariableReplacedListener.*

import spock.lang.Specification
import de.colenet.dockertest.DockerClient

class DockerTestSpecificationTest extends Specification {

	private DockerTestSpecification sut

	private DockerTestReporter reporter = Mock(DockerTestReporter)

	private DockerClient client = Mock(DockerClient)

	def setup() {
		sut = new DockerTestSpecification("specification",client,reporter)
	}

	def "requires send test result to reporter"() {
		when:
		sut.requires {}

		then:
		1 * reporter.logSuccess("specification")
	}

	def "requires calls closure"() {
		setup:
		def called = false

		when:
		sut.requires { called = true }

		then:
		called == true
	}

	def "requires delegates execute call to docker client"() {
		setup:
		def containerId = "id"
		def command = "cmd"

		when:
		sut.requires { execute(containerId,command) }

		then:
		1 * client.execute(containerId,command)
	}

	def "requires delegates inspect call to docker client"() {
		setup:
		def attribute = "attribute"

		when:
		sut.requires { inspect(attribute) }

		then:
		1 * client.inspect(attribute)
	}

	def "requires logs exceptions with reporter"() {
		when:
		sut.requires {
			assertThat(false).isTrue()
		}
		then:
		1 * reporter.logFailure("specification",_)
	}
}
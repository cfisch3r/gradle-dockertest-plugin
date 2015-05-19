package de.colenet.gradle.dockertest

import org.gradle.api.Project
import spock.lang.Specification;
import org.gradle.testfixtures.ProjectBuilder

class DockerTestPluginTest extends Specification{
	def "when plugin is applied to project extension is set"() {
		Project project = ProjectBuilder.builder().build()
		
		expect:
		project !=null
		
	}
}

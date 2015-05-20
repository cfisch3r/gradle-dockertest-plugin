package de.colenet.gradle.dockertest

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification
import de.colenet.dockertest.DockerClient

class DockerTestPluginTest extends Specification{
	def "when plugin is applied to project extension is set"() {
		Project project = ProjectBuilder.builder().build()

		when:
		project.repositories.mavenLocal()
		Configuration config = project.configurations.create("dockertest")
		config.setVisible(false)
				.setTransitive(true)
				.setDescription('The Docker Java libraries to be used for this project.')
		Dependency dockerClientDependency = project.dependencies.create('de.colenet:dockerclient:0.1')
		config.dependencies.add(dockerClientDependency)
		URL[] urls = config.files.collect { file -> file.toURI().toURL() } as URL[]
		println urls
		def classLoader = new URLClassLoader(urls, ClassLoader.systemClassLoader.parent)
		ServiceLoader<DockerClient> loader = ServiceLoader.load(DockerClient.class, classLoader)


		then:
		loader.iterator().hasNext() == true
	}
}

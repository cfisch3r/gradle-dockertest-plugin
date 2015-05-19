package de.colenet.docker.tdd

import groovy.lang.Closure;
import groovy.lang.Lazy;
import org.apache.commons.io.IOUtils

/**
 * ContainerTest Delegate with DSL.
 *
 */
class DockerSpecificationDSL {
	Closure getDockerClient
	
		private String containerId
	
		private String specDescription
		
		private dockerClient
		
		/**
		 * List of closures called after a successfully passed test.
		 */
		List<Closure> onTestSuccess = []
	
		/**
		 * List of closures called after a failed test.
		 */
		List<Closure> onTestFailure = []
		
		/**
		 * Constructor.
		 * 
		 * @param containerId Docker Container ID
		 * @param dockerClient Docker Client
		 */
		DockerSpecificationDSL (String containerId, dockerClient) {
			this.containerId = containerId
			this.dockerClient = dockerClient
		}
		
		/**
		 * runs Docker exec command on container.
		 * 	
		 * @param commands list of commands to be executed.
		 * @return standard and error output of command
		 */
		String command(String... commands) {
			def response = dockerClient.execCreateCmd(containerId).withAttachStdout().withAttachStderr().withCmd(commands).exec()
			def inputStream = dockerClient.execStartCmd(containerId).withExecId(response.getId()).exec()
			def output = IOUtils.toString(inputStream, "UTF-8");
			inputStream.close()
			return output
		}
		
		/**
		 * starts a container test specification.
		 * 
		 * @param description specification
		 * @return self
		 */
		DockerSpecificationDSL specification(String description) {
			specDescription = description
			return this
		}
	
		/**
		 * executes assertion closure.
		 * 
		 * @param assertion assertions.
		 */
		void requires(Closure assertion) {
			 assertion.delegate = this
			assertion.resolveStrategy = Closure.DELEGATE_FIRST
			try {
				assertion.call()
				onTestSuccess.each { it(specDescription) }
			} catch (AssertionError e) {
				onTestFailure.each { it(specDescription, e) }
			}
		}
	
		/**
		 * Information from docker inspect command
		 * @see com.github.dockerjava.api.command.InspectContainerResponse
		 */
		@Lazy container = {
			return dockerClient.inspectContainerCmd(containerId).exec()
		}()
	
}

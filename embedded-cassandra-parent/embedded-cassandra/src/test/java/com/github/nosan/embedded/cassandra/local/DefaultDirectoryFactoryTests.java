/*
 * Copyright 2018-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nosan.embedded.cassandra.local;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.github.nosan.embedded.cassandra.Version;
import com.github.nosan.embedded.cassandra.local.artifact.Artifact;
import com.github.nosan.embedded.cassandra.test.support.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultDirectoryFactory}.
 *
 * @author Dmytro Nosan
 */
public class DefaultDirectoryFactoryTests {

	@Test
	@SuppressWarnings("unchecked")
	public void shouldCreateDirectory() throws Exception {
		Version version = new Version(3, 11, 5);
		Path workingDirectory = Paths.get(UUID.randomUUID().toString());
		URL logbackFile = Paths.get("logback.xml").toUri().toURL();
		URL configurationFile = Paths.get("cassandra.yaml").toUri().toURL();
		URL rackFile = Paths.get("rack.properties").toUri().toURL();
		URL topologyFile = Paths.get("topology.properties").toUri().toURL();
		DefaultDirectoryFactory factory = new DefaultDirectoryFactory(version, workingDirectory, configurationFile,
				logbackFile, rackFile, topologyFile);

		Artifact artifact = () -> Paths.get("artifact");

		Directory directory = factory.create(artifact);
		assertThat(ReflectionUtils.getField(directory, "directory")).isEqualTo(workingDirectory);
		assertThat(ReflectionUtils.getField(directory, "artifact")).isEqualTo(artifact);
		List<? extends DirectoryCustomizer> customizers =
				(List<? extends DirectoryCustomizer>) ReflectionUtils.getField(directory, "customizers");

		assertThat(customizers).hasSize(5);
		assertThat(ReflectionUtils.getField(customizers.get(0), "logbackFile")).isEqualTo(logbackFile);
		assertThat(ReflectionUtils.getField(customizers.get(1), "configurationFile")).isEqualTo(configurationFile);
		assertThat(ReflectionUtils.getField(customizers.get(2), "rackFile")).isEqualTo(rackFile);
		assertThat(ReflectionUtils.getField(customizers.get(3), "topologyFile")).isEqualTo(topologyFile);
		assertThat(customizers).last().isInstanceOf(PortReplacerCustomizer.class);

	}
}
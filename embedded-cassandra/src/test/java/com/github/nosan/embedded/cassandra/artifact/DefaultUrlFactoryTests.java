/*
 * Copyright 2018-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nosan.embedded.cassandra.artifact;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.github.nosan.embedded.cassandra.api.Version;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultUrlFactory}.
 *
 * @author Dmytro Nosan
 */
class DefaultUrlFactoryTests {

	private final DefaultUrlFactory urlFactory = new DefaultUrlFactory();

	@Test
	void testCreate() throws IOException {
		assertThat(this.urlFactory.create(Version.of("4.0-alpha3"))).isEqualTo(Arrays.asList(
				new URL("https://apache.org/dyn/closer.cgi?action=download&filename=cassandra/4.0-alpha3/"
						+ "apache-cassandra-4.0-alpha3-bin.tar.gz"),
				new URL("https://dist.apache.org/repos/dist/release/cassandra/4.0-alpha3/"
						+ "apache-cassandra-4.0-alpha3-bin.tar.gz"),
				new URL("https://archive.apache.org/dist/cassandra/4.0-alpha3/apache-cassandra-4.0-alpha3-bin.tar.gz")));

	}

}

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

package com.github.nosan.embedded.cassandra.test.testng;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.datastax.driver.core.Cluster;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.github.nosan.embedded.cassandra.Cassandra;
import com.github.nosan.embedded.cassandra.CassandraException;
import com.github.nosan.embedded.cassandra.CassandraFactory;
import com.github.nosan.embedded.cassandra.cql.CqlScript;
import com.github.nosan.embedded.cassandra.test.ClusterFactory;
import com.github.nosan.embedded.cassandra.test.TestCassandra;

/**
 * Base {@code test class} that allows the Cassandra to be {@link Cassandra#start() started} and
 * {@link Cassandra#stop() stopped}.
 * <p>
 * The typical usage is:
 * <pre>
 * public class CassandraTests extends CassandraTestNG {
 * &#64;Test
 * public void test() {
 * //
 * }
 * }
 * </pre>
 *
 * @author Dmytro Nosan
 * @since 1.0.0
 */
public class CassandraTestNG extends TestCassandra {

	/**
	 * Creates a {@link CassandraTestNG}.
	 *
	 * @param scripts CQL scripts to execute
	 */
	public CassandraTestNG(@Nullable CqlScript... scripts) {
		this(null, null, scripts);
	}

	/**
	 * Creates a {@link CassandraTestNG}.
	 *
	 * @param clusterFactory factory to create a {@link Cluster}
	 * @param scripts CQL scripts to execute
	 */
	public CassandraTestNG(@Nonnull ClusterFactory clusterFactory,
			@Nonnull CqlScript... scripts) {
		this(null, clusterFactory, scripts);
	}

	/**
	 * Creates a {@link CassandraTestNG}.
	 *
	 * @param cassandraFactory factory to create a {@link Cassandra}
	 * @param scripts CQL scripts to execute
	 */
	public CassandraTestNG(@Nullable CassandraFactory cassandraFactory, @Nullable CqlScript... scripts) {
		this(cassandraFactory, null, scripts);
	}

	/**
	 * Creates a {@link CassandraTestNG}.
	 *
	 * @param cassandraFactory factory to create a {@link Cassandra}
	 * @param clusterFactory factory to create a {@link Cluster}
	 * @param scripts CQL scripts to execute
	 */
	public CassandraTestNG(@Nullable CassandraFactory cassandraFactory,
			@Nullable ClusterFactory clusterFactory, @Nullable CqlScript... scripts) {
		super(cassandraFactory, clusterFactory, scripts);
	}

	@Override
	@BeforeClass(alwaysRun = true)
	public void start() throws CassandraException {
		super.start();
	}


	@Override
	@AfterClass(alwaysRun = true)
	public void stop() throws CassandraException {
		super.stop();
	}


}
/*
 * Copyright 2018-2019 the original author or authors.
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

package com.github.nosan.embedded.cassandra.test.spring;

import com.github.nosan.embedded.cassandra.Cassandra;
import com.github.nosan.embedded.cassandra.CassandraFactory;
import com.github.nosan.embedded.cassandra.cql.CqlScript;
import com.github.nosan.embedded.cassandra.lang.annotation.Nullable;
import com.github.nosan.embedded.cassandra.test.Connection;
import com.github.nosan.embedded.cassandra.test.ConnectionFactory;
import com.github.nosan.embedded.cassandra.test.TestCassandra;

/**
 * Factory that creates {@link TestCassandra}.
 *
 * @author Dmytro Nosan
 * @since 2.0.0
 */
@FunctionalInterface
public interface TestCassandraFactory {

	/**
	 * Creates {@link TestCassandra}.
	 *
	 * @param cassandraFactory factory that creates {@link Cassandra}
	 * @param connectionFactory factory that creates {@link Connection}.
	 * @param scripts the {@link CqlScript scripts} to execute
	 * @return the {@link TestCassandra}
	 */
	TestCassandra create(@Nullable CassandraFactory cassandraFactory,
			@Nullable ConnectionFactory connectionFactory, CqlScript... scripts);

}

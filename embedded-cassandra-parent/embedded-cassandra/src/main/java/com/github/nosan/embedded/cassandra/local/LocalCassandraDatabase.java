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

package com.github.nosan.embedded.cassandra.local;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nosan.embedded.cassandra.Settings;
import com.github.nosan.embedded.cassandra.Version;

/**
 * A simple implementation of {@link CassandraDatabase}.
 *
 * @author Dmytro Nosan
 * @since 2.0.0
 */
class LocalCassandraDatabase implements CassandraDatabase {

	private static final Logger log = LoggerFactory.getLogger(LocalCassandraDatabase.class);

	private final CassandraNode node;

	private final Path workingDirectory;

	private final boolean deleteWorkingDirectory;

	private final List<WorkingDirectoryCustomizer> workingDirectoryCustomizers;

	LocalCassandraDatabase(CassandraNode node, Path workingDirectory, boolean deleteWorkingDirectory,
			List<WorkingDirectoryCustomizer> workingDirectoryCustomizers) {
		this.node = node;
		this.workingDirectory = workingDirectory;
		this.deleteWorkingDirectory = deleteWorkingDirectory;
		this.workingDirectoryCustomizers = Collections.unmodifiableList(new ArrayList<>(workingDirectoryCustomizers));
	}

	@Override
	public void start() throws IOException, InterruptedException {
		initialize();
		log.info("Start Apache Cassandra '{}'", getVersion());
		long start = System.currentTimeMillis();
		this.node.start();
		long elapsed = System.currentTimeMillis() - start;
		log.info("Apache Cassandra '{}' is started ({} ms)", getVersion(), elapsed);
	}

	@Override
	public void stop() throws IOException, InterruptedException {
		if (this.node.isAlive()) {
			long start = System.currentTimeMillis();
			log.info("Stop Apache Cassandra '{}'", getVersion());
			this.node.stop();
			long elapsed = System.currentTimeMillis() - start;
			log.info("Apache Cassandra '{}' is stopped ({} ms)", getVersion(), elapsed);
		}
		delete();
	}

	@Override
	public Version getVersion() {
		return this.node.getVersion();
	}

	@Override
	public Settings getSettings() {
		return this.node.getSettings();
	}

	private void initialize() throws IOException {
		log.info("Initialize Apache Cassandra '{}'. It takes a while...", getVersion());
		long start = System.currentTimeMillis();
		for (WorkingDirectoryCustomizer customizer : this.workingDirectoryCustomizers) {
			customizer.customize(this.workingDirectory, getVersion());
		}
		long elapsed = System.currentTimeMillis() - start;
		log.info("Apache Cassandra '{}' is initialized ({} ms)", getVersion(), elapsed);
	}

	private void delete() throws IOException {
		if (this.deleteWorkingDirectory && FileUtils.delete(this.workingDirectory)) {
			log.info("The working directory '{}' was deleted.", this.workingDirectory);
		}
	}

}

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

package com.github.nosan.embedded.cassandra.artifact;

import java.io.File;
import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nosan.embedded.cassandra.annotations.Nullable;
import com.github.nosan.embedded.cassandra.api.Version;
import com.github.nosan.embedded.cassandra.commons.FileLock;
import com.github.nosan.embedded.cassandra.commons.io.Resource;
import com.github.nosan.embedded.cassandra.commons.util.FileUtils;

/**
 * An {@link Artifact} that provides a {@link Descriptor} based on the specified archive resource and Cassandra's
 * version. Archive will be extracted only once into the {@code destination}.
 *
 * @author Dmytro Nosan
 * @since 3.0.0
 */
public final class ArchiveArtifact implements Artifact {

	private static final Logger log = LoggerFactory.getLogger(ArchiveArtifact.class);

	private final Version version;

	private final Resource archiveResource;

	@Nullable
	private Path destination;

	/**
	 * Constructs a new {@link ArchiveArtifact} with the specified archive resource and Cassandra's version.
	 *
	 * @param version Cassandra's version
	 * @param archiveResource the archive resource
	 */
	public ArchiveArtifact(Version version, Resource archiveResource) {
		this.version = Objects.requireNonNull(version, "'version' must not be null");
		this.archiveResource = Objects.requireNonNull(archiveResource, "'archiveResource' must not be null");
	}

	/**
	 * Returns Cassandra's version.
	 *
	 * @return the version
	 */
	public Version getVersion() {
		return this.version;
	}

	/**
	 * Returns archive file.
	 *
	 * @return the archive file
	 */
	public Resource getArchiveResource() {
		return this.archiveResource;
	}

	/**
	 * Directory used to extract an archive file. Defaults to {@code user.home}
	 *
	 * @return the directory
	 */
	@Nullable
	public Path getDestination() {
		return this.destination;
	}

	/**
	 * Sets directory to extract an archive file.
	 *
	 * @param destination the path to the directory
	 */
	public void setDestination(@Nullable Path destination) {
		this.destination = destination;
	}

	@Override
	public Artifact.Descriptor getDescriptor() throws IOException {
		Path destination = getRealDestination();

		Artifact artifact = new DefaultArtifact(this.version, destination);

		if (!Files.exists(destination.resolve(".extracted"))) {
			Files.createDirectories(destination);
			Path lockFile = destination.resolve(".lock");
			try (FileLock fileLock = FileLock.of(lockFile)) {
				if (!fileLock.tryLock(30, TimeUnit.SECONDS)) {
					throw new IllegalStateException("File lock cannot be acquired for a file '" + lockFile + "'");
				}
				if (!Files.exists(destination.resolve(".extracted"))) {
					extract(this.archiveResource, destination);
					Artifact.Descriptor descriptor = artifact.getDescriptor();
					FileUtils.createIfNotExists(destination.resolve(".extracted"));
					return descriptor;
				}
			}
		}

		return artifact.getDescriptor();
	}

	private Path getRealDestination() {
		Path destination = this.destination;
		if (destination == null) {
			destination = Optional.ofNullable(System.getProperty("user.home")).map(Paths::get).orElse(null);
		}
		if (destination == null) {
			throw new IllegalStateException("'destination' must not be null");
		}
		return destination.resolve(".embedded-cassandra/artifact/local/" + this.version);
	}

	private void extract(Resource archiveResource, Path destination) throws IOException {
		log.info("Extracts '{}' into '{}' directory", archiveResource, destination);
		File archive = archiveResource.toFile();
		Archiver archiver = ArchiverFactory.createArchiver(archive);
		archiver.extract(archive, destination.toFile());
		if (Thread.interrupted()) {
			throw new ClosedByInterruptException();
		}
	}

}

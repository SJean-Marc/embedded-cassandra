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

package com.github.nosan.embedded.cassandra.commons;

import java.nio.file.Path;
import java.util.Objects;

/**
 * {@link PathSupplier} that returns the specified path.
 *
 * @author Dmytro Nosan
 * @since 3.0.0
 */
public class DefaultPathSupplier implements PathSupplier {

	private final Path path;

	/**
	 * Constructs a {@link DefaultPathSupplier} with a specified path.
	 *
	 * @param path path to the directory
	 */
	public DefaultPathSupplier(Path path) {
		this.path = Objects.requireNonNull(path, "'path' must not be null");
	}

	@Override
	public Path get() {
		return this.path;
	}

}

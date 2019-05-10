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

import com.github.nosan.embedded.cassandra.Version;

/**
 * A basic interface to customize a working directory.
 *
 * @author Dmytro Nosan
 * @since 2.0.0
 */
@FunctionalInterface
public interface WorkingDirectoryCustomizer {

	/**
	 * Customize a working directory.
	 *
	 * @param workingDirectory a working directory
	 * @param version a version
	 * @throws IOException in the case of any I/O errors
	 */
	void customize(Path workingDirectory, Version version) throws IOException;

}

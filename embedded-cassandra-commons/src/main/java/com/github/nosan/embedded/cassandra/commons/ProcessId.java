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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import com.github.nosan.embedded.cassandra.annotations.Nullable;

/**
 * Representation of {@link Process} and its PID.
 *
 * @author Dmytro Nosan
 * @since 3.0.0
 */
public final class ProcessId {

	@Nullable
	private static final Method PID_METHOD;

	static {
		Method method;
		try {
			method = Process.class.getMethod("pid");
		}
		catch (NoSuchMethodException ex) {
			method = null;
		}
		PID_METHOD = method;
	}

	private final Process process;

	private final long pid;

	/**
	 * Constructs a new {@link ProcessId} with the specified process.
	 *
	 * @param process the process
	 */
	public ProcessId(Process process) {
		Objects.requireNonNull(process, "'process' must not be null");
		this.process = process;
		this.pid = getPid(process);
	}

	/**
	 * Returns the {@link Process}.
	 *
	 * @return the process
	 */
	public Process getProcess() {
		return this.process;
	}

	/**
	 * Returns the pid.
	 *
	 * @return the pid (or -1 if none)
	 */
	public long getPid() {
		return this.pid;
	}

	@Override
	public String toString() {
		return String.format("%s [%d]", this.process, this.pid);
	}

	private static long getPid(Process process) {
		try {
			if (PID_METHOD != null) {
				return (long) PID_METHOD.invoke(process);
			}
			Field field = process.getClass().getDeclaredField("pid");
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			return (int) field.get(process);
		}
		catch (NoSuchFieldException ex) {
			return -1;
		}
		catch (ReflectiveOperationException ex) {
			throw new IllegalStateException(ex);
		}
	}

}
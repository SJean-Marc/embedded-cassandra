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

package com.github.nosan.embedded.cassandra;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import com.github.nosan.embedded.cassandra.annotations.Nullable;

/**
 * A utility class to get a PID of the {@link Process}.
 *
 * @author Dmytro Nosan
 */
final class Pid {

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

	private Pid() {
	}

	/**
	 * Determines the given process PID or {@code -1}.
	 *
	 * @param process the process
	 * @return PID or -1
	 */
	static long get(Process process) {
		try {
			if (PID_METHOD != null) {
				return getLong(PID_METHOD.invoke(process));
			}
			Field field = process.getClass().getDeclaredField("pid");
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			return getLong(field.get(process));
		}
		catch (Throwable ex) {
			return -1;
		}
	}

	private static long getLong(Object result) {
		return Long.parseLong(Objects.toString(result, "-1"));
	}

}

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

package com.github.nosan.embedded.cassandra.cql;

import java.util.List;
import java.util.StringJoiner;

import com.github.nosan.embedded.cassandra.annotations.Nullable;

/**
 * {@link CqlScript} for a raw CQL scripts.
 *
 * @author Dmytro Nosan
 */
final class ScriptCqlScript implements CqlScript {

	private final String script;

	/**
	 * Constructs a new {@link ScriptCqlScript} with the specified CQL script.
	 *
	 * @param script the CQL script
	 */
	ScriptCqlScript(String script) {
		this.script = script;
	}

	@Override
	public List<String> getStatements() {
		return new Parser(this.script).getStatements();
	}

	@Override
	public boolean equals(@Nullable Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}

		ScriptCqlScript that = (ScriptCqlScript) other;

		return this.script.equals(that.script);
	}

	@Override
	public int hashCode() {
		return this.script.hashCode();
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", ScriptCqlScript.class.getSimpleName() + "[", "]").add("script=" + this.script)
				.toString();
	}

}
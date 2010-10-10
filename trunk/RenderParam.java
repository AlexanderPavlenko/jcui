/**
 * Java Console UI Framework
 * Copyright (C) 2010 Alerticus <mailto:alerticus@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ru.alerticus.jcui;

/**
 * Represents the menu item's parameter. For internal use only.
 */
class RenderParam {
	private Object value;
	private final Class<?> type;
	private final String request;
	private final ParamFilter filter;

	/**
	 * @param type Variable's class.
	 * @param request The prompt string for obtaining this value.
	 */
	public RenderParam(Class<?> type, String request) {
		this(type, request, null);
	}

	/**
	 * @param type Variable's class.
	 * @param request The prompt string for obtaining this value.
	 * @param filter Filter for this variable.
	 */
	public RenderParam(Class<?> type, String request, ParamFilter filter) {
		this.type = type;
		this.request = request;
		this.filter = filter;
	}

	/**
	 * @return Variable's value.
	 */
	public Object get() {
		return value;
	}

	/**
	 * For internal use in {@link ConsoleUI#obtainParams(MenuItem item)}.
	 * @param value Variable's value.
	 */
	protected void set(Object value) {
		if (value.getClass() != type) {
			throw new IllegalArgumentException("given: " + value.getClass().getName() + ", expected: " + type.getName());
		}
		this.value = value;
	}

	/**
	 * @return Variable's class.
	 */
	public Class<?> getValueType() {
		return type;
	}

	/**
	 * @return true, if this parameter has any associated filter.
	 */
	public boolean hasFilter() {
		return filter != null;
	}

	/**
	 * @return Filter's object.
	 */
	public ParamFilter getFilter() {
		return filter;
	}

	@Override
	public String toString() {
		return request;
	}
}

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
 * Realization of {@link ParamFilter} which rejects value that is less or equal 0.
 */
public class ParamAboveZero implements ParamFilter {
	private static final Integer z = new Integer(0);

	/**
	 * @param value Should be {@link Comparable}.
	 * @return Given value if it's greater then 0, otherwise null.
	 */
	public Object filter(Object value) {
		return ((value instanceof Comparable) && (((Comparable) value).compareTo(new Integer(0)) > 0)) ? value : null;
	}
}

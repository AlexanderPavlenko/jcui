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

import java.util.HashMap;
import java.util.Iterator;

/**
 * Abstract class for the menu items.
 */
public abstract class MenuItem {
	private final String title;
	private HashMap<String, RenderParam> params = null;
	private SubMenu back;

	/**
	 * @param title String which will be shown during the parent SubMenu rendering.
	 */
	public MenuItem(String title) {
		this(title, null);
	}

	/**
	 * @param title String which will be shown during the parent SubMenu rendering.
	 * @param back Defines menu which will be shown after this item render or
	 * after back move if this item is menu too. parent submenu by default.
	 */
	public MenuItem(String title, SubMenu back) {
		this.title = title;
		this.back = back;
	}

	/**
	 * @param menu Defines menu which will be shown after this item render or
	 * after back move if this item is menu too. parent submenu by default.
	 */
	public void setBack(SubMenu menu) {
		back = menu;
	}

	/**
	 * @return This item's back menu.
	 * @see setBack(SubMenu menu)
	 */
	public SubMenu getBack() {
		return back;
	}

	/**
	 * Adds associated variable which will be obtained from user without filtering.
	 * @see addParam(String name, String type, String request, ParamFilter filter)
	 * @return true, if variable was associated.
	 */
	public boolean addParam(String name, Class<?> type, String request) {
		return addParam(name, type, request, null);
	}

	/**
	 * Adds associated variable which will be obtained from user.
	 * @param name Parameter's name to access it next time.
	 * @param type Parameter's class. Valid are Number's subclasses or String.
	 * @param request String which will be shown as the prompt for the user input.
	 * @param filter Filter which rejects invalid values.
	 * @return true, if variable was associated.
	 */
	public boolean addParam(String name, Class<?> type, String request, ParamFilter filter) {
		if (params == null) {
			params = new HashMap<String, RenderParam>();
		}
		if (type.getSuperclass() == Number.class || type == String.class) {
			params.put(name, new RenderParam(type, request, filter));
			return true;
		}
		return false;
	}

	/**
	 * @return true, if this menu item has any associated variables.
	 */
	public boolean hasParams() {
		return params != null && params.size() > 0;
	}

	/**
	 * For internal use in {@link ConsoleUI#obtainParams(MenuItem item)}.
	 * Use {@link getValue(String key)} in your realizations instead.
	 * @return Iterator upon menu item's parameters.
	 */
	protected Iterator<java.util.Map.Entry<String, RenderParam>> getParamIterator() {
		return params.entrySet().iterator();
	}

	/**
	 * Use this method during the rendering to obtain
	 * the associated variable's value entered by user.
	 * @param key Parameter's name. Should be set before
	 * by {@link addParam(String name, Class<?> type, String request)}
	 * @return Associated variable's value.
	 */
	protected Object getValue(String key) {
		return params.get(key).get();
	}

	/**
	 * For internal use in {@link ConsoleUI#obtainParams(MenuItem item)}.
	 * @param key Variable's name.
	 * @param value Variable's value to set.
	 */
	protected void setValue(String key, Object value) {
		RenderParam p = params.get(key);
		if (p.getValueType() != value.getClass()) {
			throw new java.lang.ClassCastException();
		}
		p.set(value);
	}

	@Override
	public String toString() {
		return title;
	}

	/**
	 * This method is called when this menu item was selected by user.
	 * Can display something or just call another methods due to MVC.
	 */
	public abstract void render();
}

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

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Realization of {@link MenuItem} which can contain another menu items.
 */
public class SubMenu extends MenuItem {
	private final LinkedList<MenuItem> menu = new LinkedList<MenuItem>();
	private String title_prefix = "---: ";
	private String title_suffix = " :---";

	/**
	 * @param title String which will be shown during the parent SubMenu rendering.
	 */
	public SubMenu(String title) {
		super(title);
	}

	/**
	 * Adds child menu item.
	 * @param item Child menu item.
	 * @return true
	 */
	public boolean add(MenuItem item) {
		if (item.getBack() == null) {
			item.setBack(this);
		}
		return menu.add(item);
	}

	/**
	 * Removes child menu item.
	 * @param item Child menu item.
	 * @return true, if this menu contained the specified child menu item.
	 */
	public boolean remove(MenuItem item) {
		return menu.remove(item);
	}

	/**
	 * @param index Child menu item's index.
	 * @return Child menu item.
	 * @throws IndexOutOfBoundsException
	 */
	public MenuItem get(int index) {
		return menu.get(index);
	}

	/**
	 * @return The number of child menu items.
	 */
	public int size() {
		return menu.size();
	}

	@Override
	public void render() {
		ListIterator<MenuItem> iter = menu.listIterator();
		System.out.println(title_prefix + this + title_suffix);
		while (iter.hasNext()) {
			System.out.println((iter.nextIndex() + 1) + ". " + iter.next());
		}
	}

	/**
	 * @return String which is shown before the menu title.
	 */
	public String getTitlePrefix() {
		return title_prefix;
	}

	/**
	 * @param value String which will be shown before the menu title.
	 */
	public void setTitlePrefix(String value) {
		title_prefix = value;
	}

	/**
	 * @return String which is shown after the menu title.
	 */
	public String getTitleSuffix() {
		return title_suffix;
	}

	/**
	 * @param value String which will be shown after the menu title.
	 */
	public void setTitleSuffix(String value) {
		title_suffix = value;
	}
}

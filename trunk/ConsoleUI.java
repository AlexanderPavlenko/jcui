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

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main class, contains the user input handling, menus switching and action rendering.
 */
public class ConsoleUI implements Runnable {
	private static String LOCAL_BACK;
	private final SubMenu menu;
	private final Scanner in = new Scanner(System.in);
	private static final Logger logger = Logger.getLogger(ConsoleUI.class.getName());
	private String menu_prompt = ">> ";
	private String param_prompt = " > ";

	/**
	 * @param menu Sets the menu which will be shown at first.
	 */
	public ConsoleUI(SubMenu menu) {
		this.menu = menu;
		LOCAL_BACK = "Back";
	}

	/**
	 * Starts the main UI loop in the separated thread.
	 */
	@Override
	public void run() {
		SubMenu curr = menu;
		int pos = -1;
		obtainParams(curr);
		curr.render();
		while (true) {
			System.out.print(menu_prompt);
			if (in.hasNextInt()) {
				pos = in.nextInt() - 1;
			} else {
				in.next();
				continue;
			}
			if (pos == -1) {
				curr = curr.getBack();
				if (curr == null) {
					curr = menu;
				}
			} else if (pos > curr.size() - 1 || pos < -1) {
				continue;
			} else if (curr.get(pos) instanceof SubMenu) {
				curr = (SubMenu) curr.get(pos);
			} else {
				MenuItem action = curr.get(pos);
				obtainParams(action);
				action.render();
				curr = action.getBack();
			}
			obtainParams(curr);
			curr.render();
			if (curr.getBack() != null) {
				System.out.println("0. " + LOCAL_BACK);
			}
		}
	}

	/**
	 * Scans user input and puts them into the associated variables.
	 * @param item Menu whose parameters will be obtained.
	 */
	private void obtainParams(MenuItem item) {
		if (!item.hasParams()) {
			return;
		}
		Iterator<Map.Entry<String, RenderParam>> iter = item.getParamIterator();
		while (iter.hasNext()) {
			RenderParam param = iter.next().getValue();
			Object value = null;
			while (value == null) {
				System.out.print(param + param_prompt);
				try {
					if (in.hasNext()) {
						if (param.getValueType() == String.class) {
							value = in.next();
						} else {
							value = param.getValueType().getDeclaredMethod("valueOf", String.class).invoke(param, in.next());
						}
						if (param.hasFilter()) {
							value = param.getFilter().filter(value);
						}
					}
				} catch (java.lang.reflect.InvocationTargetException ex) {
					if (!(ex.getCause() instanceof NumberFormatException)) {
						doLog(ex);
					}  // else try again, dear user
				} catch (Exception ex) {
					doLog(ex);
				}
			}
			param.set(value);
		}
	}

	/**
	 * Logging our buggy things =)
	 */
	private void doLog(Exception ex) {
		logger.log(Level.SEVERE, null, ex);
	}

	/**
	 * @return String which is shown as the prompt for the menu item selection.
	 */
	public String getMenuPrompt() {
		return menu_prompt;
	}

	/**
	 * @param value String which will be shown as the prompt for the menu item selection.
	 */
	public void setMenuPrompt(String value) {
		menu_prompt = value;
	}

	/**
	 * @return String which is shown after the prompt for the user input.
	 */
	public String getParamPrompt() {
		return param_prompt;
	}

	/**
	 * @param value String which will be shown after the prompt for the user input.
	 */
	public void setParamPrompt(String value) {
		param_prompt = value;
	}
}

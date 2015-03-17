# Introduction #

jcui is easy to use as shown below.

# Example #

```
import ru.alerticus.jcui.*;

public class Example {
   public static void main(String[] args) {
      SubMenu mainMenu = new SubMenu("Main menu");
      SubMenu subMenu = new SubMenu("Sub menu");
      subMenu.add(new MenuItem("Menu item", mainMenu) {
         public void render() {
            System.out.println("Hello from sub menu!");
         }
      });
      mainMenu.add(subMenu);
      MenuItem activeItem = new MenuItem("Acquaintance") {
         public void render() {
            System.out.println("Hello, " + getValue("name") + "! I know, you are " + getValue("age") + '.');
         }
      };
      activeItem.addParam("name", String.class, "Enter your name");
      activeItem.addParam("age", Short.class, "Enter your age", new ParamFilter() {
         public Object filter(Object obj) {
            Short value = (Short) obj;
            return ((value > 0) && (value < 121)) ? value : null;
         }
      });
      mainMenu.add(activeItem);
      ConsoleUI ui = new ConsoleUI(mainMenu);
      ui.run();
   }
}
```
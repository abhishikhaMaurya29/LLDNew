package model;

import java.util.Map;

// Read-heavy, write-light structure
public class Menu {
    private final String menuId;
    private final Map<String, MenuItem> items;

    public Menu(String menuId, Map<String, MenuItem> items) {
        this.menuId = menuId;
        this.items = items;
    }

    public String getMenuId() {
        return menuId;
    }

    public Map<String, MenuItem> getItems() {
        return items;
    }
}
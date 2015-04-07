package com.henrychua.mydailyassessment.models;

/**
 * Created by henrychua on 06/11/2014.
 */
public class DrawerItem {
    private int icon;
    private int iconSelected;
    private String name;
    private Boolean isItemSelected;


    // Constructor.
    public DrawerItem(int icon, int iconSelected, String name, Boolean isItemSelected) {
        this.icon = icon;
        this.iconSelected = iconSelected;
        this.name = name;
        this.isItemSelected = isItemSelected;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconSelected() {
        return iconSelected;
    }

    public void setIconSelected(int iconSelected) {
        this.iconSelected = iconSelected;
    }

    public Boolean getIsItemSelected() {
        return isItemSelected;
    }

    public void setIsItemSelected(Boolean isItemSelected) {
        this.isItemSelected = isItemSelected;
    }
}

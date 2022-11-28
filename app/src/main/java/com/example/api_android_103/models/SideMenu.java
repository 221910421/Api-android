package com.example.api_android_103.models;

import com.example.api_android_103.MainActivity;

public class SideMenu {
    public int icon;
    public String name;

    public SideMenu(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public SideMenu(MainActivity mainActivity, int list_view_item_row, SideMenu[] drawerItem) {
    }
}

package com.aymardkouakou.tablayoutcoordinator.listener;

import com.google.android.material.tabs.TabLayout;

public interface OnTabSelectedListener {

    void onTabSelected(TabLayout.Tab tab);

    void onTabUnselected(TabLayout.Tab tab);

    void onTabReselected(TabLayout.Tab tab);
}

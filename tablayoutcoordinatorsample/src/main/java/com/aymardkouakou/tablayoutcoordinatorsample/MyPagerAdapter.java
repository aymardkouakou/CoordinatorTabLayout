package com.aymardkouakou.tablayoutcoordinatorsample;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by hugeterry(http://hugeterry.cn)
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;

    MyPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragments = new ArrayList<>();
    }

    public void withFragments(ArrayList<Fragment> fragments) {
        this.mFragments.addAll(fragments);
    }

    @Override
    public int getCount() {
        return this.mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        MainFragment fragment = (MainFragment) getItem(position);
        return fragment.getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }
}

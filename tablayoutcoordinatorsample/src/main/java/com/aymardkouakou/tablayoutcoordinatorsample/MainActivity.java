package com.aymardkouakou.tablayoutcoordinatorsample;

import android.os.Bundle;

import com.aymardkouakou.tablayoutcoordinator.TabLayoutCoordinator;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private TabLayoutCoordinator mTabLayoutCoordinator;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"Android", "iOS", "Web", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        initViewPager();
        int[] mImageArray = new int[]{
                R.mipmap.bg_android,
                R.mipmap.bg_ios,
                R.mipmap.bg_js,
                R.mipmap.bg_other};
        int[] mColorArray = new int[]{
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light};

        mTabLayoutCoordinator = findViewById(R.id.tablayoutcoordinator);
        mTabLayoutCoordinator.setTranslucentStatusBar(this)
                .setTitle("Demo for life")
                .setDisplayHomeAsUpEnabled(true)
                .setImageArray(mImageArray, mColorArray)
                .setupWithViewPager(mViewPager);
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        for (String title : mTitles) {
            mFragments.add(MainFragment.getInstance().withTitle(title));
        }
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.vp);
        mViewPager.setOffscreenPageLimit(4);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.withFragments(mFragments);
        mViewPager.setAdapter(adapter);
    }

}

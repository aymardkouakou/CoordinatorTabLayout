package com.aymardkouakou.tablayoutcoordinator;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.aymardkouakou.tablayoutcoordinator.listener.LoadHeaderImagesListener;
import com.aymardkouakou.tablayoutcoordinator.utils.SystemView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class TabLayoutCoordinator extends CoordinatorLayout {
    private int[] mImageArray, mColorArray;

    private Context mContext;
    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private TabLayout mTabLayout;
    private ImageView mImageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LoadHeaderImagesListener mLoadHeaderImagesListener;
    private TabLayout.OnTabSelectedListener mOnTabSelectedListener;

    public TabLayoutCoordinator(Context context) {
        super(context);
        mContext = context;
    }

    public TabLayoutCoordinator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (!isInEditMode()) {
            initView(context);
            initWidget(context, attrs);
        }
    }

    public TabLayoutCoordinator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (!isInEditMode()) {
            initView(context);
            initWidget(context, attrs);
        }
    }

    private void initView(Context context) {
        final View v = LayoutInflater.from(context).inflate(R.layout.view_coordinatortablayout, this, true);
        initToolbar(v);
        mCollapsingToolbarLayout = v.findViewById(R.id.collapsing_toolbar);
        mTabLayout = v.findViewById(R.id.tabLayout);
        mImageView = v.findViewById(R.id.imageview);
    }

    private void initWidget(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs
                , R.styleable.TabLayoutCoordinator);

        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int contentScrimColor = typedArray.getColor(
                R.styleable.TabLayoutCoordinator_contentScrim, typedValue.data);
        mCollapsingToolbarLayout.setContentScrimColor(contentScrimColor);

        int tabIndicatorColor = typedArray.getColor(R.styleable.TabLayoutCoordinator_tabIndicatorColor, Color.WHITE);
        mTabLayout.setSelectedTabIndicatorColor(tabIndicatorColor);

        int tabTextColor = typedArray.getColor(R.styleable.TabLayoutCoordinator_tabTextColor, Color.WHITE);
        mTabLayout.setTabTextColors(ColorStateList.valueOf(tabTextColor));
        typedArray.recycle();
    }

    private void initToolbar(View v) {
        mToolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) mContext).setSupportActionBar(mToolbar);
        mActionbar = ((AppCompatActivity) mContext).getSupportActionBar();
    }

    public TabLayoutCoordinator setTitle(String title) {
        if (mActionbar != null) {
            mActionbar.setTitle(title);
        }
        return this;
    }

    public TabLayoutCoordinator setBackEnable(Boolean canBack) {
        if (canBack && mActionbar != null) {
            mActionbar.setDisplayHomeAsUpEnabled(true);
            mActionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        }
        return this;
    }

    public TabLayoutCoordinator setImageArray(@NonNull int[] imageArray) {
        mImageArray = imageArray;
        return this;
    }

    public TabLayoutCoordinator setImageArray(@NonNull int[] imageArray, @NonNull int[] colorArray) {
        mImageArray = imageArray;
        mColorArray = colorArray;
        return this;
    }

    public TabLayoutCoordinator setContentScrimColorArray(@NonNull int[] colorArray) {
        mColorArray = colorArray;
        return this;
    }

    private void setupTabLayout() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mImageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_dismiss));
                if (mLoadHeaderImagesListener == null) {
                    if (mImageArray != null) {
                        mImageView.setImageResource(mImageArray[tab.getPosition()]);
                    }
                } else {
                    mLoadHeaderImagesListener.loadHeaderImages(mImageView, tab);
                }
                if (mColorArray != null) {
                    mCollapsingToolbarLayout.setContentScrimColor(
                            ContextCompat.getColor(
                                    mContext, mColorArray[tab.getPosition()]));
                }
                mImageView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_show));

                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabSelected(tab);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabUnselected(tab);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabReselected(tab);
                }
            }
        });
    }

    public TabLayoutCoordinator setTabMode(@TabLayout.Mode int mode) {
        mTabLayout.setTabMode(mode);
        return this;
    }

    public TabLayoutCoordinator setupWithViewPager(ViewPager viewPager) {
        setupTabLayout();
        mTabLayout.setupWithViewPager(viewPager);
        return this;
    }

    /**
     * 获取该组件中的ActionBar
     */
    public ActionBar getActionBar() {
        return mActionbar;
    }

    /**
     * 获取该组件中的TabLayout
     */
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * 获取该组件中的ImageView
     */
    public ImageView getImageView() {
        return mImageView;
    }

    /**
     * 设置LoadHeaderImagesListener
     *
     * @param loadHeaderImagesListener 设置LoadHeaderImagesListener
     * @return CoordinatorTabLayout
     */
    public TabLayoutCoordinator setLoadHeaderImagesListener(LoadHeaderImagesListener loadHeaderImagesListener) {
        mLoadHeaderImagesListener = loadHeaderImagesListener;
        return this;
    }

    /**
     * 设置onTabSelectedListener
     *
     * @param onTabSelectedListener 设置onTabSelectedListener
     * @return CoordinatorTabLayout
     */
    public TabLayoutCoordinator addOnTabSelectedListener(TabLayout.OnTabSelectedListener onTabSelectedListener) {
        mOnTabSelectedListener = onTabSelectedListener;
        return this;
    }

    /**
     * 设置透明状态栏
     *
     * @param activity 当前展示的activity
     * @return CoordinatorTabLayout
     */
    public TabLayoutCoordinator setTranslucentStatusBar(@NonNull Activity activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return this;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (mToolbar != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
            layoutParams.setMargins(
                    layoutParams.leftMargin,
                    layoutParams.topMargin + SystemView.getStatusBarHeight(activity),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
        }

        return this;
    }

    public TabLayoutCoordinator setTranslucentNavigationBar(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return this;
        } else {
            mToolbar.setPadding(0, SystemView.getStatusBarHeight(activity) >> 1, 0, 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return this;
    }

}

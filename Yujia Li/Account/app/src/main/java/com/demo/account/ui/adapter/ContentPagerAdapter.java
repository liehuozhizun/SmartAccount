package com.demo.account.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.demo.account.ui.fragment.CountFragment;
import com.demo.account.ui.fragment.HomeFragment;
import com.demo.account.ui.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class ContentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public ContentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.titles.add("Home");
        this.titles.add("Count");
        this.titles.add("My");
    }

    public void setContentFragments(){
        this.fragments.add(HomeFragment.getInstance());
        this.fragments.add(CountFragment.getInstance());
        this.fragments.add(MyFragment.getInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments == null ? 0 : this.fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.titles.get(position);
    }
}

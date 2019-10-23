package com.sdu.chy.chytest.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sdu.chy.chytest.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentTestActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    List<Fragment> fragmentList = new ArrayList<>();//存储多个Fragment实例的列表
    List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        initFragment();
        initView();

    }

    private void initView(){
        tabLayout = (TabLayout) findViewById(R.id.tl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        //创建PagerAdapter实例并关联到ViewPager
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        //将ViewPager关联到TabLayout中
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initFragment(){
        Test1Fragment test1Fragment = new Test1Fragment();
        Test2Fragment test2Fragment = new Test2Fragment();

        fragmentList.add(test1Fragment);
        fragmentList.add(test2Fragment);

        titles.add("Fragment1");
        titles.add("Fragment2");
    }

    @Override
    public void onFragmentInteraction(String data) {
        Toast.makeText(FragmentTestActivity.this,data,Toast.LENGTH_LONG).show();
    }
}

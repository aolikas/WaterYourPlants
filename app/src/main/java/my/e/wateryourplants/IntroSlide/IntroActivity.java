package my.e.wateryourplants.IntroSlide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import my.e.wateryourplants.Auth.StartActivity;
import my.e.wateryourplants.R;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnNext, btnSkip;
    private ViewPager mViewPager;
    private int[] mLayouts;
    private IntroPrefManager mPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefManager = new IntroPrefManager(this);
        if (!mPrefManager.isFirstTimeStart()) {
            launchStartPage();
            finish();
        }

        setContentView(R.layout.activity_intro);

        mViewPager = findViewById(R.id.intro_view_pager);

        btnNext = findViewById(R.id.intro_btn_next);
        btnNext.setOnClickListener(this);
        btnSkip = findViewById(R.id.intro_btn_skip);
        btnSkip.setOnClickListener(this);

        mLayouts = new int[]{
                R.layout.fragment_screen_one,
                R.layout.fragment_screen_two,
                R.layout.fragment_screen_three};

        mViewPager.setAdapter(new PagerAdapter() {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                LayoutInflater layoutInflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(mLayouts[position], container, false);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return mLayouts.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                View view = (View) object;
                container.removeView(view);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    btnNext.setText(R.string.intro_btn_start);
                    btnSkip.setVisibility(View.GONE);

                } else {
                    btnNext.setText(R.string.intro_btn_next);
                    btnSkip.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // launch startActivity
    private void launchStartPage() {
        mPrefManager.setFirstTimeStart(false);
        startActivity(new Intent(IntroActivity.this, StartActivity.class));
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.intro_btn_skip:
                // when user skip, starts Start Activity
                launchStartPage();
                break;
            case R.id.intro_btn_next:
                int current = mViewPager.getCurrentItem();
                if (current < mLayouts.length - 1) {
                    mViewPager.setCurrentItem(current + 1);
                } else {
                    launchStartPage();
                }
        }
    }
}
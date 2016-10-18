package com.feicuiedu.readgroup.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.feicuiedu.apphx.presentation.contact.list.HxContactListFragment;
import com.feicuiedu.apphx.presentation.conversation.HxConversationListFragment;
import com.feicuiedu.readgroup.R;
import com.feicuiedu.readgroup.presentation.books.BooksFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.text_books) TextView tvBooks;
    @BindView(R.id.text_contacts) TextView tvContacts;
    @BindView(R.id.text_conversations) TextView tvConversations;
    @BindView(R.id.text_me) TextView tvMe;

    @BindView(R.id.view_pager) ViewPager viewPager;

    private final FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new BooksFragment();
                case 1:
                    return new HxContactListFragment();
                case 2:
                    return new HxConversationListFragment();
                case 3:
                    return new BooksFragment();
                default:
                    throw new RuntimeException();
            }
        }

        @Override public int getCount() {
            return 4;
        }
    };

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        // 默认"书友"是选中状态
        tvBooks.setSelected(true);
    }

    @OnClick({R.id.text_books, R.id.text_contacts, R.id.text_conversations, R.id.text_me})
    public void chooseFragment(View view) {
        switch (view.getId()) {
            case R.id.text_books:
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.text_contacts:
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.text_conversations:
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.text_me:
                viewPager.setCurrentItem(3, false);
                break;
            default:
                throw new RuntimeException();
        }
    }

    // start-interface: OnPageChangeListener
    /* no-op */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override public void onPageSelected(int position) {
        tvBooks.setSelected(position == 0);
        tvContacts.setSelected(position == 1);
        tvConversations.setSelected(position == 2);
        tvMe.setSelected(position == 3);
    }

    /* no-op */
    @Override public void onPageScrollStateChanged(int state) {
    }
    // end-interface: OnPageChangeListener
}

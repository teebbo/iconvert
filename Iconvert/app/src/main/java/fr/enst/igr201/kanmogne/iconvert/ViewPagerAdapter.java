package fr.enst.igr201.kanmogne.iconvert;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fr.enst.igr201.kanmogne.iconvert.CurrenciesTabFragment;
import fr.enst.igr201.kanmogne.iconvert.HomeTabFragment;

/**
 * Created by joffrey on 24/10/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles; // store the title of the tab
    private int mTabsNumbers; // store the number of tabs

    public ViewPagerAdapter(FragmentManager fragmentManager,
                            String[] titles, int tabsNumbers){
        super(fragmentManager);
        this.mTitles = titles;
        this.mTabsNumbers = tabsNumbers;
    }

    /*
        Return the fragment, within the position "position"
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                 fragment = new HomeTabFragment();
                break;
            case 1:
                fragment = new CurrenciesTabFragment();
                break;
            case 2:
                fragment = new AboutFragment();
                break;
        }

        return fragment;
    }

    @Override
    public String getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return this.mTabsNumbers;
    }
}
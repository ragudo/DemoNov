

package com.ibm.mobileappbuilder.demonov20161104124234.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ibm.mobileappbuilder.demonov20161104124234.R;

import ibmmobileappbuilder.ui.BaseListingActivity;
/**
 * ExpenseTrackerActivity list activity
 */
public class ExpenseTrackerActivity extends BaseListingActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        if(isTaskRoot()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        setTitle(getString(R.string.expenseTrackerActivity));
    }

    @Override
    protected Class<? extends Fragment> getFragmentClass() {
        return ExpenseTrackerFragment.class;
    }

}

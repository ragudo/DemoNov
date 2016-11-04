
package com.ibm.mobileappbuilder.demonov20161104124234.ui;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDSItem;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDSService;
import com.ibm.mobileappbuilder.demonov20161104124234.presenters.ExpensesFormFormPresenter;
import com.ibm.mobileappbuilder.demonov20161104124234.R;
import ibmmobileappbuilder.ui.FormFragment;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.views.DatePicker;
import ibmmobileappbuilder.views.TextWatcherAdapter;
import java.util.Date;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDSItem;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDS;

public class ExpensesFormFragment extends FormFragment<ExpensesDSItem> {

    private CrudDatasource<ExpensesDSItem> datasource;

    public static ExpensesFormFragment newInstance(Bundle args){
        ExpensesFormFragment fr = new ExpensesFormFragment();
        fr.setArguments(args);

        return fr;
    }

    public ExpensesFormFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        // the presenter for this view
        setPresenter(new ExpensesFormFormPresenter(
                (CrudDatasource) getDatasource(),
                this));

            }

    @Override
    protected ExpensesDSItem newItem() {
        return new ExpensesDSItem();
    }

    private ExpensesDSService getRestService(){
        return ExpensesDSService.getInstance();
    }

    @Override
    protected int getLayout() {
        return R.layout.expensesform_form;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final ExpensesDSItem item, View view) {
        
        bindLong(R.id.expensesds_datafield0, item.dataField0, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.dataField0 = StringUtils.parseLong(s.toString());
            }
        });
        
        
        bindString(R.id.expensesds_title, item.title, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.title = s.toString();
            }
        });
        
        
        bindDouble(R.id.expensesds_amount, item.amount, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.amount = StringUtils.parseDouble(s.toString());
            }
        });
        
        
        bindDatePicker(R.id.expensesds_date, item.date, new DatePicker.DateSelectedListener() {
            @Override
            public void onSelected(Date selected) {
                item.date = selected;
            }
        });
        
        
        bindLong(R.id.expensesds_categoryid, item.categoryId, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.categoryId = StringUtils.parseLong(s.toString());
            }
        });
        
    }

    @Override
    public Datasource<ExpensesDSItem> getDatasource() {
      if (datasource != null) {
        return datasource;
      }
       datasource = ExpensesDS.getInstance(new SearchOptions());
        return datasource;
    }
}

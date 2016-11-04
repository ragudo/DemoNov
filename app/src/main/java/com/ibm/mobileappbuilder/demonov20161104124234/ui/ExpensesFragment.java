package com.ibm.mobileappbuilder.demonov20161104124234.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.CategoriesDSItem;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDSService;
import com.ibm.mobileappbuilder.demonov20161104124234.presenters.ExpensesPresenter;
import com.ibm.mobileappbuilder.demonov20161104124234.R;
import ibmmobileappbuilder.behaviors.FabBehaviour;
import ibmmobileappbuilder.behaviors.SelectionBehavior;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.filter.NumberFilter;
import ibmmobileappbuilder.ui.ListGridFragment;
import ibmmobileappbuilder.util.Constants;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.util.ViewHolder;
import java.util.List;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDSItem;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDS;
import ibmmobileappbuilder.mvp.view.CrudListView;
import ibmmobileappbuilder.ds.CrudDatasource;
import android.content.Intent;
import ibmmobileappbuilder.util.Constants;
import static ibmmobileappbuilder.util.NavigationUtils.generateIntentToAddOrUpdateItem;

/**
 * "ExpensesFragment" listing
 */
public class ExpensesFragment extends ListGridFragment<ExpensesDSItem> implements CrudListView<ExpensesDSItem> {

    private CrudDatasource<ExpensesDSItem> datasource;
    private CategoriesDSItem categoriesDSItem;

    // "Add" button
    private FabBehaviour fabBehavior;

    public static ExpensesFragment newInstance(Bundle args) {
        ExpensesFragment fr = new ExpensesFragment();

        fr.setArguments(args);
        return fr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        categoriesDSItem = getArguments() != null && getArguments().containsKey(CategoriesDSItem.class.getName()) ? 
        	(CategoriesDSItem)getArguments().getParcelable(CategoriesDSItem.class.getName()) : 
        	new CategoriesDSItem();
        setPresenter(new ExpensesPresenter(
            (CrudDatasource) getDatasource(),
            this
        ));
        // Multiple selection
        SelectionBehavior<ExpensesDSItem> selectionBehavior = new SelectionBehavior<>(
            this,
            R.string.remove_items,
            R.drawable.ic_delete_alpha);

        selectionBehavior.setCallback(new SelectionBehavior.Callback<ExpensesDSItem>() {
            @Override
            public void onSelected(List<ExpensesDSItem> selectedItems) {
                getPresenter().deleteItems(selectedItems);
            }
        });
        addBehavior(selectionBehavior);
        // FAB button
        fabBehavior = new FabBehaviour(this, R.drawable.ic_add_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().addForm();
            }
        });
        addBehavior(fabBehavior);
    }

    protected SearchOptions getSearchOptions() {
        SearchOptions.Builder searchOptionsBuilder = SearchOptions.Builder.searchOptions();
        searchOptionsBuilder
            .withFixedFilters(Arrays.<Filter>asList(new NumberFilter("categoryId", categoriesDSItem.dataField0)));
        return searchOptionsBuilder.build();
    }


    /**
    * Layout for the list itselft
    */
    @Override
    protected int getLayout() {
        return R.layout.fragment_list;
    }

    /**
    * Layout for each element in the list
    */
    @Override
    protected int getItemLayout() {
        return R.layout.expenses_item;
    }

    @Override
    protected Datasource<ExpensesDSItem> getDatasource() {
        if (datasource != null) {
            return datasource;
        }
        datasource = ExpensesDS.getInstance(getSearchOptions());
        return datasource;
    }

    @Override
    protected void bindView(ExpensesDSItem item, View view, int position) {
        
        TextView title = ViewHolder.get(view, R.id.title);
        
        if (item.amount != null){
            title.setText("$" + StringUtils.doubleToString(item.amount, true));
            
        }
        
        TextView subtitle = ViewHolder.get(view, R.id.subtitle);
        
        if (item.title != null && item.date != null){
            subtitle.setText(item.title + " on " + DateFormat.getMediumDateFormat(getActivity()).format(item.date));
            
        }
    }

    @Override
    protected void itemClicked(final ExpensesDSItem item, final int position) {
        fabBehavior.hide(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                getPresenter().detail(item, position);
            }
        });
    }

    @Override
    public void showDetail(ExpensesDSItem item, int position) {
        Bundle args = new Bundle();
        args.putInt(Constants.ITEMPOS, position);
        args.putParcelable(Constants.CONTENT, item);
        Intent intent = new Intent(getActivity(), ExpensesDetailActivity.class);
        intent.putExtras(args);

        if (!getResources().getBoolean(R.bool.tabletLayout)) {
            startActivityForResult(intent, Constants.DETAIL);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void showAdd() {
        startActivityForResult(generateIntentToAddOrUpdateItem(null,
                        0,
                        getActivity(),
                        ExpensesFormFormActivity.class
                ), Constants.MODE_CREATE
        );
    }

    @Override
    public void showEdit(ExpensesDSItem item, int position) {
        startActivityForResult(
                generateIntentToAddOrUpdateItem(item,
                        position,
                        getActivity(),
                        ExpensesFormFormActivity.class
                ), Constants.MODE_EDIT
        );
    }
}

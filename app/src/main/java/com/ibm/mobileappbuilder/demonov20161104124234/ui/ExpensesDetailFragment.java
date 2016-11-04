
package com.ibm.mobileappbuilder.demonov20161104124234.ui;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDSService;
import com.ibm.mobileappbuilder.demonov20161104124234.presenters.ExpensesDetailPresenter;
import com.ibm.mobileappbuilder.demonov20161104124234.R;
import ibmmobileappbuilder.behaviors.FabBehaviour;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.util.ColorUtils;
import ibmmobileappbuilder.util.Constants;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDSItem;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDS;

public class ExpensesDetailFragment extends ibmmobileappbuilder.ui.DetailFragment<ExpensesDSItem>  {

    private CrudDatasource<ExpensesDSItem> datasource;
    public static ExpensesDetailFragment newInstance(Bundle args){
        ExpensesDetailFragment fr = new ExpensesDetailFragment();
        fr.setArguments(args);

        return fr;
    }

    public ExpensesDetailFragment(){
        super();
    }

    @Override
    public Datasource<ExpensesDSItem> getDatasource() {
        if (datasource != null) {
            return datasource;
    }
       datasource = ExpensesDS.getInstance(new SearchOptions());
        return datasource;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        // the presenter for this view
        setPresenter(new ExpensesDetailPresenter(
                (CrudDatasource) getDatasource(),
                this));
        // Edit button
        addBehavior(new FabBehaviour(this, R.drawable.ic_edit_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DetailCrudPresenter<ExpensesDSItem>) getPresenter()).editForm(getItem());
            }
        }));

    }

    // Bindings

    @Override
    protected int getLayout() {
        return R.layout.expensesdetail_detail;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final ExpensesDSItem item, View view) {
        if (item.title != null){
            
            TextView view0 = (TextView) view.findViewById(R.id.view0);
            view0.setText(item.title);
            
        }
        
        TextView view1 = (TextView) view.findViewById(R.id.view1);
        view1.setText("-----------------------------------------------");
        
        if (item.amount != null){
            
            TextView view2 = (TextView) view.findViewById(R.id.view2);
            view2.setText(StringUtils.doubleToString(item.amount, true));
            
        }
        if (item.date != null){
            
            TextView view3 = (TextView) view.findViewById(R.id.view3);
            view3.setText(DateFormat.getMediumDateFormat(getActivity()).format(item.date));
            
        }
    }

    @Override
    protected void onShow(ExpensesDSItem item) {
        // set the title for this fragment
        getActivity().setTitle("Expense Detail");
    }

    @Override
    public void navigateToEditForm() {
        Bundle args = new Bundle();

        args.putInt(Constants.ITEMPOS, 0);
        args.putParcelable(Constants.CONTENT, getItem());
        args.putInt(Constants.MODE, Constants.MODE_EDIT);

        Intent intent = new Intent(getActivity(), ExpensesFormFormActivity.class);
        intent.putExtras(args);
        startActivityForResult(intent, Constants.MODE_EDIT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_menu, menu);

        MenuItem item = menu.findItem(R.id.action_delete);
        ColorUtils.tintIcon(item, R.color.textBarColor, getActivity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete){
            ((DetailCrudPresenter<ExpensesDSItem>) getPresenter()).deleteItem(getItem());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

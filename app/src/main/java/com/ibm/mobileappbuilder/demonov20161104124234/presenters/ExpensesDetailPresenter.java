
package com.ibm.mobileappbuilder.demonov20161104124234.presenters;

import com.ibm.mobileappbuilder.demonov20161104124234.R;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDSItem;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.mvp.view.DetailView;

public class ExpensesDetailPresenter extends BasePresenter implements DetailCrudPresenter<ExpensesDSItem>,
      Datasource.Listener<ExpensesDSItem> {

    private final CrudDatasource<ExpensesDSItem> datasource;
    private final DetailView view;

    public ExpensesDetailPresenter(CrudDatasource<ExpensesDSItem> datasource, DetailView view){
        this.datasource = datasource;
        this.view = view;
    }

    @Override
    public void deleteItem(ExpensesDSItem item) {
        datasource.deleteItem(item, this);
    }

    @Override
    public void editForm(ExpensesDSItem item) {
        view.navigateToEditForm();
    }

    @Override
    public void onSuccess(ExpensesDSItem item) {
                view.showMessage(R.string.item_deleted, true);
        view.close(true);
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic, true);
    }
}

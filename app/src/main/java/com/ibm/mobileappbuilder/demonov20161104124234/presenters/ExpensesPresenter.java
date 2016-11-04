
package com.ibm.mobileappbuilder.demonov20161104124234.presenters;

import com.ibm.mobileappbuilder.demonov20161104124234.R;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.ExpensesDSItem;

import java.util.List;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.ListCrudPresenter;
import ibmmobileappbuilder.mvp.view.CrudListView;

public class ExpensesPresenter extends BasePresenter implements ListCrudPresenter<ExpensesDSItem>,
      Datasource.Listener<ExpensesDSItem>{

    private final CrudDatasource<ExpensesDSItem> crudDatasource;
    private final CrudListView<ExpensesDSItem> view;

    public ExpensesPresenter(CrudDatasource<ExpensesDSItem> crudDatasource,
                                         CrudListView<ExpensesDSItem> view) {
       this.crudDatasource = crudDatasource;
       this.view = view;
    }

    @Override
    public void deleteItem(ExpensesDSItem item) {
        crudDatasource.deleteItem(item, this);
    }

    @Override
    public void deleteItems(List<ExpensesDSItem> items) {
        crudDatasource.deleteItems(items, this);
    }

    @Override
    public void addForm() {
        view.showAdd();
    }

    @Override
    public void editForm(ExpensesDSItem item, int position) {
        view.showEdit(item, position);
    }

    @Override
    public void detail(ExpensesDSItem item, int position) {
        view.showDetail(item, position);
    }

    @Override
    public void onSuccess(ExpensesDSItem item) {
                view.showMessage(R.string.items_deleted);
        view.refresh();
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic);
    }

}

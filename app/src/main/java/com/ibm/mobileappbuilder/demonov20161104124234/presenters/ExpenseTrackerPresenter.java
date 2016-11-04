
package com.ibm.mobileappbuilder.demonov20161104124234.presenters;

import com.ibm.mobileappbuilder.demonov20161104124234.R;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.CategoriesDSItem;

import java.util.List;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.ListCrudPresenter;
import ibmmobileappbuilder.mvp.view.CrudListView;

public class ExpenseTrackerPresenter extends BasePresenter implements ListCrudPresenter<CategoriesDSItem>,
      Datasource.Listener<CategoriesDSItem>{

    private final CrudDatasource<CategoriesDSItem> crudDatasource;
    private final CrudListView<CategoriesDSItem> view;

    public ExpenseTrackerPresenter(CrudDatasource<CategoriesDSItem> crudDatasource,
                                         CrudListView<CategoriesDSItem> view) {
       this.crudDatasource = crudDatasource;
       this.view = view;
    }

    @Override
    public void deleteItem(CategoriesDSItem item) {
        crudDatasource.deleteItem(item, this);
    }

    @Override
    public void deleteItems(List<CategoriesDSItem> items) {
        crudDatasource.deleteItems(items, this);
    }

    @Override
    public void addForm() {
        view.showAdd();
    }

    @Override
    public void editForm(CategoriesDSItem item, int position) {
        view.showEdit(item, position);
    }

    @Override
    public void detail(CategoriesDSItem item, int position) {
        view.showDetail(item, position);
    }

    @Override
    public void onSuccess(CategoriesDSItem item) {
                view.showMessage(R.string.items_deleted);
        view.refresh();
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic);
    }

}

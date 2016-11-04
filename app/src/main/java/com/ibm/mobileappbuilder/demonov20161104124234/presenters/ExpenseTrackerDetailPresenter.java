
package com.ibm.mobileappbuilder.demonov20161104124234.presenters;

import com.ibm.mobileappbuilder.demonov20161104124234.R;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.CategoriesDSItem;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.mvp.view.DetailView;

public class ExpenseTrackerDetailPresenter extends BasePresenter implements DetailCrudPresenter<CategoriesDSItem>,
      Datasource.Listener<CategoriesDSItem> {

    private final CrudDatasource<CategoriesDSItem> datasource;
    private final DetailView view;

    public ExpenseTrackerDetailPresenter(CrudDatasource<CategoriesDSItem> datasource, DetailView view){
        this.datasource = datasource;
        this.view = view;
    }

    @Override
    public void deleteItem(CategoriesDSItem item) {
        datasource.deleteItem(item, this);
    }

    @Override
    public void editForm(CategoriesDSItem item) {
        view.navigateToEditForm();
    }

    @Override
    public void onSuccess(CategoriesDSItem item) {
                view.showMessage(R.string.item_deleted, true);
        view.close(true);
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic, true);
    }
}

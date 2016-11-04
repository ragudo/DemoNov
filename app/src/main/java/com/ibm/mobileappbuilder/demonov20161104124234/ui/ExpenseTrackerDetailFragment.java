
package com.ibm.mobileappbuilder.demonov20161104124234.ui;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.CategoriesDSService;
import com.ibm.mobileappbuilder.demonov20161104124234.presenters.ExpenseTrackerDetailPresenter;
import com.ibm.mobileappbuilder.demonov20161104124234.R;
import ibmmobileappbuilder.actions.NavigateToAction;
import ibmmobileappbuilder.behaviors.FabBehaviour;
import ibmmobileappbuilder.ds.restds.AppNowDatasource;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.util.ColorUtils;
import ibmmobileappbuilder.util.Constants;
import ibmmobileappbuilder.util.image.ImageLoader;
import ibmmobileappbuilder.util.image.PicassoImageLoader;
import ibmmobileappbuilder.util.StringUtils;
import java.net.URL;
import static ibmmobileappbuilder.util.image.ImageLoaderRequest.Builder.imageLoaderRequest;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.CategoriesDSItem;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.CategoriesDS;

public class ExpenseTrackerDetailFragment extends ibmmobileappbuilder.ui.DetailFragment<CategoriesDSItem>  {

    private CrudDatasource<CategoriesDSItem> datasource;
    public static ExpenseTrackerDetailFragment newInstance(Bundle args){
        ExpenseTrackerDetailFragment fr = new ExpenseTrackerDetailFragment();
        fr.setArguments(args);

        return fr;
    }

    public ExpenseTrackerDetailFragment(){
        super();
    }

    @Override
    public Datasource<CategoriesDSItem> getDatasource() {
        if (datasource != null) {
            return datasource;
    }
       datasource = CategoriesDS.getInstance(new SearchOptions());
        return datasource;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        // the presenter for this view
        setPresenter(new ExpenseTrackerDetailPresenter(
                (CrudDatasource) getDatasource(),
                this));
        // Edit button
        addBehavior(new FabBehaviour(this, R.drawable.ic_edit_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DetailCrudPresenter<CategoriesDSItem>) getPresenter()).editForm(getItem());
            }
        }));

    }

    // Bindings

    @Override
    protected int getLayout() {
        return R.layout.expensetrackerdetail_detail;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final CategoriesDSItem item, View view) {
        
        ImageView view0 = (ImageView) view.findViewById(R.id.view0);
        URL view0Media = ((AppNowDatasource) getDatasource()).getImageUrl(item.icon);
        if(view0Media != null){
          ImageLoader imageLoader = new PicassoImageLoader(view0.getContext());
          imageLoader.load(imageLoaderRequest()
                                   .withPath(view0Media.toExternalForm())
                                   .withTargetView(view0)
                                   .fit()
                                   .build()
                    );
        	
        } else {
          view0.setImageDrawable(null);
        }
        if (item.dataField1 != null){
            
            TextView view1 = (TextView) view.findViewById(R.id.view1);
            view1.setText(item.dataField1);
            
        }
        if (item.title != null){
            
            TextView view2 = (TextView) view.findViewById(R.id.view2);
            view2.setText(item.title + " expenses");
            bindAction(view2, new NavigateToAction(CategoriesDSItem.class.getName(), ExpensesActivity.class, getItem()));
        }
    }

    @Override
    protected void onShow(CategoriesDSItem item) {
        // set the title for this fragment
        getActivity().setTitle(item.title);
    }

    @Override
    public void navigateToEditForm() {
        Bundle args = new Bundle();

        args.putInt(Constants.ITEMPOS, 0);
        args.putParcelable(Constants.CONTENT, getItem());
        args.putInt(Constants.MODE, Constants.MODE_EDIT);

        Intent intent = new Intent(getActivity(), ExpenseTrackerFormFormActivity.class);
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
            ((DetailCrudPresenter<CategoriesDSItem>) getPresenter()).deleteItem(getItem());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

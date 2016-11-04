
package com.ibm.mobileappbuilder.demonov20161104124234.ui;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.CategoriesDSItem;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.CategoriesDSService;
import com.ibm.mobileappbuilder.demonov20161104124234.presenters.ExpenseTrackerFormFormPresenter;
import com.ibm.mobileappbuilder.demonov20161104124234.R;
import ibmmobileappbuilder.ui.FormFragment;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.views.ImagePicker;
import ibmmobileappbuilder.views.TextWatcherAdapter;
import java.io.IOException;
import java.io.File;

import static android.net.Uri.fromFile;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.CategoriesDSItem;
import com.ibm.mobileappbuilder.demonov20161104124234.ds.CategoriesDS;

public class ExpenseTrackerFormFragment extends FormFragment<CategoriesDSItem> {

    private CrudDatasource<CategoriesDSItem> datasource;

    public static ExpenseTrackerFormFragment newInstance(Bundle args){
        ExpenseTrackerFormFragment fr = new ExpenseTrackerFormFragment();
        fr.setArguments(args);

        return fr;
    }

    public ExpenseTrackerFormFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        // the presenter for this view
        setPresenter(new ExpenseTrackerFormFormPresenter(
                (CrudDatasource) getDatasource(),
                this));

            }

    @Override
    protected CategoriesDSItem newItem() {
        return new CategoriesDSItem();
    }

    private CategoriesDSService getRestService(){
        return CategoriesDSService.getInstance();
    }

    @Override
    protected int getLayout() {
        return R.layout.expensetrackerform_form;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final CategoriesDSItem item, View view) {
        
        bindLong(R.id.categoriesds_datafield0, item.dataField0, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.dataField0 = StringUtils.parseLong(s.toString());
            }
        });
        
        
        bindString(R.id.categoriesds_title, item.title, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.title = s.toString();
            }
        });
        
        
        bindString(R.id.categoriesds_datafield1, item.dataField1, new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                item.dataField1 = s.toString();
            }
        });
        
        
        bindImage(R.id.categoriesds_icon,
            item.icon != null ?
                getRestService().getImageUrl(item.icon) : null,
            0,
            new ImagePicker.Callback(){
                @Override
                public void imageRemoved(){
                    item.icon = null;
                    item.iconUri = null;
                    ((ImagePicker) getView().findViewById(R.id.categoriesds_icon)).clear();
                }
            }
        );
        
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            ImagePicker picker = null;
            Uri imageUri = null;
            CategoriesDSItem item = getItem();

            if((requestCode & ImagePicker.GALLERY_REQUEST_CODE) == ImagePicker.GALLERY_REQUEST_CODE) {
                imageUri = data.getData();
                switch (requestCode - ImagePicker.GALLERY_REQUEST_CODE) {
                        
                        case 0:   // icon field
                            item.iconUri = imageUri;
                            item.icon = "cid:icon";
                            picker = (ImagePicker) getView().findViewById(R.id.categoriesds_icon);
                            break;
                        
                    default:
                        return;
                }

                picker.setImageUri(imageUri);
            } else if((requestCode & ImagePicker.CAPTURE_REQUEST_CODE) == ImagePicker.CAPTURE_REQUEST_CODE) {
                switch (requestCode - ImagePicker.CAPTURE_REQUEST_CODE) {
                        
                        case 0:   // icon field
                            picker = (ImagePicker) getView().findViewById(R.id.categoriesds_icon);
                            imageUri = fromFile(picker.getImageFile());
                        		item.iconUri = imageUri;
                            item.icon = "cid:icon";
                            break;
                        
                    default:
                        return;
                }
                picker.setImageUri(imageUri);
            }
        }
    }
}



package com.ibm.mobileappbuilder.demonov20161104124234.ds;

import android.content.Context;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.restds.AppNowDatasource;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.ds.restds.TypedByteArrayUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * "CategoriesDS" data source. (e37eb8dc-6eb2-4635-8592-5eb9696050e3)
 */
public class CategoriesDS extends AppNowDatasource<CategoriesDSItem>{

    // default page size
    private static final int PAGE_SIZE = 20;

    private CategoriesDSService service;

    public static CategoriesDS getInstance(SearchOptions searchOptions){
        return new CategoriesDS(searchOptions);
    }

    private CategoriesDS(SearchOptions searchOptions) {
        super(searchOptions);
        this.service = CategoriesDSService.getInstance();
    }

    @Override
    public void getItem(String id, final Listener<CategoriesDSItem> listener) {
        if ("0".equals(id)) {
                        getItems(new Listener<List<CategoriesDSItem>>() {
                @Override
                public void onSuccess(List<CategoriesDSItem> items) {
                    if(items != null && items.size() > 0) {
                        listener.onSuccess(items.get(0));
                    } else {
                        listener.onSuccess(new CategoriesDSItem());
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure(e);
                }
            });
        } else {
                      service.getServiceProxy().getCategoriesDSItemById(id, new Callback<CategoriesDSItem>() {
                @Override
                public void success(CategoriesDSItem result, Response response) {
                                        listener.onSuccess(result);
                }

                @Override
                public void failure(RetrofitError error) {
                                        listener.onFailure(error);
                }
            });
        }
    }

    @Override
    public void getItems(final Listener<List<CategoriesDSItem>> listener) {
        getItems(0, listener);
    }

    @Override
    public void getItems(int pagenum, final Listener<List<CategoriesDSItem>> listener) {
        String conditions = getConditions(searchOptions, getSearchableFields());
        int skipNum = pagenum * PAGE_SIZE;
        String skip = skipNum == 0 ? null : String.valueOf(skipNum);
        String limit = PAGE_SIZE == 0 ? null: String.valueOf(PAGE_SIZE);
        String sort = getSort(searchOptions);
                service.getServiceProxy().queryCategoriesDSItem(
                skip,
                limit,
                conditions,
                sort,
                null,
                null,
                new Callback<List<CategoriesDSItem>>() {
            @Override
            public void success(List<CategoriesDSItem> result, Response response) {
                                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    private String[] getSearchableFields() {
        return new String[]{"title", "dataField1"};
    }

    // Pagination

    @Override
    public int getPageSize(){
        return PAGE_SIZE;
    }

    @Override
    public void getUniqueValuesFor(String searchStr, final Listener<List<String>> listener) {
        String conditions = getConditions(searchOptions, getSearchableFields());
                service.getServiceProxy().distinct(searchStr, conditions, new Callback<List<String>>() {
             @Override
             public void success(List<String> result, Response response) {
                                  result.removeAll(Collections.<String>singleton(null));
                 listener.onSuccess(result);
             }

             @Override
             public void failure(RetrofitError error) {
                                  listener.onFailure(error);
             }
        });
    }

    @Override
    public URL getImageUrl(String path) {
        return service.getImageUrl(path);
    }

    @Override
    public void create(CategoriesDSItem item, Listener<CategoriesDSItem> listener) {
                    
        if(item.iconUri != null){
            service.getServiceProxy().createCategoriesDSItem(item,
                TypedByteArrayUtils.fromUri(item.iconUri),
                callbackFor(listener));
        }
        else
            service.getServiceProxy().createCategoriesDSItem(item, callbackFor(listener));
        
    }

    private Callback<CategoriesDSItem> callbackFor(final Listener<CategoriesDSItem> listener) {
      return new Callback<CategoriesDSItem>() {
          @Override
          public void success(CategoriesDSItem item, Response response) {
                            listener.onSuccess(item);
          }

          @Override
          public void failure(RetrofitError error) {
                            listener.onFailure(error);
          }
      };
    }

    @Override
    public void updateItem(CategoriesDSItem item, Listener<CategoriesDSItem> listener) {
                    
        if(item.iconUri != null){
            service.getServiceProxy().updateCategoriesDSItem(item.getIdentifiableId(),
                item,
                TypedByteArrayUtils.fromUri(item.iconUri),
                callbackFor(listener));
        }
        else
            service.getServiceProxy().updateCategoriesDSItem(item.getIdentifiableId(), item, callbackFor(listener));
        
    }

    @Override
    public void deleteItem(CategoriesDSItem item, final Listener<CategoriesDSItem> listener) {
                service.getServiceProxy().deleteCategoriesDSItemById(item.getIdentifiableId(), new Callback<CategoriesDSItem>() {
            @Override
            public void success(CategoriesDSItem result, Response response) {
                                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    @Override
    public void deleteItems(List<CategoriesDSItem> items, final Listener<CategoriesDSItem> listener) {
                service.getServiceProxy().deleteByIds(collectIds(items), new Callback<List<CategoriesDSItem>>() {
            @Override
            public void success(List<CategoriesDSItem> item, Response response) {
                                listener.onSuccess(null);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    protected List<String> collectIds(List<CategoriesDSItem> items){
        List<String> ids = new ArrayList<>();
        for(CategoriesDSItem item: items){
            ids.add(item.getIdentifiableId());
        }
        return ids;
    }

}

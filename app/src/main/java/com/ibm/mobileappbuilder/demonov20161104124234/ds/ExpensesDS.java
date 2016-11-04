

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
 * "ExpensesDS" data source. (e37eb8dc-6eb2-4635-8592-5eb9696050e3)
 */
public class ExpensesDS extends AppNowDatasource<ExpensesDSItem>{

    // default page size
    private static final int PAGE_SIZE = 20;

    private ExpensesDSService service;

    public static ExpensesDS getInstance(SearchOptions searchOptions){
        return new ExpensesDS(searchOptions);
    }

    private ExpensesDS(SearchOptions searchOptions) {
        super(searchOptions);
        this.service = ExpensesDSService.getInstance();
    }

    @Override
    public void getItem(String id, final Listener<ExpensesDSItem> listener) {
        if ("0".equals(id)) {
                        getItems(new Listener<List<ExpensesDSItem>>() {
                @Override
                public void onSuccess(List<ExpensesDSItem> items) {
                    if(items != null && items.size() > 0) {
                        listener.onSuccess(items.get(0));
                    } else {
                        listener.onSuccess(new ExpensesDSItem());
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure(e);
                }
            });
        } else {
                      service.getServiceProxy().getExpensesDSItemById(id, new Callback<ExpensesDSItem>() {
                @Override
                public void success(ExpensesDSItem result, Response response) {
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
    public void getItems(final Listener<List<ExpensesDSItem>> listener) {
        getItems(0, listener);
    }

    @Override
    public void getItems(int pagenum, final Listener<List<ExpensesDSItem>> listener) {
        String conditions = getConditions(searchOptions, getSearchableFields());
        int skipNum = pagenum * PAGE_SIZE;
        String skip = skipNum == 0 ? null : String.valueOf(skipNum);
        String limit = PAGE_SIZE == 0 ? null: String.valueOf(PAGE_SIZE);
        String sort = getSort(searchOptions);
                service.getServiceProxy().queryExpensesDSItem(
                skip,
                limit,
                conditions,
                sort,
                null,
                null,
                new Callback<List<ExpensesDSItem>>() {
            @Override
            public void success(List<ExpensesDSItem> result, Response response) {
                                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    private String[] getSearchableFields() {
        return new String[]{"title"};
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
    public void create(ExpensesDSItem item, Listener<ExpensesDSItem> listener) {
                          service.getServiceProxy().createExpensesDSItem(item, callbackFor(listener));
          }

    private Callback<ExpensesDSItem> callbackFor(final Listener<ExpensesDSItem> listener) {
      return new Callback<ExpensesDSItem>() {
          @Override
          public void success(ExpensesDSItem item, Response response) {
                            listener.onSuccess(item);
          }

          @Override
          public void failure(RetrofitError error) {
                            listener.onFailure(error);
          }
      };
    }

    @Override
    public void updateItem(ExpensesDSItem item, Listener<ExpensesDSItem> listener) {
                          service.getServiceProxy().updateExpensesDSItem(item.getIdentifiableId(), item, callbackFor(listener));
          }

    @Override
    public void deleteItem(ExpensesDSItem item, final Listener<ExpensesDSItem> listener) {
                service.getServiceProxy().deleteExpensesDSItemById(item.getIdentifiableId(), new Callback<ExpensesDSItem>() {
            @Override
            public void success(ExpensesDSItem result, Response response) {
                                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    @Override
    public void deleteItems(List<ExpensesDSItem> items, final Listener<ExpensesDSItem> listener) {
                service.getServiceProxy().deleteByIds(collectIds(items), new Callback<List<ExpensesDSItem>>() {
            @Override
            public void success(List<ExpensesDSItem> item, Response response) {
                                listener.onSuccess(null);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    protected List<String> collectIds(List<ExpensesDSItem> items){
        List<String> ids = new ArrayList<>();
        for(ExpensesDSItem item: items){
            ids.add(item.getIdentifiableId());
        }
        return ids;
    }

}

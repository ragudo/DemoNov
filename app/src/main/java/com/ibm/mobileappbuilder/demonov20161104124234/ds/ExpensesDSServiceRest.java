
package com.ibm.mobileappbuilder.demonov20161104124234.ds;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.POST;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Path;
import retrofit.http.PUT;

public interface ExpensesDSServiceRest{

	@GET("/app/581c83b831e0f80300533569/r/expensesDS")
	void queryExpensesDSItem(
		@Query("skip") String skip,
		@Query("limit") String limit,
		@Query("conditions") String conditions,
		@Query("sort") String sort,
		@Query("select") String select,
		@Query("populate") String populate,
		Callback<List<ExpensesDSItem>> cb);

	@GET("/app/581c83b831e0f80300533569/r/expensesDS/{id}")
	void getExpensesDSItemById(@Path("id") String id, Callback<ExpensesDSItem> cb);

	@DELETE("/app/581c83b831e0f80300533569/r/expensesDS/{id}")
  void deleteExpensesDSItemById(@Path("id") String id, Callback<ExpensesDSItem> cb);

  @POST("/app/581c83b831e0f80300533569/r/expensesDS/deleteByIds")
  void deleteByIds(@Body List<String> ids, Callback<List<ExpensesDSItem>> cb);

  @POST("/app/581c83b831e0f80300533569/r/expensesDS")
  void createExpensesDSItem(@Body ExpensesDSItem item, Callback<ExpensesDSItem> cb);

  @PUT("/app/581c83b831e0f80300533569/r/expensesDS/{id}")
  void updateExpensesDSItem(@Path("id") String id, @Body ExpensesDSItem item, Callback<ExpensesDSItem> cb);

  @GET("/app/581c83b831e0f80300533569/r/expensesDS")
  void distinct(
        @Query("distinct") String colName,
        @Query("conditions") String conditions,
        Callback<List<String>> cb);
}

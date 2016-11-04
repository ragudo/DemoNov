
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
import retrofit.mime.TypedByteArray;
import retrofit.http.Part;
import retrofit.http.Multipart;

public interface CategoriesDSServiceRest{

	@GET("/app/581c83b831e0f80300533569/r/categoriesDS")
	void queryCategoriesDSItem(
		@Query("skip") String skip,
		@Query("limit") String limit,
		@Query("conditions") String conditions,
		@Query("sort") String sort,
		@Query("select") String select,
		@Query("populate") String populate,
		Callback<List<CategoriesDSItem>> cb);

	@GET("/app/581c83b831e0f80300533569/r/categoriesDS/{id}")
	void getCategoriesDSItemById(@Path("id") String id, Callback<CategoriesDSItem> cb);

	@DELETE("/app/581c83b831e0f80300533569/r/categoriesDS/{id}")
  void deleteCategoriesDSItemById(@Path("id") String id, Callback<CategoriesDSItem> cb);

  @POST("/app/581c83b831e0f80300533569/r/categoriesDS/deleteByIds")
  void deleteByIds(@Body List<String> ids, Callback<List<CategoriesDSItem>> cb);

  @POST("/app/581c83b831e0f80300533569/r/categoriesDS")
  void createCategoriesDSItem(@Body CategoriesDSItem item, Callback<CategoriesDSItem> cb);

  @PUT("/app/581c83b831e0f80300533569/r/categoriesDS/{id}")
  void updateCategoriesDSItem(@Path("id") String id, @Body CategoriesDSItem item, Callback<CategoriesDSItem> cb);

  @GET("/app/581c83b831e0f80300533569/r/categoriesDS")
  void distinct(
        @Query("distinct") String colName,
        @Query("conditions") String conditions,
        Callback<List<String>> cb);
    
    @Multipart
    @POST("/app/581c83b831e0f80300533569/r/categoriesDS")
    void createCategoriesDSItem(
        @Part("data") CategoriesDSItem item,
        @Part("icon") TypedByteArray icon,
        Callback<CategoriesDSItem> cb);
    
    @Multipart
    @PUT("/app/581c83b831e0f80300533569/r/categoriesDS/{id}")
    void updateCategoriesDSItem(
        @Path("id") String id,
        @Part("data") CategoriesDSItem item,
        @Part("icon") TypedByteArray icon,
        Callback<CategoriesDSItem> cb);
}

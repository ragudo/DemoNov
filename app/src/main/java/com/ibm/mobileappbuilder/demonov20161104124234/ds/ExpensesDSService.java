
package com.ibm.mobileappbuilder.demonov20161104124234.ds;
import java.net.URL;
import com.ibm.mobileappbuilder.demonov20161104124234.R;
import ibmmobileappbuilder.ds.RestService;
import ibmmobileappbuilder.util.StringUtils;

/**
 * "ExpensesDSService" REST Service implementation
 */
public class ExpensesDSService extends RestService<ExpensesDSServiceRest>{

    public static ExpensesDSService getInstance(){
          return new ExpensesDSService();
    }

    private ExpensesDSService() {
        super(ExpensesDSServiceRest.class);

    }

    @Override
    public String getServerUrl() {
        return "https://baked-devel-ibm.herokuapp.com";
    }

    @Override
    protected String getApiKey() {
        return "NJQBYeCo";
    }

    @Override
    public URL getImageUrl(String path){
        return StringUtils.parseUrl("https://baked-devel-ibm.herokuapp.com/app/581c83b831e0f80300533569",
                path,
                "apikey=NJQBYeCo");
    }

}

package io.github.v2compose.network.bean;

/**
 * Created by ghui on 23/07/2017.
 */

public class IgnoreResultInfo extends BaseInfo {
    @Override
    public boolean isValid() {
        // TODO: 24/07/2017  
        return rawResponse != null && rawResponse.length() == 0;
    }
}

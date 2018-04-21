package com.happytogether.framework.type;

import java.util.HashMap;
import java.util.Map;

public abstract class FeatureObj {

    private Map<String, Object> _features;

    public FeatureObj(){
        _features = new HashMap<String, Object>();
    }

    public void setFeature(String featureName, Object feature){
        _features.put(featureName, feature);
    }

    public Object getFeature(String featureName){
        return _features.get(featureName);
    }
}

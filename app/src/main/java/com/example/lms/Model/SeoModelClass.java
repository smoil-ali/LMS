package com.example.lms.Model;

import java.util.ArrayList;
import java.util.List;

public class SeoModelClass {
    List<String> meta_list = new ArrayList<>();
    String metaDiscription = "";

    public SeoModelClass() {
    }

    public List<String> getMeta_list() {
        return meta_list;
    }

    public void setMeta_list(List<String> meta_list) {
        this.meta_list = meta_list;
    }

    public String getMetaDiscription() {
        return metaDiscription;
    }

    public void setMetaDiscription(String metaDiscription) {
        this.metaDiscription = metaDiscription;
    }
}

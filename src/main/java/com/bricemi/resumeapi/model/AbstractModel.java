package com.bricemi.resumeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AbstractModel {

    protected final String type;

    public AbstractModel(){
        String[] classNameArray = getClass().getName().split("\\.");
        this.type = classNameArray[classNameArray.length-1].toUpperCase();
    }

    public String generateIdRef(String id){
        return type.concat("::").concat(id.replaceAll(" ","_").toLowerCase());
    }
}

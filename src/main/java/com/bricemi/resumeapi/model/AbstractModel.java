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
        return generateIdRef(getClass(),id);
    }

    public static String generateIdRef(Class type, String id){
        String[] classNameArray = type.getName().split("\\.");
        return classNameArray[classNameArray.length-1]
            .toUpperCase()
            .concat("::")
            .concat(
                id.replaceAll(" ","_")
                    .toLowerCase()
            );
    }
}

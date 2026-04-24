package com.pao.project.cabinet_medical.model;

public class CodProgramare {
    private final String val;

    public CodProgramare(String val){
        if (val == null || val.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.val = val;
    }
    public String getVal(){ return val;}

    @Override
    public String toString(){ return val;}

    @Override
    public boolean equals(Object o){
        if (this == o){ return true;}
        if (!(o instanceof CodProgramare)) {return false;}
        CodProgramare altcv = (CodProgramare) o;
        return val.equals(altcv.val);
    }

    @Override
    public int hashCode(){ return val.hashCode();}
}

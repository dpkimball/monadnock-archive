package com.oxbow.monadnock.service.domain;


public class USDAFood {

    private long id;

    private long db_offset;

    private long ndbno;

    private String name;

    private int barcodetype;

    private String barcodevalue;

    private String sd;

    private String food_group;

    private String sn;

    private String cn;

    private String manu;

    private String nf;

    private String cf;

    private String ff;

    private String pf;

    private String r;

    private String rd;

    private String ds;

    private String ing;

    private String upd;

    public USDAFood() {
    }

    public long getUsda_food_id() {
        return id;
    }

    public void setUsda_food_id(long usda_food_id) {
        this.id = usda_food_id;
    }

    public long getDb_offset() {
        return db_offset;
    }

    public void setDb_offset(long db_offset) {
        this.db_offset = db_offset;
    }

    public long getNdbno() {
        return ndbno;
    }

    public void setNdbno(long ndbno) {
        this.ndbno = ndbno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBarcodetype() {
        return barcodetype;
    }

    public void setBarcodetype(int barcodetype) {
        this.barcodetype = barcodetype;
    }

    public String getBarcodevalue() {
        return barcodevalue;
    }

    public void setBarcodevalue(String barcodevalue) {
        this.barcodevalue = barcodevalue;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getFood_group() {
        return food_group;
    }

    public void setFood_group(String food_group) {
        this.food_group = food_group;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getManu() {
        return manu;
    }

    public void setManu(String manu) {
        this.manu = manu;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getFf() {
        return ff;
    }

    public void setFf(String ff) {
        this.ff = ff;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getIng() {
        return ing;
    }

    public void setIng(String ing) {
        this.ing = ing;
    }

    public String getUpd() {
        return upd;
    }

    public void setUpd(String upd) {
        this.upd = upd;
    }

    @Override
    public String toString() {
        return "USDAFood {" +
                "id=" + id +
                ", name=" + name +
                ", ndbno=" + ndbno +
                ", barcode=" + barcodevalue +
                '}';
    }
}

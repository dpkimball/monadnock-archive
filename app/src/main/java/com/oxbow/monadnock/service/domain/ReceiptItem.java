package com.oxbow.monadnock.service.domain;


import java.math.BigDecimal;
import java.util.Date;

public class ReceiptItem {

    private long id;

    private ReceiptItemCategory category;

    private Receipt receipt;

    public ReceiptItemCategory getCategory() {
        return this.category;
    }

    private boolean sameAsFormer(ReceiptItemCategory newCategory) {
        return category == null ? newCategory == null : category.equals(newCategory);
    }
    private boolean sameAsFormer(Receipt newReceipt) {
        return receipt == null ? newReceipt == null : receipt.equals(newReceipt);
    }

    public void setCategory(ReceiptItemCategory category) {
        //prevent endless loop
        if (sameAsFormer(category))
            return;
        //set new owner
        ReceiptItemCategory oldCategory = this.category;
        this.category = category;
        //remove from the old owner
        if (oldCategory != null)
            oldCategory.removeReceiptItem(this);
        //set myself to new owner
        if (category != null)
            category.addReceiptItem(this);
    }

    /*
    public void setReceipt(Receipt receipt) {
        //prevent endless loop
        if (sameAsFormer(receipt))
            return;
        //set new owner
        Receipt oldReceipt = this.receipt;
        this.receipt = receipt;
        //remove from the old owner
        if (oldReceipt != null)
            oldReceipt.removeReceiptItem(this);
        //set myself to new owner
        if (receipt != null)
            receipt.addReceiptItem(this);
    }
    */

    private String label;

    private String description;

    private BigDecimal price;

    private Boolean taxable;

    private BigDecimal deposit;

    private Date expiration;

    private int barcodetype;

    private String barcodevalue;

    private USDAFood usda_food;

    public ReceiptItem() {
    }

    public ReceiptItem(ReceiptItemCategory category) {
        //this.category = category;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public USDAFood getUsda_food() {
        return usda_food;
    }

    public void setUsda_food(USDAFood usda_food) {
        this.usda_food = usda_food;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Receipt_Item {" +
                "id=" + id +
                //", category=" + category +
                ", label=" + label +
                ", price=" + price +
                ", usda_food='" + usda_food + '\'' +
                '}';
    }
}

package com.oxbow.monadnock.service.domain;


import com.oxbow.monadnock.service.domain.ReceiptItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ReceiptItemCategory {

    private long id;

    private String category;

    public ReceiptItemCategory(long id, String category) {
        this.id = id;
        this.category = category;
    }
    public ReceiptItemCategory(long id){
        this.id = id;
    }
    public ReceiptItemCategory() {

    }

    public ReceiptItemCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private Set<ReceiptItem> receiptItems = new HashSet<>(0);

    /**
     * Returns a collection with owned receipt items. The
     * returned collection is a defensive copy.
     *
     * @return a collection with owned twitter accounts
     */
    public Collection<ReceiptItem> getReceiptItems() {
        //defensive copy, nobody will be able to change
        //the list from the outside
        return new ArrayList<>(receiptItems);
    }

    /**
     * Add new receipt item to the category. The method keeps
     * relationships consistency:
     * * this category is set as the receipt_item category
     */
    public void addReceiptItem(ReceiptItem receiptItem) {
        //prevent endless loop
        if (receiptItems.contains(receiptItem))
            return;
        //add new receipt item
        receiptItems.add(receiptItem);
        //set myself into the receipt item
        receiptItem.setCategory(this);
    }

    /**
     * Removes the category from the receipt_item. The method keeps
     * relationships consistency:
     * * the category will no longer reference this receipt_item as its category
     */
    public void removeReceiptItem(ReceiptItem receiptItem) {
        //prevent endless loop
        if (!receiptItems.contains(receiptItem))
            return;
        //remove the category
        receiptItems.remove(receiptItem);
        //remove myself from the receipt item
        receiptItem.setCategory(null);
    }

    @Override
    public String toString() {
        return "Receipt Item Category {" +
                "id=" + id +
                ", category=" + category +
                '}';
    }
}

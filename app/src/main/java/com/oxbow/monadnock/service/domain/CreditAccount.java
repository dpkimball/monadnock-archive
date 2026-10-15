package com.oxbow.monadnock.service.domain;


public class CreditAccount {

    private long id;

    private long user_id;

    private String account;

    public CreditAccount() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "CreditAccount {" +
                "id=" + id +
                ", account=" + account +
                '}';
    }
}

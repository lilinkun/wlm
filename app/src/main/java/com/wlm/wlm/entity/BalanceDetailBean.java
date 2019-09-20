package com.wlm.wlm.entity;

/**
 * Created by LG on 2019/9/20.
 */
public class BalanceDetailBean {
    private int ExpenditureId;
    private int UserId;
    private String UserName;
    private int ExpenditureMoney;//此次交易额
    private int ReceiptsOrOut;//支出为-1，收入为1
    private int Balance;//剩余额度
    private String TradeType;
    private String StatusName;
    private int Status;
    private String TradeSay;
    private String CretateTime;
    private String AdminName;
    private String OrderId;
    private String SurName;
    private String NickName;

    public int getExpenditureId() {
        return ExpenditureId;
    }

    public void setExpenditureId(int expenditureId) {
        ExpenditureId = expenditureId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getExpenditureMoney() {
        return ExpenditureMoney;
    }

    public void setExpenditureMoney(int expenditureMoney) {
        ExpenditureMoney = expenditureMoney;
    }

    public int getReceiptsOrOut() {
        return ReceiptsOrOut;
    }

    public void setReceiptsOrOut(int receiptsOrOut) {
        ReceiptsOrOut = receiptsOrOut;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    public String getTradeType() {
        return TradeType;
    }

    public void setTradeType(String tradeType) {
        TradeType = tradeType;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getTradeSay() {
        return TradeSay;
    }

    public void setTradeSay(String tradeSay) {
        TradeSay = tradeSay;
    }

    public String getCretateTime() {
        return CretateTime;
    }

    public void setCretateTime(String cretateTime) {
        CretateTime = cretateTime;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }
}

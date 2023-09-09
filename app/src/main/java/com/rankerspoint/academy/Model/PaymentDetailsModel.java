package com.rankerspoint.academy.Model;

public class PaymentDetailsModel {


    public String getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(String paymentId) {
        PaymentId = paymentId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        PurchaseType = purchaseType;
    }

    public String getPurchasedServiceId() {
        return PurchasedServiceId;
    }

    public void setPurchasedServiceId(String purchasedServiceId) {
        PurchasedServiceId = purchasedServiceId;
    }

    public String getPurchasedServiceName() {
        return PurchasedServiceName;
    }

    public void setPurchasedServiceName(String purchasedServiceName) {
        PurchasedServiceName = purchasedServiceName;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public PaymentDetailsModel(String paymentId, String userId, String purchaseType, String purchasedServiceId, String purchasedServiceName, String amount, String transDate, String status) {
        PaymentId = paymentId;
        UserId = userId;
        PurchaseType = purchaseType;
        PurchasedServiceId = purchasedServiceId;
        PurchasedServiceName = purchasedServiceName;
        Amount = amount;
        TransDate = transDate;
        Status = status;
    }

    private String PaymentId;
    private String UserId;
    private String PurchaseType;
    private String PurchasedServiceId;
    private String PurchasedServiceName;
    private String Amount;
    private String TransDate;
    private String Status;


}

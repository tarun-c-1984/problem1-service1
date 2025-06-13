package org.example;

public class HoldingEvent {
    private int holdingId;
    private String assetId;
    private String accountId;
    private String placeOfSafekeeping;
    private String holdingType;
    private int settQnty;
    private int pendQnty;
    private int pendRcvQntty;
    private int pendDlvrQntty;
    private String date;

    // Getters and setters
    public int getHoldingId() { return holdingId; }
    public void setHoldingId(int holdingId) { this.holdingId = holdingId; }
    public String getAssetId() { return assetId; }
    public void setAssetId(String assetId) { this.assetId = assetId; }
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    public String getPlaceOfSafekeeping() { return placeOfSafekeeping; }
    public void setPlaceOfSafekeeping(String placeOfSafekeeping) { this.placeOfSafekeeping = placeOfSafekeeping; }
    public String getHoldingType() { return holdingType; }
    public void setHoldingType(String holdingType) { this.holdingType = holdingType; }
    public int getSettQnty() { return settQnty; }
    public void setSettQnty(int settQnty) { this.settQnty = settQnty; }
    public int getPendQnty() { return pendQnty; }
    public void setPendQnty(int pendQnty) { this.pendQnty = pendQnty; }
    public int getPendRcvQntty() { return pendRcvQntty; }
    public void setPendRcvQntty(int pendRcvQntty) { this.pendRcvQntty = pendRcvQntty; }
    public int getPendDlvrQntty() { return pendDlvrQntty; }
    public void setPendDlvrQntty(int pendDlvrQntty) { this.pendDlvrQntty = pendDlvrQntty; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}


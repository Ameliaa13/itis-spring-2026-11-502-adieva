public class TransactionEntity {

    private long id;
    private long userIdFrom;
    private long userIdTo;
    private double amount;
    private boolean success;
    private String denyReason;

    public long getId(){
        return id;
    }
    public void setId(long newId){
        id = newId;
    }

    public long getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(long userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public void setUserIdTo(long userIdTo) {
        this.userIdTo = userIdTo;
    }

    public long getUserIdTo() {
        return userIdTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDenyReason() {
        return denyReason;
    }

    public void setDenyReason(String denyReason) {
        this.denyReason = denyReason;
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
                "id=" + id +
                ", userIdFrom=" + userIdFrom +
                ", userIdTo=" + userIdTo +
                ", amount=" + amount +
                ", success=" + success +
                ", denyReason='" + denyReason + '\'' +
                '}';
    }
}

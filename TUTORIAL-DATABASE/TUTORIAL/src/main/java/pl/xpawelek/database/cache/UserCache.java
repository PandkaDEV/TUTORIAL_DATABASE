package pl.xpawelek.database.cache;

public class UserCache {

    // TODO: 23.02.2021

    private boolean status;

    public UserCache(){
        this.status = false;
    }
    public boolean isStatus(){
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

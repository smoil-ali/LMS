package com.example.lms.Model;

public class Error {
    NetworkState networkState;
    String error;

    public NetworkState getNetworkState() {
        return networkState;
    }

    public void setNetworkState(NetworkState networkState) {
        this.networkState = networkState;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

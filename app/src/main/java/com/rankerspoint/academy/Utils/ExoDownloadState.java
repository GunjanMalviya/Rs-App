package com.rankerspoint.academy.Utils;

public enum  ExoDownloadState {
    DOWNLOAD_START("Start Download"),
    DOWNLOAD_PAUSE("Pause Download"),
    DOWNLOAD_RESUME("Resume Download"),
    DOWNLOAD_COMPLETED("Downloaded"),
    DOWNLOAD_RETRY("Retry Download"),
    DOWNLOAD_QUEUE("Download In Queue");
    private String value;
    public String getValue() {
        return value;
    }
    private ExoDownloadState(String value) {
        this.value = value;
    }
}

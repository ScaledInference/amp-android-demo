package com.scaledinference.amped.store.lists;

public class HeaderListItem implements ListItem {
    private String header;

    public HeaderListItem(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}

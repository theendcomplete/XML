package com.xmltable.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface xmltableServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}

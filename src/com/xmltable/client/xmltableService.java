package com.xmltable.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("xmltableService")
public interface xmltableService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use xmltableService.App.getInstance() to access static instance of xmltableServiceAsync
     */
    public static class App {
        private static xmltableServiceAsync ourInstance = GWT.create(xmltableService.class);

        public static synchronized xmltableServiceAsync getInstance() {
            return ourInstance;
        }
    }
}

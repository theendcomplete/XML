package com.xmltable.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xmltable.client.xmltableService;

public class xmltableServiceImpl extends RemoteServiceServlet implements xmltableService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}
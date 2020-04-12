package com.kingshuk.messaging.javajmspractice.commonconfig;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class ContextConfiguration {

    public Context getJMSContextDetails() {
        Hashtable<String, String> envVariables = new Hashtable<>();
        envVariables.put(Context.PROVIDER_URL, "t3://localhost:7001");
        envVariables.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        envVariables.put(Context.SECURITY_PRINCIPAL, "admin");
        envVariables.put(Context.SECURITY_CREDENTIALS, "Iofdtiger#16");
        Context context = null;
        try {
            context = new InitialContext(envVariables);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return context;
    }
}

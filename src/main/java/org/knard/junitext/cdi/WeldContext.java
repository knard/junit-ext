package org.knard.junitext.cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class WeldContext {

    private final Weld weld;
    private final WeldContainer container;

    public WeldContext() {
        this.weld = new Weld();
        this.container = weld.initialize();
    }

    public <T> T getBean(Class<T> type) {
        return container.instance().select(type).get();
    }
    
    public void shutdown() {
    	weld.shutdown();
    }
}
/**
 * 
 */
package com.wordpress.andreiribas.cdi_weld_jpa_example.cdi.junit;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * @author Andrei Gonçalves Ribas
 *
 */
public class WeldJUnit4Runner extends BlockJUnit4ClassRunner {
 
    private final Class<?> testClass;
    
    private final Weld weld;
    
    private final WeldContainer container;
 
    public WeldJUnit4Runner(final Class<?> testClass) throws InitializationError {
    	
        super(testClass);
        
        this.testClass = testClass;
        
        this.weld = new Weld();
        
        this.container = weld.initialize();
        
    }
 
    @Override
    protected Object createTest() throws Exception {
    	
    	Object testInstance = testClass.newInstance();
    	
    	Field[] fields = testClass.getDeclaredFields();
        
        for(Field field: fields) {
        	
        	field.setAccessible(true);
        	
        	Inject injectAnnotation = field.getAnnotation(Inject.class);
        	
        	if(injectAnnotation != null) {
        		
        		Class<?> fieldType = field.getType();
        		
        		Object fieldValueInjectedFromWeld = container.instance().select(fieldType).get();
        		
        		field.set(testInstance, fieldValueInjectedFromWeld);
        	
        	}
        	
        }
        
        return testInstance;
    
    }
    
}
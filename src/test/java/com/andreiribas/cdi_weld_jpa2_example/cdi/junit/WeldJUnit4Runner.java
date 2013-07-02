/* 
The MIT License (MIT)

Copyright (c) 2013 Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
/**
 * 
 */
package com.andreiribas.cdi_weld_jpa2_example.cdi.junit;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
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
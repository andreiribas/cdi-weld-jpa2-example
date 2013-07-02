package com.wordpress.andreiribas.cdi_weld_jpa_example.domain;
/**
 * 
 */

import java.io.Serializable;

/**
 * @author Andrei Goncalves Ribas
 *
 **/
public abstract class AbstractDomain<TypeId extends Serializable> implements Serializable {
	
	private static final long serialVersionUID = -2100306541768310750L;
	
	public AbstractDomain() {}

	public AbstractDomain(TypeId id) {
		
		setId(id);
		
	}

	public abstract TypeId getId();

	public abstract void setId(TypeId id);

	@Override
	public String toString() {
		
		String toString = super.toString();
		
		if(this.getId() != null) {
			toString = getId().toString();
		}
		
		return toString;
		
	}

	@Override
	public int hashCode() {
		
		if (getId() != null) {
			
			return getId().hashCode() * 71;
			
		}
		else {
			
			return super.hashCode();
			
		}
		
	}

	@Override
	public boolean equals(Object other) {
		
		if (this == other)
			return true;
		
		if (!(this.getClass().isInstance(other)))
			return false;
		
		@SuppressWarnings("unchecked")
		final TypeId otherId = (TypeId) ((AbstractDomain<TypeId>) other).getId();
		
		return (getId() != null && getId() == otherId);
		
	}
	
}

package com.wordpress.andreiribas.cdi_weld_jpa_example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 */

/**
 * @author Andrei Goncalves Ribas
 *
 */
@Entity
@Table(name = "tb_user")
public class User extends AbstractDomain<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5866374692402278997L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name="id")
	private Long id;
	
	@Column(length = 255, name = "name", nullable = false)
	private String name;
	
	public User() {
		super();
	}
	
	public User(Long id) {
		super(id);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

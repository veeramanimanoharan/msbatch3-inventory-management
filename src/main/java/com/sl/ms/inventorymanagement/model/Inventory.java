package com.sl.ms.inventorymanagement.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Table
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	private LocalDateTime date;
	@Lob 
    private String sample;
	
	
	
	public String getSample() {
		return sample;
	}
	public void setSample(String sample) {
		this.sample = sample;
	}
	
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	
}

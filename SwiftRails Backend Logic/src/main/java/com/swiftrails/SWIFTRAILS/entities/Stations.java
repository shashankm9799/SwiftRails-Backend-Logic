package com.swiftrails.SWIFTRAILS.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Stations{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String origin;
	private String destination;
	private int price;
	private int tax;
	private Float serviceTax;
	private Float salesTax;
	
	public Long getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getPrice() {
        return price;
    }


    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPrice(int price) {
        this.price = price;
    }


	public Float getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(Float serviceTax) {
		this.serviceTax = serviceTax;
	}

	public Float getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(Float salesTax) {
		this.salesTax = salesTax;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}
	
	
}
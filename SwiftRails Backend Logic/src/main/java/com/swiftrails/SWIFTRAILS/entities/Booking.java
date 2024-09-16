package com.swiftrails.SWIFTRAILS.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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
public class Booking{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userEmail;
	private String origin;
	private String destination;
//	
//	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
//	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private String toDate;
	
//	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
//	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private String froDate;
	private int price;
	private int tax;
	private Float serviceTax;
	private Float salesTax;
	private boolean is_cancelled;
	
	public Long getId() {
        return id;
    }

	public Float getSalesTax() {
		return (float) (5*price/100);
	}
	public Float getServiceTax() {
		return (float) (5*price/100);
	}
	
    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getToDate() {
        return toDate;
    }

    public String getFroDate() {
        return froDate;
    }

    public int getPrice() {
        return price;
    }


    public boolean isCancelled() {
        return is_cancelled;
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

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setFroDate(String froDate) {
        this.froDate = froDate;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCancelled(boolean cancelled) {
        is_cancelled = cancelled;
    }


	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setSalesTax(Float salesTax) {
		this.salesTax = salesTax;
	}


	public void setServiceTax(Float serviceTax) {
		this.serviceTax = serviceTax;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}
	
	
}
package com.demo.swagger.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import com.demo.swagger.repository.Identifiable;

public class Order implements Identifiable<Long> {
	private long id;
	private long petId;
	private int quantity;
	private Date shipDate;
	private String status;
	private boolean complete;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public long getPetId() {
		return petId;
	}

	public void setPetId(long petId) {
		this.petId = petId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@ApiModelProperty(value = "Order Status", allowableValues = "placed, approved, delivered")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	@Override
	public Long getIdentifier() {
		return id;
	}
}

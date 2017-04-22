package com.niulbird.clubmgr.bfc.command;

public class FixtureSummary {
	private int yes;
	private int maybe;
	private int no;
	private int pending;
	public int getYes() {
		return yes;
	}
	public void setYes(int yes) {
		this.yes = yes;
	}
	public int getMaybe() {
		return maybe;
	}
	public void setMaybe(int maybe) {
		this.maybe = maybe;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getPending() {
		return pending;
	}
	public void setPending(int pending) {
		this.pending = pending;
	}
	
	public void addYes() {
		this.yes++;
	}
	
	public void addNo() {
		this.no++;
	}
	
	public void addMaybe() {
		this.maybe++;
	}
	
	public void addPending() {
		this.pending++;
	}
}

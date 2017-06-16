package com.dtt.chemlab.objs;

public class Loc {

	// Location Drug information
	private String id;
	private String drug_name;
	private String counting;
	private String position;
	private String remain;
	private String each;
	private String loc_standard;
	private String people;
	private String edit_time;

	public Loc() {
	}
	public void setLocationInfo(String id, String drug_name, String counting, String position,
			String remain, String each, String loc_standard, String people,
			String edit_time) {
		this.id = id;
		this.drug_name = drug_name;
		this.counting = counting;
		this.position = position;
		this.remain = remain;
		this.each = each;
		this.loc_standard = loc_standard;
		this.people = people;
		this.edit_time = edit_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRemain() {
		return remain;
	}

	public void setRemain(String remain) {
		this.remain = remain;
	}

	public String getLoc_standard() {
		return loc_standard;
	}

	public void setLoc_standard(String loc_standard) {
		this.loc_standard = loc_standard;
	}

	public String getEach() {
		return each;
	}

	public void setEach(String each) {
		this.each = each;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(String edit_time) {
		this.edit_time = edit_time;
	}

	public String getDrug_name() {
		return drug_name;
	}

	public void setDrug_name(String drug_name) {
		this.drug_name = drug_name;
	}

	public String getCounting() {
		return counting;
	}

	public void setCounting(String counting) {
		this.counting = counting;
	}

}

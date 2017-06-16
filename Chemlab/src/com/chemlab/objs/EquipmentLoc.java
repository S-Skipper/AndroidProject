package com.chemlab.objs;

public class EquipmentLoc {

	/*
	 * equip_name 仪器名
	 * fixed_assets_NO  固定资产号（唯一）
	 * factory_NO  公司编号
	 * time_buying 购买时间
	 * people 操作人
	 * position 位置 
	 * state 状态（在用 闲置 损坏 报废）
	 * state_explane 状态说明
	 * edit_time 编辑时间
	 */

	private String name;
	private String PPE;
	private String factory_NO;
	private String time_buying;
	private String oprator;
	private String position;
	private String state;
	private String state_explane;
	private String edit_time;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPPE() {
		return PPE;
	}

	public void setPPE(String pPE) {
		PPE = pPE;
	}

	public String getFactory_NO() {
		return factory_NO;
	}

	public void setFactory_NO(String factory_NO) {
		this.factory_NO = factory_NO;
	}

	public String getTime_buying() {
		return time_buying;
	}

	public void setTime_buying(String time_buying) {
		this.time_buying = time_buying;
	}

	public String getOprator() {
		return oprator;
	}

	public void setOprator(String oprator) {
		this.oprator = oprator;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState_explane() {
		return state_explane;
	}

	public void setState_explane(String state_explane) {
		this.state_explane = state_explane;
	}

	public String getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(String edit_time) {
		this.edit_time = edit_time;
	}

}

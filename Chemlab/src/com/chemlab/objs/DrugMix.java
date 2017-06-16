package com.chemlab.objs;

public class DrugMix {

	//Base Drug information
	/*
	 * {"drug_mix":"(1+2)盐酸溶液","attention":"","standard":"ml"}
	 */
	private String mix_name;
	private String attention;
	private String standard;
	

	public DrugMix() {
	}


	public String getMix_name() {
		return mix_name;
	}


	public void setMix_name(String mix_name) {
		this.mix_name = mix_name;
	}


	public String getAttention() {
		return attention;
	}


	public void setAttention(String attention) {
		this.attention = attention;
	}


	public String getStandard() {
		return standard;
	}


	public void setStandard(String standard) {
		this.standard = standard;
	}
	
}

package controller;

public class EntityName {
	public static String getname(String s){
		switch(s){
			case "G":
				return "crane";
			case "C":
				return "cart";
			case "Q":
				return "quay";
			case "E":
				return "equip";
			case "R":
				return "rtg";
			case "S":
				return "stacker";
		}
		
		return "";
	}
}

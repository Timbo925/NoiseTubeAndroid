package com.noisetube.models;

import java.util.Comparator;

public class BadgeCompare implements Comparator<Badge> {

	@Override
	public int compare(Badge b1, Badge b2) {
		if (b1.isAchieved() == b2.isAchieved()){
			return 0;
		} else if (b1.isAchieved()) {
			return -1;
		} else {
			return 1;
		} 
	}
}

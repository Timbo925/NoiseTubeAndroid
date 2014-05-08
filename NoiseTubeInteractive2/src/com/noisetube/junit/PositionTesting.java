package com.noisetube.junit;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.noisetube.models.Position;

public class PositionTesting extends TestCase {


	@Test
	public void testInside() {
		//112 km uit elkaar
		Position position = new Position(0, 0);
		Position position2 = new Position(0, 1);
		assertTrue(position.inRangePosition(position2, 122));
	}
	
	@Test
	public void testOutside() {
		//112 km uit elkaar
		Position position = new Position(0, 0);
		Position position2 = new Position(0, 1);
		assertFalse(position.inRangePosition(position2, 1));
	}
	
	@Test
	public void testPoly() {
		Position position = new Position(0, 0);
		Position position2 = new Position(0, 3);
		Position position3 = new Position(3, 0);
		Position position4 = new Position(3, 3);
		Position position5 = new Position(0.5f, 0.1f);
		List<Position> list = new ArrayList<Position>();
		list.add(position);
		list.add(position2);
		list.add(position3);
		list.add(position4);
		list.add(position);
		assertTrue(position5.insidePolygon(list));
	}
}

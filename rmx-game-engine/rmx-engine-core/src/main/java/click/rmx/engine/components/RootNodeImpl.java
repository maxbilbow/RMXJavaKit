package click.rmx.engine.components;

import click.rmx.core.Categories;
import click.rmx.engine.geometry.Geometry;
import click.rmx.engine.geometry.GeometryImpl;
import click.rmx.engine.geometry.Shape;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static click.rmx.core.RMX.rmxObjectCount;

public class RootNodeImpl extends GameNode implements RootNode {

	


	private Set<Geometry> geometries;//new HashSet<>();
	
	
	private
	Map<Shape, Set<Geometry>> shapeMap;// = new HashMap<>();
	
	int count = -1;
	
	@Override
	public void onEventDidEnd(String theEvent, Object args) {
		if (theEvent == GeometryImpl.GEOMETRY_WAS_DESTROYED ) {
			this.geometries = null;
			this.shapeMap = null;
			this.count = -1;
		}
			
		super.onEventDidEnd(theEvent, args);
	}
	
	@Override
	public Map<Shape, Set<Geometry>> getGeometries() {
		int count = rmxObjectCount(Categories.GEOMETRY);
		
		if (shapeMap == null || this.count > count) {
			geometries = new HashSet<>();
			shapeMap = new HashMap<>();
		}
		
		if (this.count != count) {
			this.addGeometryToList(geometries);
			this.count = count;
		}
	
		geometries.forEach(geo -> {
			Set<Geometry> set = shapeMap.get(geo.getShape());
			if (set == null) {
				set = new HashSet<>();
				shapeMap.put(geo.getShape(), set);
			}
			set.add(geo);
		});
		return shapeMap;
	}

	private RootNodeImpl(){
		super();
		this.setName("rootNode");
	}



	public static RootNodeImpl newInstance() {
		return new RootNodeImpl();
	}



}

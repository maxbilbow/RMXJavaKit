package click.rmx.engine.components;

import click.rmx.engine.geometry.Geometry;
import click.rmx.engine.geometry.Shape;

import java.util.Map;
import java.util.Set;

public interface RootNode extends Node {
//	@NotNull
	Map<Shape, Set<Geometry>> getGeometries();
}

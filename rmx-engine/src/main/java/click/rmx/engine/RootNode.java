package click.rmx.engine;

import java.util.Map;
import java.util.Set;

import click.rmx.engine.geometry.Geometry;
import click.rmx.engine.geometry.Shape;
import com.sun.istack.internal.NotNull;

public interface RootNode extends Node {
	@NotNull
	Map<Shape, Set<Geometry>> getGeometries();
}

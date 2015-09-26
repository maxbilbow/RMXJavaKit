package click.rmx.engine.behaviour;


import click.rmx.engine.components.Node;

@FunctionalInterface
public interface IBehaviour  {
//	public default void lateUpdate() ;
	
//	public default void setNode(Node node) {}
	
	default boolean isEnabled() {
		return true; 
	}
	
	default boolean hasLateUpdate() {
		return false;
	}

	default String getName() {
		return "Anonomous: " + getClass().getName();
	}
	
	default void broadcastMessage(String message){}


	default void broadcastMessage(String message, Object args) {}
	
	void update(Node node);

	default void setNode(Node gameNode){};
	
//	void setNode(Node node);
}

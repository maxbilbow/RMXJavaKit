package click.rmx.core;

public interface IRMXObject extends IEventListener, KeyValueObserver, Categorizable, IMessageable  {

	Long getId();

	Object setValue(String forKey, Object value);

	Object getValue(String forKey);

	Object getValueOrSetDefault(String forKey, Object value);

	void AddObserver(KeyValueObserver observer, String forKey);

	void removeObserver(KeyValueObserver observer, String forKey);

	void removeObserver(KeyValueObserver observer);

	String uniqueName();

	String getName();

	void setName(String name);
	
}
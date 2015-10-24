package click.rmx.core;

public interface KeyValueObserver {
	void onValueForKeyWillChange(String key, Object value, IRMXObject sender);
	void onValueForKeyDidChange(String key, Object value, IRMXObject sender);
}

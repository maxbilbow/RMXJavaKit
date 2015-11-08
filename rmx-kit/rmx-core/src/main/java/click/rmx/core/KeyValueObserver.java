package click.rmx.core;

public interface KeyValueObserver {
	void onValueForKeyWillChange(String key, Object value, Object sender);
	void onValueForKeyDidChange(String key, Object value, Object sender);
}

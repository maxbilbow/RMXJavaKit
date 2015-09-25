package messages;

import click.rmx.core.IRMXObject;

public interface KeyValueObserver {
	void onValueForKeyWillChange(String key, Object value, IRMXObject sender);
	void onValueForKeyDidChange(String key, Object value, IRMXObject sender);
}

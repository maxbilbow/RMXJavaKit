package click.rmx.core;

public interface Categorizable {
	default String getCategoryName() {
		return this.getClass().getSimpleName();
	}
}

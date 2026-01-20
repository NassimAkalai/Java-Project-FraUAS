package manager;

/**
 * Interface for defining options that can be executed based on user selection.
 */
public interface SelectionOptions {
    /**
     * Executes the logic associated with the selected option.
     *
     * @param args Arguments passed to the execution logic (if any).
     */
    void execute(String[] args);
}
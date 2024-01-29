package adventure;

import java.util.HashMap;
import java.util.Map;

public class FillerStage implements AdventureStage {
    private final String prompt;
    private final Map<String, AdventureStage> responses;

    /**
     * Constructor for filler stage at end of game (No responses).
     *
     * @param prompt Prompt for fillerStage.
     */
    public FillerStage(String prompt) {
        this(prompt, new HashMap<>());
    }

    /**
     * constructor for filler stage in middle of game.
     *
     * @param prompt      prompt for fillerStage.
     * @param responses    responses for fillerStage.
     */
    public FillerStage(String prompt, Map<String, AdventureStage> responses) {
        this.prompt = prompt;
        this.responses = responses;
    }

    /**
     * Plays stage.
     * Filler stages do nothing, but display their prompt, so this does nothing.
     */
    @Override
    public void playStage() {}

    @Override
    public String nextStagePrompt() {
        return this.prompt;
    }

    @Override
    public Map<String, AdventureStage> getResponses() {
        return this.responses;
    }

}

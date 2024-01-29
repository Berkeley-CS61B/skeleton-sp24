package adventure;

import java.util.Map;

public interface AdventureStage {
    void playStage();
    String nextStagePrompt();
    Map<String, AdventureStage> getResponses();
}

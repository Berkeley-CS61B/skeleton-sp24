package browser;

import java.util.List;

/**
 * Created by hug.
 */
public record NgordnetQuery(List<String> words,
        int startYear,
        int endYear,
        int k,
        NgordnetQueryType ngordnetQueryType) {}

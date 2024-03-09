package browser;

import com.google.gson.Gson;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Arrays;
import java.util.List;

public abstract class NgordnetQueryHandler implements Route {
    public abstract String handle(browser.NgordnetQuery q);
    private static final Gson gson = new Gson();

    private static List<String> commaSeparatedStringToList(String s) {
        String[] requestedWords = s.split(",");
        for (int i = 0; i < requestedWords.length; i += 1) {
            requestedWords[i] = requestedWords[i].trim();
        }
        return Arrays.asList(requestedWords);
    }

    private static NgordnetQuery readQueryMap(QueryParamsMap qm) {
        List<String> words = commaSeparatedStringToList(qm.get("words").value());

        int startYear;
        int endYear;
        int k;
        NgordnetQueryType ngordnetQueryType;

        try {
            startYear = Integer.parseInt(qm.get("startYear").value());
        } catch (RuntimeException e) {
            startYear = 1900;
        }

        try {
            endYear = Integer.parseInt(qm.get("endYear").value());
        } catch (RuntimeException e) {
            endYear = 2020;
        }

        try {
            k = Integer.parseInt(qm.get("k").value());
        } catch (RuntimeException e) {
            k = 0;
        }

        try {
            ngordnetQueryType = NgordnetQueryType.valueOf(qm.get("ngordnetQueryType").value());
        } catch (RuntimeException e) {
            ngordnetQueryType = NgordnetQueryType.HYPONYMS;
        }

        return new NgordnetQuery(words, startYear, endYear, k, ngordnetQueryType);
    }

    @Override
    public String handle(Request request, Response response) throws Exception {
        QueryParamsMap qm = request.queryMap();
        NgordnetQuery nq = readQueryMap(qm);
        String queryResult = handle(nq);
        return gson.toJson(queryResult);
    }
}

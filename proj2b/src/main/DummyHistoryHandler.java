package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class DummyHistoryHandler extends NgordnetQueryHandler {
    @Override
    public String handle(NgordnetQuery q) {
        return "NGrams and Timeseries aren't relevant for 2B! This button should do nothing.";
    }
}

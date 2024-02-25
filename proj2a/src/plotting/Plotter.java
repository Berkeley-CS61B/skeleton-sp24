package plotting;

import ngrams.TimeSeries;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class Plotter {

    public static XYChart generateTimeSeriesChart(List<String> words, List<TimeSeries> lts) {
        if (words.size() != lts.size()) {
            throw new IllegalArgumentException("List of words and List of time series objects must be the same length");
        }

        XYChart chart = new XYChart(800, 600);

        for (int i = 0; i < words.size(); i += 1) {
            TimeSeries ts = lts.get(i);
            String word = words.get(i);
            chart.addSeries(word, ts.years(), ts.data());
        }

        return chart;
    }

    public static void displayChart(XYChart chart) {
        new SwingWrapper(chart).displayChart();
    }

    public static String encodeChartAsString(XYChart chart) {
        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
        return encodedImage;
    }
}

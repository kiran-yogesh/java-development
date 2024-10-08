import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;


public class WeatherAppGUI extends JFrame {
    private static final String API_KEY = "c46971a931d9a6fb4fc1c5b2caaa2e12";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private JTextField cityTextField;
    private JLabel weatherLabel;

    public WeatherAppGUI() {
        setTitle("Weather App");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        cityTextField = new JTextField();
        JButton getWeatherButton = new JButton("Get Weather");
        weatherLabel = new JLabel("", SwingConstants.CENTER);

        panel.add(new JLabel("Enter city name:", SwingConstants.CENTER));
        panel.add(cityTextField);
        panel.add(getWeatherButton);
        add(panel, BorderLayout.CENTER);
        add(weatherLabel, BorderLayout.SOUTH);

        getWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityTextField.getText();
                getWeather(city);
            }
        });
    }

    private void getWeather(String city) {
        String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .build();
                
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());
                double temp = json.getJSONObject("main").getDouble("temp");
                String weather = json.getJSONArray("weather").getJSONObject(0).getString("description");

                weatherLabel.setText("Temperature: " + temp + "°C, Weather: " + weather);
            } else {
                weatherLabel.setText("Error: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            weatherLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeatherAppGUI app = new WeatherAppGUI();
            app.setVisible(true);
        });
    }
}

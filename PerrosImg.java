import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PerrosImg {
    public static final String BASE_URL = "https://dog.ceo/api/breeds/image/random/";
    public static String breed = "";

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                if (count < 5) { // Mostrar 5 imágenes aleatorias
                    DogsApiResponse response;
                    if (!breed.equals("")) {
                        response = queryByBreed(breed);
                    } else {
                        response = query(1);
                    }
                    ArrayList<String> urls = response.getMessage();
                    if (urls.size() > 0) {
                        for (String u : urls) {
                            System.out.println(u);
                            EventQueue.invokeLater(() -> {
                                JFrame ex = new VistaImagen(u);
                                ex.setVisible(true);
                            });
                        }
                    }
                    count++;
                } else {
                    timer.cancel();
                }
            }
        };

        String inputBreed = JOptionPane.showInputDialog(null, "Ingrese una raza de perro:", "Raza de Perro", JOptionPane.INFORMATION_MESSAGE);
        if (inputBreed != null) {
            breed = inputBreed;
        }

        timer.schedule(task, 0, 15000);
    }

    public static DogsApiResponse query(int n) {
        DogsApiResponse response = null;
        try {
            URL u = new URL(BASE_URL + n);

            BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
            String buffer;
            StringBuilder jsonText = new StringBuilder();
            while ((buffer = in.readLine()) != null) {
                jsonText.append(buffer);
            }
            in.close();
            Gson gson = new Gson();
            response = gson.fromJson(jsonText.toString(), DogsApiResponse.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static DogsApiResponse queryByBreed(String breed) {
        DogsApiResponse response = null;
        try {
            URL u = new URL("https://dog.ceo/api/breed/" + breed + "/images/random");

            BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
            String buffer;
            StringBuilder jsonText = new StringBuilder();
            while ((buffer = in.readLine()) != null) {
                jsonText.append(buffer);
            }
            in.close();
            Gson gson = new Gson();
            response = gson.fromJson(jsonText.toString(), DogsApiResponse.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}











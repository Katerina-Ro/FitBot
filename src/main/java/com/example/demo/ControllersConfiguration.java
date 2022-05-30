package com.example.demo;

import com.example.demo.ui.StartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class ControllersConfiguration {

    @Bean(name="mainView")
    public ViewHolder getMainView() throws IOException {
        return loadView("fxml/start-view.fxml");
    }

    @Bean
    public StartController getStartController() throws IOException {
        return (StartController) getMainView().getController();
    }

    protected ViewHolder loadView(String url) throws IOException {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.load(fxmlStream);
            return new ViewHolder(fxmlLoader.getRoot(), fxmlLoader.getController());
        } finally {
            {
                if (fxmlStream != null) {
                    fxmlStream.close();
                }
            }
        }
    }

    /**
     * Класс - оболочка: контроллер, указываем в качестве бина,
     */
    public static class ViewHolder {
        private Parent view;
        private Object controller;

        public ViewHolder(Parent view, Object controller) {
            this.view = view;
            this.controller = controller;
        }

        public Parent getView() {
            return view;
        }

        public void setView(Parent view) {
            this.view = view;
        }

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }
    }
}



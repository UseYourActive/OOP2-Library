package com.library.frontend.utils;

import com.library.database.entities.User;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * The {@code SceneLoader} class provides utility methods for loading JavaFX scenes and managing stages.
 * It includes methods to load main scenes, load scenes into a specific stage, and load modal dialog scenes.
 * Additionally, it allows setting a username and transferable objects for use in loaded scenes.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Load a main scene on a button click event
 * SceneLoader.load(event, "mainScene.fxml", "Main Scene");
 *
 * // Load a main scene in a specific stage
 * SceneLoader.load(primaryStage, "mainScene.fxml", "Main Scene");
 *
 * // Load a modal dialog scene with transferable objects
 * SceneLoader.loadModalityDialog("dialogScene.fxml", "Dialog Scene", object1, object2);
 * }
 * </pre>
 *
 * @see javafx.fxml.FXMLLoader
 * @see javafx.scene.Parent
 * @see javafx.scene.Scene
 * @see javafx.stage.Stage
 * @see javafx.stage.Modality
 */
public class SceneLoader {
    private static final Logger logger = LoggerFactory.getLogger(SceneLoader.class);

    /**
     * The username to be transferred to loaded scenes.
     */
    @Getter
    @Setter
    private static User user;

    /**
     * The primary stage used for loading scenes.
     */
    @Getter
    @Setter
    private static Stage stage;

    /**
     * Objects to be transferred to loaded scenes.
     */
    @Setter
    @Getter
    private static Object[] transferableObjects;

    private SceneLoader() {
    }

    /**
     * Loads a main scene triggered by an event (e.g., button click).
     *
     * @param event         The event that triggered the scene load.
     * @param resourceFiles The FXML resource file for the scene.
     * @param stageTitle    The title of the stage.
     */
    public static void load(Event event, String resourceFiles, String stageTitle,Object ... objects) {
        if (objects != null)
            SceneLoader.transferableObjects = objects;

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loadBackground(stage, resourceFiles, stageTitle);
    }

    /**
     * Loads a main scene without an event, assuming a primary stage is set.
     *
     * @param resourceFiles The FXML resource file for the scene.
     * @param stageTitle    The title of the stage.
     */
    public static void load(String resourceFiles, String stageTitle,Object ... objects) {
        if (stage == null)
            throw new RuntimeException();

        if (objects != null)
            SceneLoader.transferableObjects = objects;

        loadBackground(stage, resourceFiles, stageTitle);
    }

    /**
     * Loads a main scene into a specified stage.
     *
     * @param stage         The stage where the scene will be loaded.
     * @param resourceFiles The FXML resource file for the scene.
     * @param stageTitle    The title of the stage.
     */
    public static void load(Stage stage, String resourceFiles, String stageTitle,Object ... objects) {
        if (objects != null)
            SceneLoader.transferableObjects = objects;

        loadBackground(stage, resourceFiles, stageTitle);
    }

    /**
     * Loads the background of a stage with the specified FXML resource file and title.
     * This method is used internally by the SceneLoader utility to set the scene, title, and other properties
     * of the provided stage based on the FXML resource file.
     *
     * @param stage         The stage to load the scene background into.
     * @param resourceFiles The FXML resource file for the scene background.
     * @param stageTitle    The title to set for the stage.
     * @throws IllegalStateException If an error occurs during opening the dialog with the table view.
     * @throws NullPointerException  If unexpected null pointers occur.
     * @throws IOException           If an error occurs during the loading of the FXML form.
     */
    private static void loadBackground(Stage stage, String resourceFiles, String stageTitle) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneLoader.class.getResource(resourceFiles)));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(stageTitle);
            stage.show();
            SceneLoader.stage = stage;

            logger.info("Loaded scene: {}", resourceFiles);
        } catch (IllegalStateException e) {
            logger.error("Error occurred during opening dialog with table view", e);
        } catch (NullPointerException e) {
            logger.error("Null Pointer occurred unexpectedly!", e);
        } catch (IOException e) {
            logger.error("Error loading form: {}", resourceFiles, e);
            DialogUtils.showError("Error", "An error occurred when trying to open the new dialog window!");
        }
    }

    /**
     * Loads a modal dialog scene with transferable objects.
     *
     * @param resourceFiles The FXML resource file for the dialog scene.
     * @param stageTitle    The title of the dialog stage.
     * @param objects       Objects to be transferred to the loaded scene.
     */
    public static void loadModalityDialog(String resourceFiles, String stageTitle, Object... objects) {
        try {
            if (objects != null)
                SceneLoader.transferableObjects = objects;

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(SceneLoader.getStage());

            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneLoader.class.getResource(resourceFiles)));
            Scene dialogScene = new Scene(root);

            dialogStage.setTitle(stageTitle);
            dialogStage.setScene(dialogScene);
            dialogStage.setResizable(false);
            dialogStage.showAndWait();
        } catch (IllegalStateException e) {
            logger.error("Error occurred during opening dialog with table view!", e);
        } catch (NullPointerException e) {
            logger.error("Null Pointer occurred unexpectedly!", e);
        } catch (IOException e) {
            logger.error("Error loading form: {}", resourceFiles, e);
            DialogUtils.showError("Error", "An error occurred when trying to open the new dialog window!");
        }
    }
}

package frontend_viewcontroller;

import backend_models.*;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JRadioButton;

/**
 * This class is responsible for manipulating the data in the backend, and
 * letting the user otherwise control the GUI on the screen.
 *
 * Responsibilities of this class include:
 *
 * (1) Ask the MainViewDisplay object to change the GUI on the screen in
 * response to user input actions (without modifying any data in the backend).
 *
 * (2) Ask the backend models to modify its data, and also ask the
 * MainViewDisplay object to update the GUI on the screen (to match the data in
 * the backend), in response to user input actions.
 *
 * There should be no code here directly about painting graphics on the screen!
 * That belongs in the MainViewDisplay class.
 *
 * There should also be no code here directly about modeling the problem or
 * situation your program solves.
 *
 * All problem or situation modeling related code must go in the backend classes
 *
 * The FOUR (4) main steps to creating GUI widgets are labeled below. There are
 * many smaller steps you should get familiar with as well.
 *
 * @author cheng
 */
public class ModelsAndViewsController {

    /*
     *
     * ModelsAndViewsController needs to have an instance variable to reference
     * the backend's models because the frontend's ModelsAndViewsController is
     * responsible for asking the backend to modify its data.
     *
     * Since the backend models is initially set up by an instance of the
     * BackendModelSetup class, we just need this one instance variable here:
     */
    BackendModelSetup theBackendModel;
    /*
     *
     * This class also needs to have an instance variable to reference the
     * frontend's MainViewDisplay because the ModelsAndViewsController object is
     * responsible for asking the MainViewDisplay object to update itself.
     */
    MainViewDisplay theMainViewDisplay;

    /*
     *
     * Step 1 of 2 to provide user controls: write user action as private class
     * ------------------------------------------------------------------------
     *
     * User actions are written as private inner classes that implement
     * ActionListener, and override the actionPerformed method.
     *
     * Use the following as a template for writting more user actions.
     */
    private class SaveToFileAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                String pathToFile = theMainViewDisplay.showSaveDialog();
                if (pathToFile == null) {
                    return;
                }

                theBackendModel.picFile.saveToDisk(pathToFile, "png");
                theMainViewDisplay.updateStatus("Savd to: " + pathToFile + ".png");
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    private class RenderAction implements ActionListener {

        Thread t;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.t == null || !this.t.isAlive()) {
                RenderWithStatus rws = new RenderWithStatus(new RenderRunner());
                this.t = new Thread(rws);
                this.t.start();
            }
        }
    }

    private class RenderRunner implements Runnable {

        @Override
        public void run() {
            Renderer.render(theBackendModel.picFile);
        }
    }

    private class RenderWithStatus implements Runnable {

        Thread renderTread;

        public RenderWithStatus(RenderRunner rr) {
            this.renderTread = new Thread(rr);
        }

        @Override
        public void run() {
            this.renderTread.start();

            while (this.renderTread.isAlive()) {
                theMainViewDisplay.updateStatus(Renderer.status);
                theMainViewDisplay.updatePicLabel();

//                try {
//                    java.lang.Thread.sleep(500);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ModelsAndViewsController.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }

            theMainViewDisplay.updateStatus(Renderer.status);
            theMainViewDisplay.updatePicLabel();
        }
    }

    /*
     *
     * Constructor. Probably nothing for students to change.
     */
    public ModelsAndViewsController(BackendModelSetup aBackend, MainViewDisplay aMainViewDisplay) {
        this.theBackendModel = aBackend;
        this.theMainViewDisplay = aMainViewDisplay;
        this.initController();
    }

    /*
     *
     * Step 2 of 2 to provide user controls: wire user action to GUI widgets
     * ----------------------------------------------------------------------
     *
     * Once a private inner class has been written for a user action, you can
     * wire it to a GUI widget (i.e. one of the GUI widgets you created in the
     * MainViewDisplay class and which you gave a memorable variable name!).
     *
     * Use the following as templates for wiring in more user actions.
     */
    private void initController() {
        this.theMainViewDisplay.saveButton.addActionListener(new SaveToFileAction());
        this.theMainViewDisplay.renderButton.addActionListener(new RenderAction());
    }
}

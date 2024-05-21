import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame {
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JTextArea log = new JTextArea();
    private boolean isServerWorking = false;

    public ServerWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setTitle("Управление сервером");
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(btnStart);
        panel.add(btnStop);

        log.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(log);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerWorking) {
                    isServerWorking = true;
                    log.append("Сервер запущен.\n");
                    btnStart.setEnabled(false);
                } else {
                    log.append("Сервер уже включен.\n");
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking) {
                    isServerWorking = false;
                    log.append("Остановка сервера.\n");
                    btnStop.setEnabled(false);
                    btnStart.setEnabled(true);
                } else {
                    log.append("Сервер не включен.\n");
                }
            }
        });

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }}



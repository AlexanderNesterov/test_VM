package ru.nest;

import javax.swing.*;

public class Window {
    private JFrame jFrame = getFrame();
    private JPanel jPanel = new JPanel();

    public void showWindow() {
        jFrame.add(jPanel);

        JButton showTaskButton = new JButton("Показать задачу");
        jPanel.add(showTaskButton);
        showTaskButton.addActionListener(e -> JOptionPane.showMessageDialog(jPanel,
                "Назначение: Полиномиальное интерполирование значений функции с заданным аргументом.",
                "Задача", JOptionPane.INFORMATION_MESSAGE));

        JButton showCreatorsButton = new JButton("Показать создателей");
        jPanel.add(showCreatorsButton);
        showCreatorsButton.addActionListener(e -> JOptionPane.showMessageDialog(jPanel,
                "Над проектом работали студенты 3-го курса ФИИТ ПММ : Нестеров А.,Седиков К.,Елфимов А. ",
                "Создатели:", JOptionPane.INFORMATION_MESSAGE));

        JButton executeButton = new JButton("Выполнить");
        jPanel.add(executeButton);
        executeButton.addActionListener(actionEvent -> {
            Polynomial pol = new Polynomial();
            try {
                pol.calculate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private JFrame getFrame(){
        JFrame jFrame = new JFrame(){};
        jFrame.setVisible(true);
        jFrame.setBounds(750,250,500,500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return jFrame;
    }
}

package ru.nest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {
    private JFrame jFrame = getFrame();
    private JPanel jPanel = new JPanel();

    public void showWindow() {
        jFrame.add(jPanel);

        JButton showTaskButton = new JButton("Показать задачу ->");
        jPanel.add(showTaskButton);
        showTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(jPanel,
                        "Назначение: Полиномиальное интерполирование значений функции с заданным аргументом.",
                        "Задача",JOptionPane.OK_CANCEL_OPTION);
            }
        });

        JButton showCreatorsButton = new JButton("Показать создателей ->");
        jPanel.add(showCreatorsButton);
        showCreatorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jPanel,
                        "Над проектом работали студенты 3-го курса ФИИТ ПММ : Нестеров А.,Седиков К.,Елфимов А. ",
                        "Создатели:",JOptionPane.PLAIN_MESSAGE);
            }
        });

        JButton executeButton = new JButton("Выполнить");
        jPanel.add(executeButton);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Polynomial pol = new Polynomial();
                try {
                    pol.calculate();
                } catch (Exception e) {
                }
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

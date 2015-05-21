/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

/**
 *
 * @author raksamary
 */
public class ArrowPanel extends JPanel {

    private final int ARR_SIZE = 4;
    final Direction direction;
    final int size;
    final Dimension dimension;

    public ArrowPanel(Direction dir, int siz) {
        this.direction = dir;
        this.setForeground(Color.red);
        this.size = siz;
        this.dimension = this.getDimension();
        setPreferredSize(this.dimension);
        setMinimumSize(this.dimension);
        setMaximumSize(this.dimension);
        setSize(this.dimension);
        setLayout(null);

    }

    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);
        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }

    @Override
    public void paintComponent(Graphics g) {
        switch (this.direction) {
            case UP:
                drawArrow(g, 5, this.dimension.height, 5, 0);
                break;
            case DOWN:
                drawArrow(g, 5, 0, 5, this.dimension.height);
                break;
            case LEFT:
                drawArrow(g, this.dimension.width, 5, 0, 5);
                break;
            case RIGHT:
                drawArrow(g, 0, 5, this.dimension.width, 5);
                break;
            default:
                throw new AssertionError(this.direction.name());
        }
    }

    private Dimension getDimension() {
        Dimension dim = null;
        switch (this.direction) {
            case UP:
                dim = new Dimension(10, size);
                break;
            case DOWN:
                dim = new Dimension(10, size);
                break;
            case LEFT:

                dim = new Dimension(size, 10);
                break;
            case RIGHT:

                dim = new Dimension(size, 10);
                break;

        }

        return dim;
    }

}

enum Direction {

    UP, DOWN, LEFT, RIGHT
}

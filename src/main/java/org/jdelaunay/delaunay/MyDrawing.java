package org.jdelaunay.delaunay;
/**
 * Delaunay Package.
 *
 * @author Jean-Yves MARTIN, Erwan BOCHER
 * @date 2009-01-12
 * @version 1.0
 */

import java.awt.Graphics;

import javax.swing.JFrame;

public class MyDrawing extends JFrame {
        private static final long serialVersionUID = 1L;
        private MyMesh myMesh;

        public MyDrawing() {
                super("Display Panel");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(1200, 720);
                setVisible(true);

                this.myMesh = null;
        }

        public void add(MyMesh myMesh) {
                this.myMesh = myMesh;
        }

	@Override
        public void paint(Graphics g) {
                if (myMesh != null) {
			myMesh.displayObject(g);
		}
        }

        public void refresh() {
                this.invalidate();
                this.repaint();
        }
}

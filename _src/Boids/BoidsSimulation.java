import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class BoidsSimulation extends JPanel implements ActionListener, MouseMotionListener {
    private static final int WIDTH = 800, HEIGHT = 600;
    private static final int NUM_BOIDS = 50;
    private static final int MAX_SPEED = 4;
    private static final int BOID_RADIUS = 5;
    private static final int NEIGHBOR_RADIUS = 50;

    private double separationWeight = 0.1;
    private double cohesionWeight = 0.01;
    private double alignmentWeight = 0.05;
    private double mouseForce = 0.2;

    private boolean isFoodMode = true; // Toggle state
    private Point mousePosition = new Point(WIDTH / 2, HEIGHT / 2);

    private final ArrayList<Boid> boids;
    private final Timer timer;

    private final JSlider alignmentSlider;
    private final JSlider cohesionSlider;
    private final JSlider separationSlider;
    private final JSlider mouseForceSlider;
    private final JButton toggleButton;

    private final JLabel alignmentValueLabel;
    private final JLabel cohesionValueLabel;
    private final JLabel separationValueLabel;
    private JLabel mouseForceValueLabel = new JLabel();

    public BoidsSimulation() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.boids = new ArrayList<>();

        // Initialize boids
        resetBoids();

        // Timer for animation
        timer = new Timer(20, this);
        timer.start();

        // Mouse motion listener
        this.addMouseMotionListener(this);

        // Sliders for alignment, cohesion, and separation weights
        alignmentSlider = createPreciseSlider((int) (alignmentWeight * 100));
        cohesionSlider = createPreciseSlider((int) (cohesionWeight * 100));
        separationSlider = createPreciseSlider((int) (separationWeight * 100));

        // Vertical slider for mouse force
        mouseForceSlider = new JSlider(JSlider.VERTICAL, 0, 200, (int) (mouseForce * 100));
        mouseForceSlider.addChangeListener(e -> {
            mouseForce = mouseForceSlider.getValue() / 100.0;
            mouseForceValueLabel.setText(String.format("%.2f", mouseForce));
        });

        // Labels for showing slider values
        alignmentValueLabel = new JLabel(String.format("%.2f", alignmentWeight));
        cohesionValueLabel = new JLabel(String.format("%.2f", cohesionWeight));
        separationValueLabel = new JLabel(String.format("%.2f", separationWeight));
        mouseForceValueLabel = new JLabel(String.format("%.2f", mouseForce));

        // Toggle Button
        toggleButton = new JButton("Toggle Food/Predator");
        toggleButton.addActionListener(e -> isFoodMode = !isFoodMode);

        // Layout for controls with labels and current value labels
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(4, 3, 10, 10));
        
        controlPanel.add(new JLabel("Alignment:"));
        controlPanel.add(alignmentSlider);
        controlPanel.add(alignmentValueLabel);
        
        controlPanel.add(new JLabel("Cohesion:"));
        controlPanel.add(cohesionSlider);
        controlPanel.add(cohesionValueLabel);
        
        controlPanel.add(new JLabel("Separation:"));
        controlPanel.add(separationSlider);
        controlPanel.add(separationValueLabel);

        controlPanel.add(toggleButton);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(mouseForceSlider, BorderLayout.CENTER);
        rightPanel.add(mouseForceValueLabel, BorderLayout.SOUTH);

        JFrame frame = new JFrame("Boids Simulation");
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private JSlider createPreciseSlider(int initialValue) {
        JSlider slider = new JSlider(0, 100, initialValue);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(false);
        slider.addChangeListener(e -> {
            double value = slider.getValue() / 100.0; // Scale to 0.00 - 1.00 range
            if (slider == alignmentSlider) {
                alignmentWeight = value;
                alignmentValueLabel.setText(String.format("%.2f", alignmentWeight));
            } else if (slider == cohesionSlider) {
                cohesionWeight = value;
                cohesionValueLabel.setText(String.format("%.2f", cohesionWeight));
            } else if (slider == separationSlider) {
                separationWeight = value;
                separationValueLabel.setText(String.format("%.2f", separationWeight));
            }
        });
        return slider;
    }

    private void resetBoids() {
        boids.clear();
        for (int i = 0; i < NUM_BOIDS; i++) {
            boids.add(new Boid());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Boid boid : boids) {
            boid.update(boids, mousePosition, separationWeight, cohesionWeight, alignmentWeight, mouseForce, isFoodMode);
            boid.draw(g);
        }

        // Draw mouse pointer as food (green) or predator (red)
        g.setColor(isFoodMode ? Color.GREEN : Color.RED);
        g.fillOval(mousePosition.x - 10, mousePosition.y - 10, 20, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    class Boid {
        private Point position;
        private Point velocity;

        public Boid() {
            Random rand = new Random();
            position = new Point(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
            velocity = new Point(rand.nextInt(MAX_SPEED * 2) - MAX_SPEED, rand.nextInt(MAX_SPEED * 2) - MAX_SPEED);
        }

        public void update(ArrayList<Boid> boids, Point mouse, double separationWeight, double cohesionWeight, double alignmentWeight, double mouseForce, boolean isFoodMode) {
            Point alignment = scalePoint(align(boids), alignmentWeight);
            Point cohesion = scalePoint(cohere(boids), cohesionWeight);
            Point separation = scalePoint(separate(boids), separationWeight);

            Point mouseEffect = new Point(mouse.x - position.x, mouse.y - position.y);
            int distance = (int) Math.sqrt(mouseEffect.x * mouseEffect.x + mouseEffect.y * mouseEffect.y);
            if (distance > 0) {
                mouseEffect.x = (int) ((mouseEffect.x / (double) distance) * mouseForce * (isFoodMode ? 1 : -1));
                mouseEffect.y = (int) ((mouseEffect.y / (double) distance) * mouseForce * (isFoodMode ? 1 : -1));
            }

            velocity.translate(alignment.x + cohesion.x + separation.x + mouseEffect.x, alignment.y + cohesion.y + separation.y + mouseEffect.y);
            limitSpeed();
            position.translate(velocity.x, velocity.y);
            wrapAroundScreen();
        }
        private Point align(ArrayList<Boid> boids) {
            Point avgVelocity = new Point(0, 0);
            int count = 0;

            for (Boid other : boids) {
                if (other != this && distance(other) < NEIGHBOR_RADIUS) {
                    avgVelocity.translate(other.velocity.x, other.velocity.y);
                    count++;
                }
            }

            if (count > 0) {
                avgVelocity.x /= count;
                avgVelocity.y /= count;
                avgVelocity.translate(-velocity.x, -velocity.y);
            }
            return avgVelocity;
        }

        private Point cohere(ArrayList<Boid> boids) {
            Point center = new Point(0, 0);
            int count = 0;

            for (Boid other : boids) {
                if (other != this && distance(other) < NEIGHBOR_RADIUS) {
                    center.translate(other.position.x, other.position.y);
                    count++;
                }
            }

            if (count > 0) {
                center.x = (center.x / count) - position.x;
                center.y = (center.y / count) - position.y;
            }
            return center;
        }

        private Point separate(ArrayList<Boid> boids) {
            Point avoidance = new Point(0, 0);

            for (Boid other : boids) {
                if (other != this && distance(other) < BOID_RADIUS * 2) {
                    avoidance.translate(position.x - other.position.x, position.y - other.position.y);
                }
            }
            return avoidance;
        }

        private void limitSpeed() {
            int speed = (int) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y);
            if (speed > MAX_SPEED) {
                velocity.x = (velocity.x * MAX_SPEED) / speed;
                velocity.y = (velocity.y * MAX_SPEED) / speed;
            }
        }

        private void wrapAroundScreen() {
            if (position.x > WIDTH) position.x = 0;
            else if (position.x < 0) position.x = WIDTH;
            if (position.y > HEIGHT) position.y = 0;
            else if (position.y < 0) position.y = HEIGHT;
        }

        private int distance(Boid other) {
            return (int) position.distance(other.position);
        }
        public void draw(Graphics g) {
            // Calculate color based on togetherness
            g.setColor(Color.GREEN);
            g.fillOval(position.x - BOID_RADIUS, position.y - BOID_RADIUS, BOID_RADIUS * 2, BOID_RADIUS * 2);
        }
    }

    private Point scalePoint(Point point, double scale) {
        point.x *= scale;
        point.y *= scale;
        return point;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoidsSimulation::new);
        
        SwingUtilities.invokeLater(BoidsSimulation::new);
        SwingUtilities.invokeLater(BoidsSimulation::new);
        SwingUtilities.invokeLater(BoidsSimulation::new);
        SwingUtilities.invokeLater(BoidsSimulation::new);
    }
}

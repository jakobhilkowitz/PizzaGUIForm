import javax.swing.*;
import java.awt.*;

public class PizzaGUIFrame {
    private JFrame mainFrame;

    private JTextArea orderSummaryArea;
    private JScrollPane receiptScrollPane;

    private JPanel sizePanel;
    private JPanel crustPanel;
    private JPanel toppingsPanel; // this will have a titled boarder
    private JPanel receiptPanel; // where the order summary is displayed
    private JPanel buttonPanel;
    private JPanel formPanel; // Where the user puts their order selections

    private JRadioButton thinCrustButton;
    private JRadioButton regularCrustButton;
    private JRadioButton stuffedCrustButton;

    private ButtonGroup crustGroup;

    private JComboBox<String> sizeComboBox;

    private JCheckBox pepperoniCheckBox;
    private JCheckBox mushroomsCheckBox;
    private JCheckBox onionsCheckBox;
    private JCheckBox sausageCheckBox;
    private JCheckBox baconCheckBox;
    private JCheckBox extraCheeseCheckBox;

    private JButton orderButton;
    private JButton clearButton;
    private JButton quitButton;

    public PizzaGUIFrame() {
        mainFrame = new JFrame("Pizza Order Form");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 600);
        mainFrame.setLayout(new BorderLayout());
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(1, 3, 10, 10)); // 1 row, 3 columns, with gaps

        crustPanel = new JPanel();
        sizePanel = new JPanel();
        toppingsPanel = new JPanel();
        receiptPanel = new JPanel();
        buttonPanel = new JPanel();

        // Add titled boarder to crustPanel, sizePanel, and toppingsPanel
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust Type"));
        sizePanel.setBorder(BorderFactory.createTitledBorder("Pizza Size"));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings (+$1.00 each)"));
         // Set layout of each panel
        crustPanel.setLayout(new GridLayout(3,1));
        sizePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        toppingsPanel.setLayout(new GridLayout(3,2));

        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setPreferredSize(new Dimension(900, 180));

        buildCrustPanel(); // Call the helper method to build the crust selection panel
        buildSizePanel(); // Call the helper method to build the size selection panel
        buildToppingsPanel();
        buildReceiptPanel(); // Call the helper method to build the receipt panel
        buildButtonsPanel();

        // Add components to crustPanel
        formPanel.add(crustPanel);
        formPanel.add(sizePanel);
        formPanel.add(toppingsPanel);

        // Add panels to mainFrame
        mainFrame.add(formPanel, BorderLayout.NORTH);
        mainFrame.add(receiptPanel, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);


        mainFrame.setVisible(true);
    }

    /**
     * Helper method to build the crust selection panel with radio buttons for thin, regular, and stuffed crust options.
     */
    private void buildCrustPanel() {
        thinCrustButton = new JRadioButton("Thin Crust");
        regularCrustButton = new JRadioButton("Regular Crust");
        stuffedCrustButton = new JRadioButton("Stuffed Crust");
        crustGroup = new ButtonGroup();
        crustGroup.add(thinCrustButton);
        crustGroup.add(regularCrustButton);
        crustGroup.add(stuffedCrustButton);
        crustPanel.add(thinCrustButton);
        crustPanel.add(regularCrustButton);
        crustPanel.add(stuffedCrustButton);
    }

    /**
     * Creates an array of strings representing pizza sizes and initializes the sizeComboBox with these options,
     * allowing users to select their desired pizza size.
     */
    private void buildSizePanel() {
        String[] sizes = {"Select a Size","Small ($8.00)", "Medium ($12.00)", "Large ($16.00)", "Super ($20.00)"};
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);
    }

    /**
     * Helped method to build toppings Panel using JCheckBox components for each available topping, allowing users to
     * select multiple toppings for their pizza order. All topping cost an additional $1.00 each.
     */
    private void buildToppingsPanel() {
        pepperoniCheckBox = new JCheckBox("Pepperoni");
        mushroomsCheckBox = new JCheckBox("Mushrooms");
        onionsCheckBox = new JCheckBox("Onions");
        sausageCheckBox = new JCheckBox("Sausage");
        baconCheckBox = new JCheckBox("Bacon");
        extraCheeseCheckBox = new JCheckBox("Extra Cheese");

        toppingsPanel.add(pepperoniCheckBox);
        toppingsPanel.add(mushroomsCheckBox);
        toppingsPanel.add(onionsCheckBox);
        toppingsPanel.add(sausageCheckBox);
        toppingsPanel.add(baconCheckBox);
        toppingsPanel.add(extraCheeseCheckBox);
    }

    /**
     * Helper method to build the receipt panel.
     * This method initializes the orderSummaryArea as a JTextArea to display the order summary, and wraps it in a
     * JScrollPane (receiptScrollPane) to allow for scrolling if the content exceeds the visible area. The receipt panel
     * is then set up with a BorderLayout, and the scroll pane is added to the center of the panel.
     */
    private void buildReceiptPanel() {
        receiptPanel.setBorder(BorderFactory.createTitledBorder("Receipt"));
        orderSummaryArea = new JTextArea(20, 30);
        orderSummaryArea.setEditable(false); // Make the text area read-only
        receiptScrollPane = new JScrollPane(orderSummaryArea); // warp the text area in a scroll pane
        receiptPanel.setLayout(new BorderLayout());
        receiptPanel.add(receiptScrollPane, BorderLayout.CENTER);
    }

    private void buildButtonsPanel() {
        buttonPanel.setLayout (new FlowLayout(FlowLayout.CENTER, 20, 10)); // Center the buttons with horizontal and vertical gaps
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");
        orderButton.addActionListener(e -> processOrder());
        clearButton.addActionListener(e -> clearForm());
        quitButton.addActionListener(e -> askQuit());
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);
    }

    /**
     * Helper method to confirm quit action with the user. When the "Quit" button is clicked, this method displays a
     * confirmation dialog asking the user if they are sure they want to quit the application. If the user confirms by
     * selecting "Yes", the application will exit; otherwise, it will remain open. This provides a safeguard against
     * accidental closure of the application, ensuring that users do not lose their progress or order information
     * unintentionally.
     */
    private void askQuit() {
        int response = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to quit?", "Confirm Quit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Method to process the pizza order when the "Order" button is clicked. It first checks which crust type is selected and
     * stores it in the variable 'crust'. If no crust type is selected, it displays an error message prompting the user
     * to select a crust type and returns early from the method. The method will then continue to gather the selected
     * size and toppings, calculate the total cost, and generate an order summary to be displayed in the orderSummaryArea.
     * This method is responsible for validating user input and ensuring that all necessary selections are made before
     * processing the order.
     */
    private void processOrder() {
        // Validate crust selection
        String crust = "";
        if (thinCrustButton.isSelected()) {
            crust = "Thin Crust";
        } else if (regularCrustButton.isSelected()) {
            crust = "Regular Crust";
        } else if (stuffedCrustButton.isSelected()) {
            crust = "Stuffed Crust";
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Please select a crust type.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate pizza size selection
        int sizeIndex = sizeComboBox.getSelectedIndex();
        if (sizeComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(mainFrame, "Please select a pizza size.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get selected size and calculate base price
        String sizeText = (String) sizeComboBox.getSelectedItem();
        double basePrice = 0.0;
        if (sizeIndex == 1) {
            basePrice = 8.00;
        } else if (sizeIndex == 2) {
            basePrice = 12.00;
        } else if (sizeIndex == 3) {
            basePrice = 16.00;
        } else if (sizeIndex == 4) {
            basePrice = 20.00;
        }

        // Gather selected toppings
        int toppingCount = 0;
        if (pepperoniCheckBox.isSelected()) toppingCount++;
        if (mushroomsCheckBox.isSelected()) toppingCount++;
        if (onionsCheckBox.isSelected()) toppingCount++;
        if (sausageCheckBox.isSelected()) toppingCount++;
        if (baconCheckBox.isSelected()) toppingCount++;
        if (extraCheeseCheckBox.isSelected()) toppingCount++;
        // No topping validation, user can choose 0 or more toppings
        double toppingsCost = toppingCount * 1.00; // Each topping costs $1.00
        double subtotalCost = basePrice + toppingsCost;
        double tax = subtotalCost * 0.07; // Tax rate of 7%
        double totalCost = subtotalCost + tax;

        // Generate order summary
        StringBuilder orderSummary = new StringBuilder();
        orderSummary.append("=========================================\n");
        orderSummary.append(String.format("%-25s %10s\n", "Type of Crust & Size", "Price"));
        orderSummary.append("-----------------------------------------\n");
        orderSummary.append(String.format("%-25s %10s\n", crust, "$0.00"));
        orderSummary.append(String.format("%-25s %10s\n", sizeText, String.format("$%.2f", basePrice)));
        orderSummary.append("\n");
        orderSummary.append(String.format("%-25s %10s\n", "Ingredient", "Price"));
        orderSummary.append("-----------------------------------------\n");

        if (pepperoniCheckBox.isSelected()) orderSummary.append(String.format("%-25s %10s\n", "Pepperoni", "$1.00"));
        if (mushroomsCheckBox.isSelected()) orderSummary.append(String.format("%-25s %10s\n", "Mushrooms", "$1.00"));
        if (onionsCheckBox.isSelected()) orderSummary.append(String.format("%-25s %10s\n", "Onions", "$1.00"));
        if (sausageCheckBox.isSelected()) orderSummary.append(String.format("%-25s %10s\n", "Sausage", "$1.00"));
        if (baconCheckBox.isSelected()) orderSummary.append(String.format("%-25s %10s\n", "Bacon", "$1.00"));
        if (extraCheeseCheckBox.isSelected()) orderSummary.append(String.format("%-25s %10s\n", "Extra Cheese", "$1.00"));
        orderSummary.append("\n");
        orderSummary.append(String.format("%-25s %10s\n", "Subtotal", String.format("$%.2f", subtotalCost)));
        orderSummary.append(String.format("%-25s %10s\n", "Tax (7%)", String.format("$%.2f", tax)));
        orderSummary.append(String.format("%-25s %10s\n", "Total", String.format("$%.2f", totalCost)));
        orderSummary.append("=========================================\n");
        orderSummaryArea.setText(orderSummary.toString());
    }

    /**
     * Helper method to clear the form by resetting all selections to their default states, including clearing the
     * crust selection, resetting the size combo box to the first option, unchecking all topping checkboxes, and
     * clearing the order summary text area. This method is typically called when the user clicks the "Clear" button to
     * start a new order without any previous selections.
     */
    private void clearForm() {
        crustGroup.clearSelection();
        sizeComboBox.setSelectedIndex(0);
        pepperoniCheckBox.setSelected(false);
        mushroomsCheckBox.setSelected(false);
        onionsCheckBox.setSelected(false);
        sausageCheckBox.setSelected(false);
        baconCheckBox.setSelected(false);
        extraCheeseCheckBox.setSelected(false);
        orderSummaryArea.setText("");
    }
}

package jagarcia.store_inventory_management.view;

import jagarcia.store_inventory_management.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class ProductForm extends JFrame {

    ProductServiceImpl productService;
    @Autowired
    public ProductForm(ProductServiceImpl productService) {
        this.productService = productService;
        initForm();
    }
    private JPanel panel;
    private JTable tableProducts;
    private DefaultTableModel tableModelProducts;

    private void initForm() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900, 700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension sizeScreen = toolkit.getScreenSize();
        int x = (sizeScreen.width - getWidth()/ 2);
        int y = (sizeScreen.height - getHeight() /2);
        setLocation(x, y);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tableModelProducts = new DefaultTableModel(0,5);
        String[] headers = {"Id", "Product", "Category", "Price", "Stock"};
        this.tableModelProducts.setColumnIdentifiers(headers);
        //instanciar el objeto JTable
        this.tableProducts = new JTable(tableModelProducts);
    }
}

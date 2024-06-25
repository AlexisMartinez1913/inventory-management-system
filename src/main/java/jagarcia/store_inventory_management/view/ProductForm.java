package jagarcia.store_inventory_management.view;

import jagarcia.store_inventory_management.entity.Product;
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
        addButton.addActionListener( e -> addProduct());
    }



    private JPanel panel;
    private JTable tableProducts;
    private JTextField productText;
    private JTextField descriptionText;
    private JTextField priceProduct;
    private JTextField stockText;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
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

    private void addProduct() {

        if (productText.getText().isEmpty()) {
            showMessage("Enter the name of the product");
            productText.requestFocusInWindow();
            return;
        }
        //leer los valores del FORM
        var nameProuct = productText.getText();
        var description = descriptionText.getText();
        var price = Double.parseDouble(priceProduct.getText());
        var stock = Integer.parseInt(stockText.getText());

        //Crear Objeto Product y asignar
        var product = new Product();
        product.setNameProduct(nameProuct);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        this.productService.save(product);
        showMessage("Product successfully added!");
        cleanForm();
        listProducts();
    }

    private void cleanForm() {
        productText.setText("");
        descriptionText.setText("");
        priceProduct.setText("");
        stockText.setText("");
    }

    private void showMessage(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tableModelProducts = new DefaultTableModel(0,5);
        String[] headers = {"Id", "Product", "Category", "Price", "Stock"};
        this.tableModelProducts.setColumnIdentifiers(headers);
        //instanciar el objeto JTable
        this.tableProducts = new JTable(tableModelProducts);
        listProducts();
    }

    private void listProducts() {
        //limpiar la tabla
        tableModelProducts.setRowCount(0);
        // obtener los libros
        var products = productService.getAllProducts();
        products.forEach((product) -> {
            Object[] lineProduct = {
                    product.getIdProduct(),
                    product.getNameProduct(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock()
            };
            this.tableModelProducts.addRow(lineProduct);
        });

    }
}

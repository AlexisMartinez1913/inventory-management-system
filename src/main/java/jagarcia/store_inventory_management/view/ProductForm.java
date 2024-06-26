package jagarcia.store_inventory_management.view;

import jagarcia.store_inventory_management.entity.Product;
import jagarcia.store_inventory_management.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ProductForm extends JFrame {

    ProductServiceImpl productService;
    @Autowired
    public ProductForm(ProductServiceImpl productService) {
        this.productService = productService;
        initForm();
        addButton.addActionListener( e -> addProduct());
        tableProducts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadProductsSelected();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }



    private JPanel panel;
    private JTable tableProducts;
    private JTextField productText;
    private JTextField descriptionText;
    private JTextField priceProduct;
    private JTextField stockText;
    private JButton addButton;
    private JTextField idText;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton exitButton;
    private DefaultTableModel tableModelProducts;

    //Metodo para cargar el form
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
    //Metodo para agregar un producto
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

    //Metodo para ACTUALIZAR PRODUCTO
    private void updateProduct() {

        if (this.idText.getText().isEmpty()) {
            showMessage("You should be select a register");
        } else {
            if (productText.getText().isEmpty()) {
                showMessage("Enter the name of the product");
                productText.requestFocusInWindow();
            }
        }

        //leer los valores del FORM
        var idProduct = Integer.parseInt(idText.getText());
        var nameProuct = productText.getText();
        var description = descriptionText.getText();
        var price = Double.parseDouble(priceProduct.getText());
        var stock = Integer.parseInt(stockText.getText());
        //Crear Objeto Product y asignarlo
        var product = new Product();
        product.setIdProduct(idProduct);
        product.setNameProduct(nameProuct);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        this.productService.save(product);
        showMessage("Product updated successfully!");
        cleanForm();
        listProducts();
        addButton.setEnabled(true);



    }
    //Metodo para seleccionar los datos de la tabla y ponerlos en los textfield
    private void loadProductsSelected() {
        //indices de las columnas inician en cero
        var line = tableProducts.getSelectedRow();
        if (line != -1) { //-1 si no se selecciono ningun registro
            String idProduct = tableProducts.getModel().getValueAt(line, 0).toString();
            idText.setText(idProduct);
            String nameProduct = tableProducts.getModel().getValueAt(line, 1).toString();
            productText.setText(nameProduct);
            String description = tableProducts.getModel().getValueAt(line, 2).toString();
            descriptionText.setText(description);
            String price = tableProducts.getModel().getValueAt(line, 3).toString();
            priceProduct.setText(price);
            String stock = tableProducts.getModel().getValueAt(line, 4).toString();
            stockText.setText(stock);

            //inhabilitar boton de add para q no agregue uno repetido
            addButton.setEnabled(false);



        }

    }

    private void deleteProduct() {
        //validar si el user si selecciona el registro
        var line = tableProducts.getSelectedRow();
        if (line !=1) {
            String idProduct = tableProducts.getModel().getValueAt(line, 0).toString();
            var product = new Product();
            product.setIdProduct(Integer.parseInt(idProduct));
            productService.deleteProduct(product);
            showMessage("Product with " + idProduct + " succesfully" + "deleted!" );
            cleanForm();
            listProducts();
        } else {
            showMessage("You should be select a Product.");
        }


        cleanForm();
        listProducts();


    }
    // Metodo para Limpiar FORM
    private void cleanForm() {
        productText.setText("");
        descriptionText.setText("");
        priceProduct.setText("");
        stockText.setText("");
    }
    //Mostrar mensaje si se dejan datos vacios
    private void showMessage(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    //LISTAR LOS PRODUCTOS EN LA TABLA
    private void listProducts() {
        //limpiar la tabla
        tableModelProducts.setRowCount(0);
        // obtener los libros llamando al service
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


    //VISUALIZACION DE LA GUI
    private void createUIComponents() {
        // TODO: place custom component creation code here
        //Crear el elemento IdProduct de manera OCULTA en la GUI
        idText = new JTextField("");
        idText.setVisible(false);
        //TAMAÑO DE LA TABLA
        this.tableModelProducts = new DefaultTableModel(0,5) {
            //sobreescribir metodo para que el user no pueda editar los datos desde la table
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //CABECERA DE LA TABLA
        String[] headers = {"Id", "Product", "Category", "Price", "Stock"};
        this.tableModelProducts.setColumnIdentifiers(headers);
        //instanciar el objeto TIPO JTable
        this.tableProducts = new JTable(tableModelProducts);
        //Evitar q se seleccionen varios registros
        tableProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listProducts();
    }

}

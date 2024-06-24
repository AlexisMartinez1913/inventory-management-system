package jagarcia.store_inventory_management;

import jagarcia.store_inventory_management.view.ProductForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class StoreInventoryManagementApplication {

	public static void main(String[] args) {
		//configuracion de swing con spring
		ConfigurableApplicationContext contextSpring =
				new SpringApplicationBuilder(StoreInventoryManagementApplication.class)
						.headless(false)
						//NO ES APP WEB
						.web(WebApplicationType.NONE)
						.run(args);

		//llamar la instancia que carga el formulario
		EventQueue.invokeLater(() -> {
			//obtenemos el objeto form a tarves de spring
			ProductForm productForm = contextSpring.getBean(ProductForm.class);
			productForm.setVisible(true);
		});
	}

}

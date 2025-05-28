package com.jmcurso.cource.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jmcurso.cource.entities.Category;
import com.jmcurso.cource.entities.Order;
import com.jmcurso.cource.entities.OrderItem;
import com.jmcurso.cource.entities.Payment;
import com.jmcurso.cource.entities.Product;
import com.jmcurso.cource.entities.User;
import com.jmcurso.cource.entities.enums.OrderStatus;
import com.jmcurso.cource.entities.enums.PaymentMethod;
import com.jmcurso.cource.repositories.CategoryRepository;
import com.jmcurso.cource.repositories.OrderItemRepository;
import com.jmcurso.cource.repositories.OrderRepository;
import com.jmcurso.cource.repositories.ProductRepository;
import com.jmcurso.cource.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public void run(String... args) throws Exception {

		Category cat1 = new Category(null, "Tecnologia STAR Labs");
		Category cat2 = new Category(null, "Análises e Registros");
		Category cat3 = new Category(null, "Equipamentos de Viagem no Tempo");

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));

		Product p1 = new Product(null, "Traje do Flash",
		        "Uniforme usado por Barry Allen para correr em alta velocidade.", 4500.0, "");
		p1.setQuantityInStock(200);

		Product p2 = new Product(null, "Óculos de Vibração",
		        "Dispositivo criado por Cisco Ramon para acessar outras dimensões.", 1800.0, "");
		p2.setQuantityInStock(200);

		Product p3 = new Product(null, "Esfera Temporal",
		        "Dispositivo usado por Barry para viagens no tempo.", 25000.0, "");
		p3.setQuantityInStock(200);

		Product p4 = new Product(null, "Luva de Energia",
		        "Arma tecnológica desenvolvida por Cisco na Star Labs.", 3200.0, "");
		p4.setQuantityInStock(200);

		Product p5 = new Product(null, "Diário do Harrison Wells",
		        "Anotações científicas essenciais para a equipe Flash.", 250.0, "");
		p5.setQuantityInStock(200);

		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

		p1.getCategories().add(cat2);
		p2.getCategories().add(cat1);
		p2.getCategories().add(cat3);
		p3.getCategories().add(cat3);
		p4.getCategories().add(cat1);
		p5.getCategories().add(cat2);

		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

		User u1 = new User(null, "Barry Allen", "barry@starlabs.com", "988888888", "123456");
		User u2 = new User(null, "Cisco Ramon", "cisco@starlabs.com", "977777777", "123456");

		userRepository.saveAll(Arrays.asList(u1, u2));

		Order o1 = new Order(null, Instant.parse("2025-03-15T10:30:00Z"), OrderStatus.APROVADO_PELO_CISCO, u1);
		Order o2 = new Order(null, Instant.parse("2025-04-10T14:45:00Z"), OrderStatus.PROCESSANDO_EM_LABORATORIO, u2);
		Order o3 = new Order(null, Instant.parse("2025-05-05T08:20:00Z"), OrderStatus.PROCESSANDO_EM_LABORATORIO, u1);

		orderRepository.saveAll(Arrays.asList(o1, o2, o3));

		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());

		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

		Payment pay1 = new Payment();
		pay1.setMoment(Instant.parse("2025-03-15T12:30:00Z"));
		pay1.setOrder(o1);
		pay1.setPaymentMethod(PaymentMethod.CREDITOS_TEMPORAIS);

		o1.setPayment(pay1);

		orderRepository.save(o1);
	}
}

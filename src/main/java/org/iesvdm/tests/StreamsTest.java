package org.iesvdm.tests;

import org.hibernate.annotations.ParamDef;
import org.iesvdm.streams.*;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.fail;

class StreamsTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}


	@Test
	void testSkeletonCliente() {
	
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			list.forEach(System.out::println);
		
			
			//TODO STREAMS
			
		
			cliHome.commitTransaction();
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	

	@Test
	void testSkeletonComercial() {
	
		ComercialHome comHome = new ComercialHome();	
		try {
			comHome.beginTransaction();
		
			List<Comercial> list = comHome.findAll();		
			list.forEach(System.out::println);		
			//TODO STREAMS
		
			comHome.commitTransaction();
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	@Test
	void testSkeletonPedido() {
	
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
			list.forEach(System.out::println);	
						
			//TODO STREAMS
		
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	/**
	 * 1. Devuelve un listado de todos los pedidos que se realizaron durante el año 2017, 
	 * cuya cantidad total sea superior a 500€.
	 * @throws ParseException 
	 */
	@Test
	void test1() throws ParseException {
		
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
			
			//PISTA: Generación por sdf de fechas
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date ultimoDia2016 = sdf.parse("2016-12-31");
			Date primerDia2018 = sdf.parse("2018-01-01");


			List<Pedido> list = pedHome.findAll();
				
			//TODO STREAMS

			List <String> pedidos2017 = list.stream()
					.filter(pedido -> pedido.getFecha().after(ultimoDia2016) && pedido.getFecha().before(primerDia2018) && pedido.getTotal() > 500)
					.map(pedido -> "ID " + pedido.getId() + "TOTAL " + pedido.getTotal() + " Fecha " + pedido.getFecha())
					.collect(toList());

			pedidos2017.forEach(System.out::println);
						
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 2. Devuelve un listado con los identificadores de los clientes que NO han realizado algún pedido. 
	 * 
	 */
	@Test
	void test2() {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			
			//TODO STREAMS
			List <String> listaID = list.stream()
							.filter(cliente -> cliente.getPedidos().isEmpty())
									.map(cliente -> "ID: " + cliente.getId())
											.collect(toList());

			listaID.forEach(System.out::println);
		
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 3. Devuelve el valor de la comisión de mayor valor que existe en la tabla comercial
	 */
	@Test
	void test3() {
		
		ComercialHome comHome = new ComercialHome();	
		try {
			comHome.beginTransaction();
		
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS
			Float comisionMaxima;
			List<Comercial> ComercialcomisionMaxima = list.stream()
							.sorted(Comparator.comparing(Comercial::getComisión).reversed())
									.limit(1).collect(toList());

			comisionMaxima = ComercialcomisionMaxima.get(0).getComisión();
			System.out.println(comisionMaxima);


				
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 4. Devuelve el identificador, nombre y primer apellido de aquellos clientes cuyo segundo apellido no es NULL. 
	 * El listado deberá estar ordenado alfabéticamente por apellidos y nombre.
	 */
	@Test
	void test4() {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			
			//TODO STREAMS
			List <String> clientes = list.stream()
							.filter(cliente -> !(cliente.getApellido2() != null))
					.sorted(Comparator.comparing(( Cliente cliente) -> cliente.getApellido1()).thenComparing(cliente -> cliente.getNombre()))
							.map(cliente -> "ID" + cliente.getId() + "Nombre: " + cliente.getNombre() + "Apellido: " + cliente.getApellido1()).collect(toList());
			clientes.forEach(System.out::println);
			
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 5. Devuelve un listado con los nombres de los comerciales que terminan por "el" o "o". 
	 *  Tenga en cuenta que se deberán eliminar los nombres repetidos.
	 */
	@Test
	void test5() {
		
		ComercialHome comHome = new ComercialHome();	
		try {
			comHome.beginTransaction();
		
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS
			List<String> listaNombres = list.stream()
							.filter(comercial -> comercial.getNombre().endsWith("el") || comercial.getNombre().endsWith("o"))
											.map(comercial -> "Nombre: " + comercial.getNombre() )
												.distinct().collect(toList());
			listaNombres.forEach(System.out::println);
			
			comHome.commitTransaction();
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 6. Devuelve un listado de todos los clientes que realizaron un pedido durante el año 2017, cuya cantidad esté entre 300 € y 1000 €.
	 */
	@Test
	void test6() {
	
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date ultimoDia2016 = sdf.parse("2016-12-31");
			Date primerDia2018 = sdf.parse("2018-01-01");
						
			//TODO STREAMS
			List<Cliente> clientes = list.stream()
							.filter(pedido -> pedido.getFecha().after(ultimoDia2016) && pedido.getFecha().before(primerDia2018) && pedido.getTotal()>300 && pedido.getTotal() < 1000)
									.map(pedido -> pedido.getCliente()).collect(toList());

			clientes.forEach(System.out::println);
		
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		} catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
	
	
	/**
	 * 7. Calcula la media del campo total de todos los pedidos realizados por el comercial Daniel Sáez
	 */
	@Test
	void test7() {
		
		ComercialHome comHome = new ComercialHome();	
		//PedidoHome pedHome = new PedidoHome();	
		try {
			//pedHome.beginTransaction();
			comHome.beginTransaction();
			
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS
			List <Comercial> comercialDaniel = list.stream()
							.filter(comercial -> comercial.getNombre().equalsIgnoreCase("Daniel") && comercial.getApellido1().equalsIgnoreCase("saez")).collect(toList());
			//Double mediaPedidos = (comercialDaniel.get(0).getPedidos().stream().collect(averagingDouble(Pedido::getTotal)));
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 8. Devuelve un listado con todos los pedidos que se han realizado.
	 *  Los pedidos deben estar ordenados por la fecha de realización
	 * , mostrando en primer lugar los pedidos más recientes
	 */
	@Test
	void test8() {
	
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			//TODO STREAMS

			List<String> pedidos = list.stream()
							.sorted(Comparator.comparing(Pedido::getFecha))
					.map(pedido -> pedido.getFecha() + " " + pedido.getId())
									.collect(toList());
			pedidos.forEach(System.out::println);
			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 9. Devuelve todos los datos de los dos pedidos de mayor valor.
	 */
	@Test
	void test9() {
	
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			//TODO STREAMS
			List<String> pedidos = list.stream()
							.sorted(Comparator.comparing(Pedido::getTotal).reversed())
									.limit(2)
											.map(pedido -> "id:" + pedido.getId() + " fecha:" + pedido.getFecha() + " Total" + pedido.getTotal() + " Cliente" + pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido1()).collect(toList());
			pedidos.forEach(System.out::println);
			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 10. Devuelve un listado con los identificadores de los clientes que han realizado algún pedido. 
	 * Tenga en cuenta que no debe mostrar identificadores que estén repetidos.
	 */
	@Test
	void test10() {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			
			//TODO STREAMS
			List<String> listaid = list.stream()
							.map(pedido -> pedido.getCliente())
									.distinct()
											.map(cliente -> "ID: " + cliente.getId())
													.collect(toList());
			listaid.forEach(System.out::println);
			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 11. Devuelve un listado con el nombre y los apellidos de los comerciales que tienen una comisión entre 0.05 y 0.11.
	 * 
	 */
	@Test
	void test11() {
		
		ComercialHome comHome = new ComercialHome();	
		//PedidoHome pedHome = new PedidoHome();	
		try {
			//pedHome.beginTransaction();
			comHome.beginTransaction();
			
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS
			List<String> comercialesComision = list.stream()
							.filter(comercial -> comercial.getComisión() > 0.05 && comercial.getComisión() < 0.11)
									.map(comercial -> comercial.getNombre() + " " + comercial.getApellido1() + " " + comercial.getApellido2()).collect(toList());
			comercialesComision.forEach(System.out::println);
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 12. Devuelve el valor de la comisión de menor valor que existe para los comerciales.
	 * 
	 */
	@Test
	void test12() {
		
		ComercialHome comHome = new ComercialHome();	
		//PedidoHome pedHome = new PedidoHome();	
		try {
			//pedHome.beginTransaction();
			comHome.beginTransaction();
			
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS

			List<Comercial> comisionMenorLista = list.stream()
							.sorted(Comparator.comparing(comercial -> comercial.getComisión()))
									.limit(1).collect(toList());
			float comisionMenor = comisionMenorLista.get(0).getComisión();
			System.out.println(comisionMenor);
			
			
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 13. Devuelve un listado de los nombres de los clientes que 
	 * empiezan por A y terminan por n y también los nombres que empiezan por P. 
	 * El listado deberá estar ordenado alfabéticamente.
	 * 
	 */
	@Test
	void test13() {
		
		ComercialHome comHome = new ComercialHome();	
		//PedidoHome pedHome = new PedidoHome();	
		try {
			//pedHome.beginTransaction();
			comHome.beginTransaction();
			
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS

			List<Pedido> listaDePedidos = (List<Pedido>) list.stream()
					.distinct()
					.flatMap(comercial -> comercial.getPedidos().stream())
					.collect(toList());

			List<String> listaDeNombres = (List<String>) listaDePedidos.stream()
					.filter(pedido -> (pedido.getCliente().getNombre().startsWith("A") && pedido.getCliente().getNombre().endsWith("n")) || pedido.getCliente().getNombre().startsWith("P"))
					.sorted(Comparator.comparing((Pedido pedido) -> pedido.getCliente().getNombre()))
					.map(pedido -> pedido.getCliente().getNombre())
							.collect(toList());

			listaDeNombres.forEach(System.out::println);


			
			
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 14. Devuelve un listado de los nombres de los clientes 
	 * que empiezan por A y terminan por n y también los nombres que empiezan por P. 
	 * El listado deberá estar ordenado alfabéticamente.
	 */
	@Test
	void test14() {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			
			//TODO STREAMS
			List<String> nombres = list.stream()
							.filter(cliente -> (cliente.getNombre().startsWith("A") && cliente.getNombre().endsWith("n")) || cliente.getNombre().startsWith("P"))
									.map(cliente -> cliente.getNombre())
											.collect(toList());
			nombres.forEach(System.out::println);
			
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 15. Devuelve un listado de los clientes cuyo nombre no empieza por A. 
	 * El listado deberá estar ordenado alfabéticamente por nombre y apellidos.
	 */
	@Test
	void test15() {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			
			//TODO STREAMS
			List<String> nombres = list.stream()
							.filter(cliente -> !cliente.getNombre().startsWith("A"))
									.sorted(Comparator.comparing((Cliente cliente) -> cliente.getNombre()).thenComparing(cliente -> cliente.getApellido1()))
											.map(cliente -> cliente.getNombre() + " " + cliente.getApellido1())
													.collect(toList());
			nombres.forEach(System.out::println);
			
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 16. Devuelve un listado con el identificador, nombre y los apellidos de todos 
	 * los clientes que han realizado algún pedido. 
	 * El listado debe estar ordenado alfabéticamente por apellidos y nombre y se deben eliminar los elementos repetidos.
	 */
	@Test
	void test16() {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			
			//TODO STREAMS
			List<String> clientes = list.stream()
							.sorted(Comparator.comparing((Pedido pedido) -> pedido.getCliente().getApellido1()).thenComparing(pedido -> pedido.getCliente().getNombre()))
									.map(pedido -> pedido.getCliente().getApellido1() + ", " + pedido.getCliente().getNombre())
											.distinct()
													.collect(toList());

			clientes.forEach(System.out::println);
			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 17. Devuelve un listado que muestre todos los pedidos que ha realizado cada cliente. 
	 * El resultado debe mostrar todos los datos del cliente primero junto con un sublistado de sus pedidos. 
	 * El listado debe mostrar los datos de los clientes ordenados alfabéticamente por nombre y apellidos.
	 * 
Cliente [id=1, nombre=Aar�n, apellido1=Rivero, apellido2=G�mez, ciudad=Almer�a, categor�a=100]
	Pedido [id=2, cliente=Cliente [id=1, nombre=Aar�n, apellido1=Rivero, apellido2=G�mez, ciudad=Almer�a, categor�a=100], comercial=Comercial [id=5, nombre=Antonio, apellido1=Carretero, apellido2=Ortega, comisi�n=0.12], total=270.65, fecha=2016-09-10]
	Pedido [id=16, cliente=Cliente [id=1, nombre=Aar�n, apellido1=Rivero, apellido2=G�mez, ciudad=Almer�a, categor�a=100], comercial=Comercial [id=5, nombre=Antonio, apellido1=Carretero, apellido2=Ortega, comisi�n=0.12], total=2389.23, fecha=2019-03-11]
	Pedido [id=15, cliente=Cliente [id=1, nombre=Aar�n, apellido1=Rivero, apellido2=G�mez, ciudad=Almer�a, categor�a=100], comercial=Comercial [id=5, nombre=Antonio, apellido1=Carretero, apellido2=Ortega, comisi�n=0.12], total=370.85, fecha=2019-03-11]
Cliente [id=2, nombre=Adela, apellido1=Salas, apellido2=D�az, ciudad=Granada, categor�a=200]
	Pedido [id=12, cliente=Cliente [id=2, nombre=Adela, apellido1=Salas, apellido2=D�az, ciudad=Granada, categor�a=200], comercial=Comercial [id=1, nombre=Daniel, apellido1=S�ez, apellido2=Vega, comisi�n=0.15], total=3045.6, fecha=2017-04-25]
	Pedido [id=7, cliente=Cliente [id=2, nombre=Adela, apellido1=Salas, apellido2=D�az, ciudad=Granada, categor�a=200], comercial=Comercial [id=1, nombre=Daniel, apellido1=S�ez, apellido2=Vega, comisi�n=0.15], total=5760.0, fecha=2015-09-10]
	Pedido [id=3, cliente=Cliente [id=2, nombre=Adela, apellido1=Salas, apellido2=D�az, ciudad=Granada, categor�a=200], comercial=Comercial [id=1, nombre=Daniel, apellido1=S�ez, apellido2=Vega, comisi�n=0.15], total=65.26, fecha=2017-10-05]
	...
	 */
	@Test
	void test17() {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			
			//TODO STREAMS
			
			
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 18. Devuelve un listado que muestre todos los pedidos en los que ha participado un comercial. 
	 * El resultado debe mostrar todos los datos de los comerciales y el sublistado de pedidos. 
	 * El listado debe mostrar los datos de los comerciales ordenados alfabéticamente por apellidos.
	 */
	@Test
	void test18() {
		
		ComercialHome comHome = new ComercialHome();	
		try {
		
			comHome.beginTransaction();
			
			List<Comercial> list = comHome.findAll();		
		
			
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 19. Devuelve el nombre y los apellidos de todos los comerciales que ha participado 
	 * en algún pedido realizado por María Santana Moreno.
	 */
	@Test
	void test19() {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			
			//TODO STREAMS
			List <String> comerciales = list.stream()
							.filter(pedido -> pedido.getCliente().getNombre().equalsIgnoreCase("maría") && pedido.getCliente().getApellido1().equalsIgnoreCase("santana") && pedido.getCliente().getApellido2().equalsIgnoreCase("moreno"))
									.map(pedido -> pedido.getComercial().getNombre() + " " + pedido.getComercial().getApellido1() + " " + pedido.getComercial().getApellido2())
											.collect(toList());

			comerciales.forEach(System.out::println);
			
			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	

	/**
	 * 20. Devuelve un listado que solamente muestre los comerciales que no han realizado ningún pedido.
	 */
	@Test
	void test20() {
		
		ComercialHome comHome = new ComercialHome();	
		try {
		
			comHome.beginTransaction();
			
			List<Comercial> list = comHome.findAll();		
		
			//TODO STREAMS
			
			
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 21. Calcula el número total de comerciales distintos que aparecen en la tabla pedido
	 */
	@Test
	void test21() {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			//TODO STREAMS
			
			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 22. Calcula el máximo y el mínimo de total de pedido en un solo stream, transforma el pedido a un array de 2 double total, utiliza reduce junto con el array de double para calcular ambos valores.
	 */
	@Test
	void test22() {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			//TODO STREAMS
			

			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	
	/**
	 * 23. Calcula cuál es el valor máximo de categoría para cada una de las ciudades que aparece en cliente
	 */
	@Test
	void test23() {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			
			//TODO STREAMS
			
			
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 24. Calcula cuál es el máximo valor de los pedidos realizados 
	 * durante el mismo día para cada uno de los clientes. Es decir, el mismo cliente puede haber 
	 * realizado varios pedidos de diferentes cantidades el mismo día. Se pide que se calcule cuál es 
	 * el pedido de máximo valor para cada uno de los días en los que un cliente ha realizado un pedido. 
	 * Muestra el identificador del cliente, nombre, apellidos, la fecha y el valor de la cantidad.
	 * Pista: utiliza collect, groupingBy, maxBy y comparingDouble métodos estáticos de la clase Collectors
	 */
	@Test
	void test24() {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			//TODO STREAMS
			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 *  25. Calcula cuál es el máximo valor de los pedidos realizados durante el mismo día para cada uno de los clientes, 
	 *  teniendo en cuenta que sólo queremos mostrar aquellos pedidos que superen la cantidad de 2000 €.
	 *  Pista: utiliza collect, groupingBy, filtering, maxBy y comparingDouble métodos estáticos de la clase Collectors
	 */
	@Test
	void test25() {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
					
			//TODO STREAMS
			
			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 *  26. Devuelve un listado con el identificador de cliente, nombre y apellidos 
	 *  y el número total de pedidos que ha realizado cada uno de clientes durante el año 2017.
	 * @throws ParseException 
	 */
	@Test
	void test26() throws ParseException {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			//PISTA: Generación por sdf de fechas
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date ultimoDia2016 = sdf.parse("2016-12-31");
			Date primerDia2018 = sdf.parse("2018-01-01");
			
			//TODO STREAMS
			
			
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 27. Devuelve cuál ha sido el pedido de máximo valor que se ha realizado cada año. El listado debe mostrarse ordenado por año.
	 */
	@Test
	void test27() {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
					
			Calendar calendar = Calendar.getInstance();
			
			
			//TODO STREAMS
					
					
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	
	/**
	 *  28. Devuelve el número total de pedidos que se han realizado cada año.
	 */
	@Test
	void test28() {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
					
			Calendar calendar = Calendar.getInstance();
			
			//TODO STREAMS
					
							
					
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 *  29. Devuelve los datos del cliente que realizó el pedido
	 *  
	 *   más caro en el año 2019.
	 * @throws ParseException 
	 */
	@Test
	void test29() throws ParseException {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
					
			//PISTA: Generación por sdf de fechas
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date ultimoDia2018 = sdf.parse("2018-12-31");
			Date primerDia2020 = sdf.parse("2020-01-01");
			
			//TODO STREAMS
			
				
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	
	/**
	 *  30. Calcula la estadísticas de total de todos los pedidos.
	 *  Pista: utiliza collect con summarizingDouble
	 */
	@Test
	void test30() throws ParseException {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
			
			//TODO STREAMS
				
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
}

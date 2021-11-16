-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-11-2021 a las 12:02:49
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tienda_saul_refoyo_ferrero`
--
CREATE DATABASE IF NOT EXISTS `tienda_saul_refoyo_ferrero` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tienda_saul_refoyo_ferrero`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `descripcion`, `nombre`) VALUES
(1, 'Género en el que el jugador debe usar su velocidad, destreza y tiempo de reacción.', 'Accion'),
(2, 'Género de videojuegos, caracterizados por la investigación, exploración, la solución de rompecabezas, la interacción con personajes del videojuego, y un enfoque en el relato en vez de desafíos basados en reflejos .', 'Aventuras'),
(3, ' Género de videojuegos que se caracterizan por tener que caminar, correr, saltar o escalar sobre una serie de plataformas y acantilados.', 'Plataformas'),
(4, 'Género que combina elementos del género aventura con elementos del género acción.', 'Acción-Aventura');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `configuracion`
--

DROP TABLE IF EXISTS `configuracion`;
CREATE TABLE `configuracion` (
  `id` int(11) NOT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `valor` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `configuracion`
--

INSERT INTO `configuracion` (`id`, `clave`, `tipo`, `valor`) VALUES
(1, 'direccion', 'texto', 'Calle La Mentira'),
(2, 'cif', 'numero', '4939'),
(3, 'nombreTienda', 'texto', 'Universal Games'),
(4, 'pais', 'texto', 'España'),
(5, 'numFactura', 'numero', '0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalles_pedido`
--

DROP TABLE IF EXISTS `detalles_pedido`;
CREATE TABLE `detalles_pedido` (
  `id` int(11) NOT NULL,
  `id_pedido` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `impuesto` float DEFAULT NULL,
  `precio_unidad` float DEFAULT NULL,
  `total` double DEFAULT NULL,
  `unidades` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metodos_pago`
--

DROP TABLE IF EXISTS `metodos_pago`;
CREATE TABLE `metodos_pago` (
  `id` int(11) NOT NULL,
  `metodo_pago` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `metodos_pago`
--

INSERT INTO `metodos_pago` (`id`, `metodo_pago`) VALUES
(1, 'tarjeta'),
(2, 'paypal');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `opciones_menu`
--

DROP TABLE IF EXISTS `opciones_menu`;
CREATE TABLE `opciones_menu` (
  `id` int(11) NOT NULL,
  `grupo_opcion` varchar(255) DEFAULT NULL,
  `id_rol` int(11) DEFAULT NULL,
  `nombre_opcion` varchar(255) DEFAULT NULL,
  `url_opcion` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `opciones_menu`
--

INSERT INTO `opciones_menu` (`id`, `grupo_opcion`, `id_rol`, `nombre_opcion`, `url_opcion`) VALUES
(1, 'Pedidos', 1, 'Listado', 'pedidos/pedido'),
(2, 'Pedidos', 1, 'Cancelar', 'pedidos/cancelar'),
(3, 'Pedidos', 1, 'Confirmar', 'pedidos/procesar'),
(4, 'Productos', 1, 'Catalogo', 'productos/'),
(5, 'Usuarios', 1, 'Listado Clientes', 'usuarios/clientes'),
(6, 'Usuarios', 1, 'Listado Empleados', 'usuarios/empleados'),
(7, 'Usuarios', 1, 'Crear Empleado', 'usuarios/form/empleado'),
(8, 'Usuarios', 1, 'Crear Cliente', 'usuarios/form/cliente'),
(9, 'Productos', 1, 'Lista', 'productos/lista'),
(10, 'Usuarios', 1, 'Perfil', 'usuarios/perfil'),
(11, 'Pedidos', 2, 'Listado', 'pedidos/pedido'),
(12, 'Pedidos', 2, 'Confirmar', 'pedidos/procesar'),
(13, 'Productos', 2, 'Catalogo', 'productos/'),
(14, 'Usuarios', 2, 'Listado Clientes', 'usuarios/clientes'),
(15, 'Usuarios', 2, 'Crear Cliente', 'usuarios/form/cliente'),
(16, 'Productos', 2, 'Lista', 'productos/lista'),
(17, 'Usuarios', 2, 'Perfil', 'usuarios/perfil'),
(18, 'Pedidos', 3, 'Listado', 'pedidos/pedido'),
(19, 'Productos', 3, 'Catalogo', 'productos/'),
(20, 'Usuarios', 3, 'Perfil', 'usuarios/perfil'),
(21, 'Productos', 4, 'Catalogo', 'productos/'),
(22, 'Productos', 1, 'Nuevo Producto', 'productos/form'),
(23, 'Productos', 2, 'Nuevo Producto', 'productos/form');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
CREATE TABLE `pedidos` (
  `id` int(11) NOT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `fecha` datetime NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `metodo_pago` varchar(255) DEFAULT NULL,
  `num_factura` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

DROP TABLE IF EXISTS `productos`;
CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_alta` datetime NOT NULL,
  `fecha_baja` datetime NOT NULL,
  `id_categoria` int(11) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `impuesto` float DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `stock` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `descripcion`, `fecha_alta`, `fecha_baja`, `id_categoria`, `imagen`, `impuesto`, `nombre`, `precio`, `stock`) VALUES
(1, 'Únete a la cazarrecompensas Samus Aran mientras escapa de un mortífero planeta alienígena infestado de amenazas mecánicas.', '2021-09-12 00:30:40', '2021-09-12 00:30:40', 4, 'dread.jpg', 21, 'Metroid Dread', 60, 60),
(2, 'Explora cavernas tortuosas, ciudades antiguas y páramos mortales. Combate contra criaturas corrompidas, haz amistad con extraños insectos y resuelve los antiguos misterios que yacen en el corazón de reino.', '2019-07-03 15:50:40', '2019-07-03 15:50:40', 4, 'hollow.png', 21, 'Hollow Knight', 50, 30),
(3, 'Ponte en la piel de un curtido experimentado y maduro Peter Parker y consigue salvar Manhattan de criminales y villanos todo esto compaginándolo con su vida personal. ', '2018-09-15 17:00:20', '2018-09-15 17:00:20', 1, 'spider.jpg', 21, 'Marvel’s Spider-Man', 30, 80),
(4, 'Kratos, que ha dejado atrás el mundo de los dioses, deberá adaptarse a tierras que no le son familiares, afrontar peligros inesperados y aprovechar una segunda oportunidad de ejercer como padre. ', '2018-09-28 18:10:20', '2018-09-28 18:10:20', 1, 'god.gif', 21, 'God of War', 30, 60),
(5, 'Han pasado varios años en Devil May Cry 5 y la amenaza del poder demoníaco, desde hace mucho tiempo olvidado, ha vuelto a amenazar al mundo una vez más.', '2019-03-08 14:10:42', '2019-03-08 14:10:42', 1, 'dmc.jpg', 21, 'Devil May Cry 5', 40, 33),
(6, 'Cuando unos agentes federales amenazan a su familia, el exforajido John Marston recorre la frontera americana para ayudar a imponer la ley. ', '2010-05-21 19:13:49', '2010-05-21 19:13:49', 2, 'reddead.jpg', 21, 'Red Dead Redemption', 10, 8),
(7, 'Assasin’s Creed coloca al jugador en el papel de Altair, un presunto miembro de un despiadado grupo de asesinos.', '2007-09-13 14:57:26', '2007-09-13 14:57:26', 2, 'assassin.jpg', 21, 'Assassin\'s Creed', 5, 10),
(8, 'Explora enormes reinos 3D llenos de secretos y sorpresas, incluidos atuendos para Mario y montones de maneras diferentes de interactuar con el entorno.', '2017-10-27 14:57:26', '2017-10-27 14:57:26', 3, 'mario.jpg', 21, 'Super Mario Odyssey', 40, 40),
(9, ' Crash se lanza de lleno con tus marsupiales favoritos a una aventura temporal que se cae a pedazos.', '2020-09-16 17:37:16', '2020-09-16 17:37:16', 3, 'crash.jpg', 21, 'Crash Bandicoot 4: It\'s About Time', 50, 60),
(10, 'Cuphead es un juego de acción clásico que se centra en combates contra  jefes.', '2017-09-29 13:16:48', '2017-09-29 13:16:48', 3, 'cup.jpg', 21, 'Cuphead', 20, 70);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `rol` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `rol`) VALUES
(1, 'Administrador'),
(2, 'Empleado'),
(3, 'Cliente'),
(4, 'Anonimo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `apellido1` varchar(255) DEFAULT NULL,
  `apellido2` varchar(255) DEFAULT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `id_rol` int(11) DEFAULT NULL,
  `localidad` varchar(255) DEFAULT NULL,
  `nombre` varchar(16) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `apellido1`, `apellido2`, `clave`, `direccion`, `dni`, `email`, `id_rol`, `localidad`, `nombre`, `provincia`, `telefono`) VALUES
(1, NULL, NULL, 'MTIzNA==', NULL, NULL, 'admin@admin.com', 1, NULL, 'Admin', NULL, NULL),
(2, 'Garcia', 'Marquez', 'MTIzNA==', 'Llano Ponte', '65638534N', 'ramon@correo.com', 2, 'Aviles', 'Ramón', 'Aviles', '648542876'),
(3, 'Lopez', 'Labrador', 'MTIzNA==', 'Conde Ponte', '86465534N', 'mario@correo.com', 2, 'Gijon', 'Mario', 'Gijon', '866482876'),
(4, 'Perez', 'Ferrero', 'MTIzNA==', 'San Miguel', '63859834N', 'marta@correo.com', 2, 'Madrid', 'Marta', 'Madrid', '648875428'),
(5, 'Zapatero', 'Castaño', 'MTIzNA==', 'Alfonso XX', '97466385N', 'manuel@correo.com', 3, 'Murcia', 'Manuel', 'Cartagena', '985648428'),
(6, 'Valle', 'Puertas', 'MTIzNA==', 'San Vitero', '76859834N', 'rodri@correo.com', 3, 'Madrid', 'Rodriguez', 'Madrid', '648435428');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `valoraciones`
--

DROP TABLE IF EXISTS `valoraciones`;
CREATE TABLE `valoraciones` (
  `id` int(11) NOT NULL,
  `comentario` varchar(255) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `valoracion` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `configuracion`
--
ALTER TABLE `configuracion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalles_pedido`
--
ALTER TABLE `detalles_pedido`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `metodos_pago`
--
ALTER TABLE `metodos_pago`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `opciones_menu`
--
ALTER TABLE `opciones_menu`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `valoraciones`
--
ALTER TABLE `valoraciones`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `configuracion`
--
ALTER TABLE `configuracion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `detalles_pedido`
--
ALTER TABLE `detalles_pedido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `metodos_pago`
--
ALTER TABLE `metodos_pago`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `opciones_menu`
--
ALTER TABLE `opciones_menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `valoraciones`
--
ALTER TABLE `valoraciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-11-2020 a las 05:05:10
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `estacionamiento`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cajones`
--

CREATE TABLE `cajones` (
  `Folio` int(11) NOT NULL,
  `Placas` varchar(10) NOT NULL,
  `idEmpleado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cajones`
--

INSERT INTO `cajones` (`Folio`, `Placas`, `idEmpleado`) VALUES
(1, 'ASDF123', 2),
(2, 'TREW123', 2),
(3, 'GFDS123', 2),
(4, 'ASDFG12', 4),
(5, 'QWER123', 4),
(6, 'WRT123', 5),
(7, 'GHJK123', 6),
(8, 'GFDD123', 7),
(9, 'DFSG123', 7),
(10, 'TRU123', 5),
(11, 'HGFD123', 3),
(12, 'GHF1234', 2),
(13, 'AAA123', 2),
(14, 'BBB123', 2),
(15, 'CCC123', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajador`
--

CREATE TABLE `trabajador` (
  `idEmpleado` int(5) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Puesto` varchar(30) NOT NULL,
  `Contacto` varchar(10) NOT NULL,
  `Salario` float NOT NULL,
  `NivelDeAcceso` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `trabajador`
--

INSERT INTO `trabajador` (`idEmpleado`, `Nombre`, `Puesto`, `Contacto`, `Salario`, `NivelDeAcceso`) VALUES
(1, 'Daniel Ruiz Cabrera', 'Administrador', '3421585515', 10500, 1),
(2, 'Jose Garcia Rodriguez', 'Acomodador', '5421251454', 2500, 2),
(3, 'Juan Rodriguez Jimenez', 'Acomodador', '5428639852', 2500, 2),
(4, 'Pedro Hernandez Garcia', 'Acomodador', '3418529635', 2500, 2),
(5, 'Rosa Elena Manzo Deniz', 'Acomodador', '3421058854', 2500, 2),
(6, 'Javier Correa Ambrosio', 'Acomodador', '5584621546', 2500, 2),
(7, 'Mario Gonzales Fuentes', 'Acomodador', '1234567890', 3000, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE `vehiculo` (
  `Marca` varchar(30) NOT NULL,
  `Modelo` varchar(30) NOT NULL,
  `Color` varchar(15) NOT NULL,
  `Placas` varchar(15) NOT NULL,
  `CondicionesVehiculo` varchar(50) NOT NULL,
  `Cajon` int(2) NOT NULL,
  `Nivel` int(1) NOT NULL,
  `Acomodador` int(11) NOT NULL,
  `HoraEntrada` datetime NOT NULL DEFAULT current_timestamp(),
  `HoraSalida` datetime DEFAULT NULL,
  `Costo` float DEFAULT NULL,
  `Estado` varchar(10) NOT NULL,
  `Valor` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`Marca`, `Modelo`, `Color`, `Placas`, `CondicionesVehiculo`, `Cajon`, `Nivel`, `Acomodador`, `HoraEntrada`, `HoraSalida`, `Costo`, `Estado`, `Valor`) VALUES
('Nissan', 'Sentra', 'Blanco', 'AAA123', 'Buena', 1, 5, 3, '2020-11-25 14:15:58', '2020-11-25 14:16:59', 45, 'Inactivo', 1),
('VW', 'Jetta', 'Negro', 'ASDF123', 'Ninguna', 1, 1, 2, '2020-11-11 18:01:15', '2020-11-11 18:24:48', 45, 'Inactivo', 1),
('Honda', 'Accord', 'Negro', 'ASDFG12', 'Ninguna', 1, 3, 4, '2020-11-11 19:28:46', '2020-11-25 14:29:46', 5000, 'Inactivo', 1),
('Nissan', 'Sentra', 'Negro', 'BBB123', 'Bueno', 6, 1, 3, '2020-11-25 14:27:49', NULL, NULL, 'Activo', 1),
('Chevrolet', 'Aveo', 'Negro', 'CCC123', 'Bueno', 7, 1, 2, '2020-11-25 14:29:02', NULL, NULL, 'Activo', 1),
('Nissan ', 'Platina', 'Plata', 'DFSG123', 'Ninguna', 5, 2, 7, '2020-11-11 19:39:58', NULL, NULL, 'Activo', 1),
('Nissan ', 'Versa', 'Rojo', 'GFDD123', 'Ninguna', 4, 3, 7, '2020-11-11 19:38:39', NULL, NULL, 'Activo', 1),
('VW', 'Golf', 'Plata', 'GFDS123', 'Ninguna', 2, 2, 3, '2020-11-11 19:26:13', '2020-11-25 14:20:33', 4995, 'Inactivo', 1),
('Ford', 'Focus ', 'Negro', 'GHF1234', 'Ninguna', 1, 1, 2, '2020-11-12 20:21:21', NULL, NULL, 'Activo', 1),
('Chevrolet', 'Corsa', 'Negro', 'GHJK123', 'Ninguna', 2, 1, 6, '2020-11-11 19:33:14', NULL, NULL, 'Activo', 1),
('Ford ', 'Focus', 'Azul', 'HGFD123', 'Ninguna', 4, 2, 3, '2020-11-11 19:40:51', NULL, NULL, 'Activo', 1),
('Honda', 'Civic', 'Blanco', 'QWER123', 'Ninguna', 2, 3, 4, '2020-11-11 19:29:28', NULL, NULL, 'Activo', 1),
('Ford', 'Lobo', 'Tinta', 'TREW123', 'Ninguna', 1, 2, 2, '2020-11-11 18:02:21', '2020-11-13 14:04:27', 695, 'Inactivo', 1),
('Nissan ', 'Altima', 'Plata', 'TRU123', 'Ninguna', 4, 1, 5, '2020-11-11 19:40:20', NULL, NULL, 'Activo', 1),
('Chevrolet', 'Blazer', 'Negro', 'WRT123', 'Ninguna', 3, 3, 5, '2020-11-11 19:31:22', NULL, NULL, 'Activo', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cajones`
--
ALTER TABLE `cajones`
  ADD PRIMARY KEY (`Folio`);

--
-- Indices de la tabla `trabajador`
--
ALTER TABLE `trabajador`
  ADD PRIMARY KEY (`idEmpleado`);

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`Placas`),
  ADD KEY `Acomodador` (`Acomodador`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cajones`
--
ALTER TABLE `cajones`
  MODIFY `Folio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

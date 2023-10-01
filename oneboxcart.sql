-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         11.1.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para oneboxcart
CREATE DATABASE IF NOT EXISTS `oneboxcart` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `oneboxcart`;

-- Volcando estructura para tabla oneboxcart.carts
CREATE TABLE IF NOT EXISTS `carts` (
  `CART_ID` int(11) NOT NULL,
  `MOD_DATE` timestamp NOT NULL,
  PRIMARY KEY (`CART_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla oneboxcart.carts: ~0 rows (aproximadamente)

-- Volcando estructura para tabla oneboxcart.cart_id_seq
CREATE TABLE IF NOT EXISTS `cart_id_seq` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) unsigned NOT NULL,
  `cycle_option` tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB SEQUENCE=1;

-- Volcando datos para la tabla oneboxcart.cart_id_seq: ~1 rows (aproximadamente)
INSERT INTO `cart_id_seq` (`next_not_cached_value`, `minimum_value`, `maximum_value`, `start_value`, `increment`, `cache_size`, `cycle_option`, `cycle_count`) VALUES
	(1002, 1, 9223372036854775806, 2, 1, 1000, 0, 0);

-- Volcando estructura para tabla oneboxcart.cart_products
CREATE TABLE IF NOT EXISTS `cart_products` (
  `CART_ID` int(11) DEFAULT NULL,
  `PRODUCT_ID` int(11) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  KEY `delete_carts` (`CART_ID`),
  CONSTRAINT `delete_carts` FOREIGN KEY (`CART_ID`) REFERENCES `carts` (`CART_ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla oneboxcart.cart_products: ~0 rows (aproximadamente)

-- Volcando estructura para evento oneboxcart.delete_carts_event
DELIMITER //
CREATE EVENT `delete_carts_event` ON SCHEDULE EVERY 1 MINUTE STARTS '2023-09-24 12:55:23' ON COMPLETION NOT PRESERVE ENABLE DO DELETE FROM CARTS
    WHERE MOD_DATE < (NOW() - INTERVAL 10 MINUTE)//
DELIMITER ;

-- Volcando estructura para tabla oneboxcart.products
CREATE TABLE IF NOT EXISTS `products` (
  `PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ESP_NAME` varchar(20) NOT NULL,
  `ENG_NAME` varchar(20) NOT NULL,
  `ESP_DESC` varchar(100) DEFAULT NULL,
  `ENG_DESC` varchar(100) DEFAULT NULL,
  `VALUE` float NOT NULL DEFAULT 0,
  PRIMARY KEY (`PRODUCT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla oneboxcart.products: ~10 rows (aproximadamente)
INSERT INTO `products` (`PRODUCT_ID`, `ESP_NAME`, `ENG_NAME`, `ESP_DESC`, `ENG_DESC`, `VALUE`) VALUES
	(31, 'Sofá', 'Sofa', 'Sofá cómodo', 'Comfortable sofa', 499.99),
	(32, 'Mesa', 'Table', 'Mesa de comedor', 'Dining table', 299.99),
	(33, 'Silla', 'Chair', 'Silla de madera', 'Wooden chair', 99.99),
	(34, 'Cama', 'Bed', 'Cama doble', 'Double bed', 799.99),
	(35, 'Armario', 'Cabinet', 'Armario de almacenamiento', 'Storage cabinet', 399.99),
	(36, 'Lámpara', 'Lamp', 'Lámpara de pie', 'Floor lamp', 79.99),
	(37, 'Estantería', 'Bookshelf', 'Estantería de libros grande', 'Large bookshelf', 149.99),
	(38, 'Mesa de centro', 'Coffe Table', 'Mesa de café moderna', 'Modern coffee table', 199.99),
	(39, 'Escritorio', 'Desk', 'Escritorio de oficina', 'Office desk', 249.99),
	(40, 'Silla de escritorio', 'Desk chair', 'Silla de escritorio ergonómica', 'Ergonomic office chair', 149.99);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

SET GLOBAL event_scheduler = ON;

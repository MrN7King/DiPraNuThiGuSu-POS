-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 04, 2025 at 03:52 AM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cafe`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `prod_id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `prod_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `quantity` int NOT NULL,
  `price` double NOT NULL,
  `date` date DEFAULT NULL,
  `image` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `em_username` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `customer_id`, `prod_id`, `prod_name`, `type`, `quantity`, `price`, `date`, `image`, `em_username`) VALUES
(1, 1, 'PROD-001', 'burger', 'Meals', 4, 2240, '2025-03-27', '/Users/dilaksan/Desktop/burger-cafe.jpeg', 'dila'),
(2, 1, 'PROD-001', 'burger', 'Meals', 1, 560, '2025-03-27', '/Users/dilaksan/Desktop/burger-cafe.jpeg', 'dila'),
(3, 2, 'PROD-002', 'Coffee', 'Drinks', 4, 640, '2025-03-27', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(4, 2, 'PROD-002', 'Coffee', 'Drinks', 4, 640, '2025-03-27', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(5, 2, 'PROD-005', 'ginger tea', 'Drinks', 4, 520, '2025-03-27', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(7, 3, 'PROD-001', 'burger', 'Meals', 2, 1120, '2025-03-28', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(8, 3, 'PROD-001', 'burger', 'Meals', 3, 1680, '2025-03-28', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(9, 3, 'PROD-002', 'Coffee', 'Drinks', 3, 480, '2025-03-28', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(10, 4, 'PROD-001', 'burger', 'Meals', 5, 2800, '2025-03-28', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'Nuha'),
(11, 4, 'PROD-001', 'burger', 'Meals', 3, 1680, '2025-03-28', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'Nuha'),
(13, 4, 'PROD-004', 'Plain Tea', 'Drinks', 4, 160, '2025-03-28', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(15, 5, 'PROD-001', 'burger', 'Meals', 1, 560, '2025-03-28', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(16, 5, 'PROD-001', 'burger', 'Meals', 1, 560, '2025-03-28', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(17, 5, 'PROD-002', 'Coffee', 'Drinks', 1, 160, '2025-03-28', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(18, 6, 'PROD-001', 'burger', 'Meals', 1, 560, '2025-03-31', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dikku'),
(19, 6, 'PROD-002', 'Coffee', 'Drinks', 3, 480, '2025-03-31', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dikku'),
(20, 6, 'PROD-005', 'ginger tea', 'Drinks', 1, 130, '2025-03-31', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dikku'),
(21, 7, 'PROD-006', 'Donut', 'Meals', 4, 600, '2025-03-31', '/Users/dilaksan/Pictures/wallpapers/wall10.jpg', 'dikku'),
(22, 8, 'PROD-003', 'Tea', 'Drinks', 1, 100, '2025-03-31', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dikku'),
(23, 8, 'PROD-002', 'Coffee', 'Drinks', 1, 160, '2025-03-31', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dikku'),
(24, 8, 'PROD-005', 'ginger tea', 'Drinks', 2, 260, '2025-04-25', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'dila'),
(25, 8, 'PROD-001', 'burger', 'Meals', 2, 1120, '2025-04-26', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'Admin'),
(27, 9, 'PROD-001', 'burger', 'Meals', 4, 2240, '2025-04-29', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'Admin'),
(28, 9, 'PROD-002', 'Coffee', 'Drinks', 3, 480, '2025-04-29', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'Admin'),
(29, 9, 'PROD-004', 'Plain Tea', 'Drinks', 4, 160, '2025-04-29', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'Admin'),
(31, 10, 'PROD-001', 'burger', 'Meals', 1, 560, '2025-04-30', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', 'guru');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `question` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `answer` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'cashier',
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `username`, `password`, `question`, `answer`, `role`, `date`) VALUES
(10, 'Admin', '1234', 'Your favourite food ?', 'koththu', 'admin', '2025-04-25'),
(11, 'dila', '1234', 'Your pets name ?', 'ricky', 'cashier', '2025-04-25'),
(13, 'guna', '1234', 'Your favourite food ?', 'koththu', 'cashier', NULL),
(15, 'Guru', '1234', 'Your secret that no one knows ?', 'I AM SPIDERMAN', 'cashier', '2025-04-30');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `prod_id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `prod_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `stock` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `price` double NOT NULL,
  `status` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `image` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `prod_id`, `prod_name`, `type`, `stock`, `price`, `status`, `image`, `date`) VALUES
(2, 'PROD-001', 'burger', 'Meals', '12', 560, 'Available', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', '2025-03-27'),
(3, 'PROD-002', 'Coffee', 'Drinks', '78', 160, 'Available', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', '2025-03-27'),
(4, 'PROD-003', 'Tea', 'Drinks', '99', 100, 'Available', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', '2025-03-27'),
(5, 'PROD-004', 'Plain Tea', 'Drinks', '92', 40, 'Available', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', '2025-03-27'),
(6, 'PROD-005', 'ginger tea', 'Drinks', '93', 130, 'Available', '/Users/dilaksan/Desktop/Screenshot 2025-03-27 at 7.09.30 PM.png', '2025-03-27'),
(9, 'PROD-006', 'Donut', 'Meals', '46', 150, 'Available', '/Users/dilaksan/Pictures/wallpapers/wall10.jpg', '2025-03-31');

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
CREATE TABLE IF NOT EXISTS `receipt` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `total` double NOT NULL,
  `date` date DEFAULT NULL,
  `em_username` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`id`, `customer_id`, `total`, `date`, `em_username`) VALUES
(1, 1, 2800, '2025-03-27', 'dila'),
(2, 2, 1800, '2025-03-28', 'dila'),
(3, 3, 3280, '2025-03-28', 'dila'),
(4, 4, 5120, '2025-03-28', 'dila'),
(5, 5, 1280, '2025-03-28', 'dila'),
(6, 6, 1170, '2025-03-31', 'dikku'),
(7, 7, 600, '2025-03-31', 'dikku'),
(8, 8, 1640, '2025-04-26', 'Admin'),
(9, 9, 2880, '2025-04-29', 'Admin'),
(10, 10, 2880, '2025-04-29', 'admin'),
(11, 10, 2880, '2025-04-29', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `uber`
--

DROP TABLE IF EXISTS `uber`;
CREATE TABLE IF NOT EXISTS `uber` (
  `id` int NOT NULL AUTO_INCREMENT,
  `trip_id` varchar(255) NOT NULL,
  `req_time` datetime NOT NULL,
  `drop_time` datetime NOT NULL,
  `drop_addr` varchar(500) DEFAULT NULL,
  `cust_name` varchar(255) DEFAULT NULL,
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `commission` decimal(10,2) NOT NULL DEFAULT '0.00',
  `pay_method` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `uber`
--

INSERT INTO `uber` (`id`, `trip_id`, `req_time`, `drop_time`, `drop_addr`, `cust_name`, `amount`, `commission`, `pay_method`) VALUES
(1, '1', '2025-04-30 21:56:21', '2025-04-30 21:56:21', 'Boswell Road', 'guru', 1000.00, 10.00, 'cash'),
(3, 'UBR123', '2025-04-30 10:00:00', '2025-04-30 10:30:00', '123 Elm St', 'Alice', 15.50, 1.50, 'Card'),
(4, 'UBR456', '2025-04-30 11:00:00', '2025-04-30 11:30:00', '456 Oak Ave', 'Bob', 22.75, 2.25, 'Cash');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

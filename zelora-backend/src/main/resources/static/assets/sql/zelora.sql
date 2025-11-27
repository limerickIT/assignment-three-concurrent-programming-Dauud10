-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 27, 2025 at 12:00 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `zelora`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `category_image` varchar(255) DEFAULT NULL,
  `category_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `category_image`, `category_name`) VALUES
(1, NULL, 'T-Shirts'),
(2, NULL, 'Hoodies'),
(3, NULL, 'Footwear'),
(4, NULL, 'Jackets'),
(5, NULL, 'Trousers'),
(6, NULL, 'Accessories');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `address` tinytext DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `communication_preferences` tinytext DEFAULT NULL,
  `date_joined` date DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `payment_info` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `size_preferences` tinytext DEFAULT NULL,
  `vip_status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `address`, `city`, `communication_preferences`, `date_joined`, `date_of_birth`, `email`, `first_name`, `last_name`, `password`, `payment_info`, `phone_number`, `size_preferences`, `vip_status`) VALUES
(1, '123 Test Street', 'Dublin', NULL, NULL, NULL, 'test@zelora.com', 'Test', 'User', NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `inventory_id` int(11) NOT NULL,
  `quantity_in_stock` int(11) DEFAULT NULL,
  `quantity_reserved` int(11) DEFAULT NULL,
  `reorder_point` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `supplier_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`inventory_id`, `quantity_in_stock`, `quantity_reserved`, `reorder_point`, `product_id`, `supplier_id`) VALUES
(201, 20, 2, 5, 101, NULL),
(202, 10, 1, 4, 102, NULL),
(203, 3, 1, 3, 103, NULL),
(204, 0, 0, 2, 104, NULL),
(205, 12, 3, 4, 105, NULL),
(206, 8, 0, 3, 106, NULL),
(207, 2, 0, 2, 107, NULL),
(208, 15, 1, 5, 108, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `orderitems`
--

CREATE TABLE `orderitems` (
  `order_item_id` int(11) NOT NULL,
  `customisation_options` tinytext DEFAULT NULL,
  `item_price` decimal(38,2) DEFAULT NULL,
  `item_weight` decimal(38,2) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `subtotal` decimal(38,2) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `delivery_date` date DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `shipping_method` varchar(255) DEFAULT NULL,
  `total_amount` decimal(38,2) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL,
  `colour` varchar(255) DEFAULT NULL,
  `description` tinytext DEFAULT NULL,
  `discounted_price` decimal(38,2) DEFAULT NULL,
  `feature_image` varchar(255) NOT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `material` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `sustainability_rating` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `supplier_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `colour`, `description`, `discounted_price`, `feature_image`, `manufacturer`, `material`, `price`, `product_name`, `release_date`, `size`, `sustainability_rating`, `category_id`, `supplier_id`) VALUES
(1, 'White', 'Soft organic cotton t-shirt, eco-friendly and durable.', 24.99, 'img1.jpg', 'EcoWear Ltd', 'Organic Cotton', 29.99, 'Eco Cotton T-Shirt', '2025-11-20', 'M', 5, 1, 1),
(2, 'Black', 'Hoodie made from 70% recycled polyester.', NULL, 'img2.jpg', 'GreenFit', 'Recycled Polyester', 59.99, 'Recycled Fiber Hoodie', '2025-11-10', 'L', 4, 2, 1),
(3, 'Grey', 'Soft breathable socks made from bamboo fibres.', 6.99, 'img3.jpg', 'NatureFoot', 'Bamboo', 9.99, 'Bamboo Comfort Socks', '2025-11-22', 'One Size', 5, 1, 1),
(101, 'Black', 'Organic cotton black t-shirt', 24.99, 'blackt-shirtLrg.png', 'Zelora', 'Organic Cotton', 29.99, 'Black Organic Tee', '2025-11-22', 'M', 5, 1, 1),
(102, 'White', 'Soft cotton white t-shirt', NULL, 'whitet-shirtLrg.png', 'Zelora', 'Cotton', 24.99, 'White Everyday Tee', '2025-11-18', 'M', 4, 1, 1),
(103, 'Charcoal', 'Relaxed hoodie made from recycled fibres', 39.99, 'recyclehoodieLrg.png', 'Zelora', 'Recycled Polyester', 49.99, 'Recycled Hoodie', '2025-11-20', 'L', 5, 2, 1),
(104, 'Black', 'Classic faux-leather biker jacket', NULL, 'leatherjacket.png', 'Zelora', 'PU Leather', 89.99, 'Leather Jacket', '2025-11-10', 'M', 3, 4, 1),
(105, 'White', 'Everyday low-profile sneaker', NULL, 'sneakers.png', 'Zelora', 'Canvas / Rubber', 59.99, 'Low Sneaker', '2025-11-15', '42', 4, 3, 1),
(106, 'Natural', 'Recycled canvas tote bag', NULL, 'bag.png', 'Zelora', 'Recycled Canvas', 19.99, 'Everyday Tote Bag', '2025-11-24', 'One size', 5, 6, 1),
(107, 'Indigo', 'Organic cotton straight jeans', 59.99, 'jeans.png', 'Zelora', 'Organic Cotton Denim', 69.99, 'Straight Jeans', '2025-11-05', '32', 4, 5, 1),
(108, 'Blue', 'Lightweight longsleeve top', NULL, 'longsleeve.png', 'Zelora', 'Cotton', 34.99, 'Cotton Longsleeve', '2025-11-23', 'M', 3, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `review_id` int(11) NOT NULL,
  `flagged_as_spam` bit(1) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `review_date` date DEFAULT NULL,
  `review_text` tinytext DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`review_id`, `flagged_as_spam`, `rating`, `review_date`, `review_text`, `customer_id`, `product_id`) VALUES
(301, b'0', 5, '2025-11-23', 'Great fit and very soft.', 1, 101),
(302, b'0', 4, '2025-11-24', 'Nice hoodie, runs a little big.', 1, 103),
(303, b'0', 3, '2025-11-21', 'Comfortable everyday sneaker.', 1, 105);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `supplier_id` int(11) NOT NULL,
  `address` tinytext DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(255) DEFAULT NULL,
  `description` tinytext DEFAULT NULL,
  `supplier_name` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`supplier_id`, `address`, `contact_email`, `contact_name`, `contact_phone`, `description`, `supplier_name`, `website`) VALUES
(1, NULL, NULL, NULL, NULL, NULL, 'Default Supplier', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `wishlist`
--

CREATE TABLE `wishlist` (
  `wishlist_id` int(11) NOT NULL,
  `added_date` date DEFAULT NULL,
  `notes` tinytext DEFAULT NULL,
  `wishlist_name` varchar(255) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `wishlist`
--

INSERT INTO `wishlist` (`wishlist_id`, `added_date`, `notes`, `wishlist_name`, `customer_id`, `product_id`) VALUES
(1, '2025-11-27', NULL, 'Default', NULL, 101),
(2, '2025-11-27', NULL, 'Default', NULL, 103),
(3, '2025-11-27', NULL, 'Default', NULL, 102),
(4, '2025-11-27', NULL, 'Default', NULL, 108),
(5, '2025-11-27', NULL, 'Default', NULL, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`inventory_id`),
  ADD KEY `FKq2yge7ebtfuvwufr6lwfwqy9l` (`product_id`),
  ADD KEY `FK1ticfk66udu8mbj1otebp2rgw` (`supplier_id`);

--
-- Indexes for table `orderitems`
--
ALTER TABLE `orderitems`
  ADD PRIMARY KEY (`order_item_id`),
  ADD KEY `FKm3mp87f5ygbbfuqfdhc09y9a` (`order_id`),
  ADD KEY `FKqu7dfdpbhltsgxfq2ahkdnyv5` (`product_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `FKpxtb8awmi0dk6smoh2vp1litg` (`customer_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  ADD KEY `FK6i174ixi9087gcvvut45em7fd` (`supplier_id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `FK4sm0k8kw740iyuex3vwwv1etu` (`customer_id`),
  ADD KEY `FKpl51cejpw4gy5swfar8br9ngi` (`product_id`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`supplier_id`);

--
-- Indexes for table `wishlist`
--
ALTER TABLE `wishlist`
  ADD PRIMARY KEY (`wishlist_id`),
  ADD KEY `FKk6lal9w7ut5e4xvta479rq06y` (`customer_id`),
  ADD KEY `FK6p7qhvy1bfkri13u29x6pu8au` (`product_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `FK1ticfk66udu8mbj1otebp2rgw` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`),
  ADD CONSTRAINT `FKq2yge7ebtfuvwufr6lwfwqy9l` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Constraints for table `orderitems`
--
ALTER TABLE `orderitems`
  ADD CONSTRAINT `FKm3mp87f5ygbbfuqfdhc09y9a` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `FKqu7dfdpbhltsgxfq2ahkdnyv5` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FKpxtb8awmi0dk6smoh2vp1litg` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FK6i174ixi9087gcvvut45em7fd` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`),
  ADD CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `FK4sm0k8kw740iyuex3vwwv1etu` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  ADD CONSTRAINT `FKpl51cejpw4gy5swfar8br9ngi` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Constraints for table `wishlist`
--
ALTER TABLE `wishlist`
  ADD CONSTRAINT `FK6p7qhvy1bfkri13u29x6pu8au` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `FKk6lal9w7ut5e4xvta479rq06y` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
